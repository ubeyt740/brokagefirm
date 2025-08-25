package org.inghub.brokagefirm.controller;

import org.inghub.brokagefirm.dto.listorder.ListOrderRequest;
import org.inghub.brokagefirm.dto.listorder.ListOrderResponse;
import org.inghub.brokagefirm.security.JwtUtil;
import org.inghub.brokagefirm.service.ListOrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class ListOrderController extends BaseController {

    private final ListOrderService listOrderService;

    public ListOrderController(JwtUtil jwtUtil, ListOrderService listOrderService) {
        super(jwtUtil);
        this.listOrderService = listOrderService;
    }

    @PostMapping("/list")
    public ListOrderResponse listOrders(@RequestBody(required = false) ListOrderRequest request,
                                        @RequestHeader("Authorization") String authHeader) {

        if (request == null) {
            request = new ListOrderRequest();
        }

        // If customerId is not in the request, get it from the token
        if (request.getCustomerId() == null) {
            request.setCustomerId(getAuthCustomerId(authHeader));
        }


        validateAccess(authHeader, request.getCustomerId());

        return listOrderService.listOrders(request);
    }
}
