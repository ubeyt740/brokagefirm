package org.inghub.brokagefirm.service;

import org.inghub.brokagefirm.dto.createorder.CreateOrderRequest;
import org.inghub.brokagefirm.model.*;
import org.inghub.brokagefirm.repository.AssetRepository;
import org.inghub.brokagefirm.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateOrderServiceTest {
    @Mock
    private AssetRepository assetRepository;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private CreateOrderService createOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrderSetsPending() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomerId(1L);
        request.setAssetName("ING");
        request.setSide(OrderSide.BUY);
        request.setSize(100.0);
        request.setPrice(50.0);

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        Asset tryAsset = new Asset();
        tryAsset.setCustomerId(1L);
        tryAsset.setAssetName("TRY");
        tryAsset.setUsableSize(100000.0);
        tryAsset.setSize(100000.0);

        when(assetRepository.findByCustomerIdAndAssetName(Mockito.anyLong(), Mockito.anyString()))
                .thenReturn(Optional.of(tryAsset));
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        assertDoesNotThrow(() -> {
            var response = createOrderService.createOrder(request);
            assertEquals(true, response.isSuccess());
        });

        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
