package org.inghub.brokagefirm.service;

import org.inghub.brokagefirm.dto.listorder.ListOrderRequest;
import org.inghub.brokagefirm.dto.listorder.ListOrderResponse;
import org.inghub.brokagefirm.dto.listorder.OrderDto;
import org.inghub.brokagefirm.model.Order;
import org.inghub.brokagefirm.repository.ListOrderRepository;
import org.inghub.brokagefirm.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListOrderService {

    private final ListOrderRepository listOrderRepository;

    public ListOrderService(ListOrderRepository listOrderRepository) {
        this.listOrderRepository = listOrderRepository;
    }

    public ListOrderResponse listOrders(ListOrderRequest request) {
        List<Order> orders = listOrderRepository.findOrders(
                request.getCustomerId(),
                request.getStartDate(),
                request.getEndDate()
        );

        List<OrderDto> orderDtos = orders.stream().map(order -> {
            OrderDto dto = new OrderDto();
            dto.setOrderId(order.getId());
            dto.setCustomerId(order.getCustomerId());
            dto.setAssetName(order.getAssetName());
            dto.setSide(order.getOrderSide().name());
            dto.setSize(order.getSize());
            dto.setPrice(order.getPrice());
            dto.setStatus(order.getStatus().name());
            dto.setCreateDate(order.getCreateDate());
            return dto;
        }).collect(Collectors.toList());

        ListOrderResponse response = new ListOrderResponse();
        response.setSuccess(true);
        response.setMessage("Orders listed successfully");
        response.setOrders(orderDtos);

        return response;
    }
}
