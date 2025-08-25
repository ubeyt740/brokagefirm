package org.inghub.brokagefirm.service;

import org.inghub.brokagefirm.dto.adminorder.*;
import org.inghub.brokagefirm.model.*;
import org.inghub.brokagefirm.repository.AdminOrderRepository;
import org.inghub.brokagefirm.repository.AssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminApprovalServiceTest {

    @Mock
    private AdminOrderRepository orderRepository;
    @Mock
    private AssetRepository assetRepository;
    @InjectMocks
    private AdminApprovalService adminApprovalService;

    private Order pendingBuy;
    private Order pendingSell;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pendingBuy = new Order();
        pendingBuy.setId(1L);
        pendingBuy.setCustomerId(1L);
        pendingBuy.setAssetName("ING");
        pendingBuy.setSize(100.0);
        pendingBuy.setPrice(50.0);
        pendingBuy.setOrderSide(OrderSide.BUY);
        pendingBuy.setStatus(OrderStatus.PENDING);

        pendingSell = new Order();
        pendingSell.setId(2L);
        pendingSell.setCustomerId(1L);
        pendingSell.setAssetName("ING");
        pendingSell.setSize(50.0);
        pendingSell.setPrice(40.0);
        pendingSell.setOrderSide(OrderSide.SELL);
        pendingSell.setStatus(OrderStatus.PENDING);
    }

    @Test
    void testApproveAndRejectOrders() {
        List<AdminOrderAction> actions = List.of(
                new AdminOrderAction() {{ setOrderId(1L); setDecision(ApprovalDecision.APPROVE); }},
                new AdminOrderAction() {{ setOrderId(2L); setDecision(ApprovalDecision.REJECT); }}
        );

        AdminApprovalRequest request = new AdminApprovalRequest();
        request.setActions(actions);

        when(orderRepository.findByIdInAndStatus(anyList(), eq(OrderStatus.PENDING)))
                .thenReturn(List.of(pendingBuy, pendingSell));
        when(assetRepository.findByCustomerIdAndAssetName(anyLong(), anyString()))
                .thenReturn(Optional.of(new Asset() {{
                    setCustomerId(1L);
                    setAssetName("ING");
                    setSize(100.0);
                    setUsableSize(100.0);
                }}));

        assertDoesNotThrow(() -> adminApprovalService.processOrders(request));

        assertEquals(OrderStatus.MATCHED, pendingBuy.getStatus());
        assertEquals(OrderStatus.CANCELED, pendingSell.getStatus());

        verify(orderRepository, times(2)).save(any(Order.class));
        verify(assetRepository, atLeastOnce()).save(any(Asset.class));
    }
}
