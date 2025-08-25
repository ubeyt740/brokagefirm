package org.inghub.brokagefirm.controller;

import org.inghub.brokagefirm.dto.adminorder.AdminApprovalRequest;
import org.inghub.brokagefirm.dto.adminorder.AdminApprovalResponse;
import org.inghub.brokagefirm.security.JwtUtil;
import org.inghub.brokagefirm.service.AdminApprovalService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/orders")
public class AdminApprovalController extends BaseController {

    private final AdminApprovalService adminApprovalService;

    public AdminApprovalController(JwtUtil jwtUtil, AdminApprovalService adminApprovalService) {
        super(jwtUtil);
        this.adminApprovalService = adminApprovalService;
    }

    @PostMapping("/approveOrReject")
    public AdminApprovalResponse approveOrRejectOrders(
            @RequestBody AdminApprovalRequest request,
            @RequestHeader("Authorization") String authHeader) {


        validateAdminAccess(authHeader);

        adminApprovalService.processOrders(request);
        AdminApprovalResponse response = new AdminApprovalResponse();
        response.setSuccess(true);
        response.setMessage("Orders processed successfully");
        return response;
    }
}

