package org.inghub.brokagefirm.controller;

import org.inghub.brokagefirm.dto.createorder.CreateOrderRequest;
import org.inghub.brokagefirm.dto.createorder.CreateOrderResponse;
import org.inghub.brokagefirm.security.JwtUtil;
import org.inghub.brokagefirm.service.CreateOrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class CreateOrderController extends BaseController {

    private final CreateOrderService createOrderService;

    public CreateOrderController(JwtUtil jwtUtil, CreateOrderService createOrderService) {
        super(jwtUtil);
        this.createOrderService = createOrderService;
    }

    @PostMapping("/create")
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest request,
                                           @RequestHeader("Authorization") String authHeader) {
        // customerId is retrieved from the token and set into the request
        Long customerId = getAuthCustomerId(authHeader);
        request.setCustomerId(customerId);


        validateAccess(authHeader, request.getCustomerId());

        return createOrderService.createOrder(request);
    }
}