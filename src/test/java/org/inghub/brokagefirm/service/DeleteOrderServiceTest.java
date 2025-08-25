package org.inghub.brokagefirm.service;

import org.inghub.brokagefirm.dto.deleteorder.DeleteOrderRequest;
import org.inghub.brokagefirm.dto.deleteorder.DeleteOrderResponse;
import org.inghub.brokagefirm.model.Order;
import org.inghub.brokagefirm.model.OrderStatus;
import org.inghub.brokagefirm.repository.DeleteOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteOrderServiceTest {

    @Mock
    private DeleteOrderRepository deleteOrderRepository;
    @InjectMocks
    private DeleteOrderService deleteOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteOrderSuccess() {
        DeleteOrderRequest request = new DeleteOrderRequest();
        request.setOrderId(1L);

        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.PENDING);

        when(deleteOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        DeleteOrderResponse response = deleteOrderService.deleteOrder(request);

        assertTrue(response.isSuccess());
        verify(deleteOrderRepository, times(1)).delete(order);
    }
    @Test
    void testDeleteOrderFailure() {
        DeleteOrderRequest request = new DeleteOrderRequest();
        request.setOrderId(1L);

        Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.CANCELED);

        when(deleteOrderRepository.findById(1L)).thenReturn(Optional.of(order));

        DeleteOrderResponse response = deleteOrderService.deleteOrder(request);

        assertFalse(response.isSuccess());
        verify(deleteOrderRepository, never()).delete(order);
    }
}
