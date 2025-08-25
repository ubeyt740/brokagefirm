package org.inghub.brokagefirm.controller;

import org.inghub.brokagefirm.dto.deleteorder.DeleteOrderRequest;
import org.inghub.brokagefirm.dto.deleteorder.DeleteOrderResponse;
import org.inghub.brokagefirm.security.JwtUtil;
import org.inghub.brokagefirm.service.DeleteOrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class DeleteOrderController extends BaseController {

    private final DeleteOrderService deleteOrderService;

    public DeleteOrderController(JwtUtil jwtUtil, DeleteOrderService deleteOrderService) {
        super(jwtUtil);
        this.deleteOrderService = deleteOrderService;
    }

    @PostMapping("/delete")
    public DeleteOrderResponse deleteOrder(@RequestBody DeleteOrderRequest request,
                                           @RequestHeader("Authorization") String authHeader) {
        Long resourceCustomerId = deleteOrderService.getCustomerIdByOrderId(request.getOrderId());
        validateAccess(authHeader, resourceCustomerId);
        return deleteOrderService.deleteOrder(request);
    }
}
