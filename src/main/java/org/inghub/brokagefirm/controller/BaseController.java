package org.inghub.brokagefirm.controller;

import io.jsonwebtoken.Claims;
import org.inghub.brokagefirm.exception.AppException;
import org.inghub.brokagefirm.security.JwtUtil;
import org.springframework.http.HttpStatus;

public abstract class BaseController {

    protected final JwtUtil jwtUtil;

    public BaseController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    protected Claims getClaimsFromHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token is missing or invalid");
        }
        String token = authHeader.replace("Bearer ", "");
        return jwtUtil.validateToken(token);
    }

    protected Long getAuthCustomerId(String authHeader) {
        return getClaimsFromHeader(authHeader).get("customerId", Long.class);
    }

    protected String getAuthRole(String authHeader) {
        return getClaimsFromHeader(authHeader).get("role", String.class);
    }

    protected void validateAccess(String authHeader, Long resourceCustomerId) {
        String role = getAuthRole(authHeader);
        Long authCustomerId = getAuthCustomerId(authHeader);


        if (resourceCustomerId == null) {
            if (!"ADMIN".equals(role)) {
                throw new AppException("Admin privileges are required for this operation!", HttpStatus.FORBIDDEN.value());
            }
        } else {
            if (!"ADMIN".equals(role) && !authCustomerId.equals(resourceCustomerId)) {
                throw new AppException("You do not have permission to access this resource!", HttpStatus.FORBIDDEN.value());
            }
        }
    }
    //Authorization method for admin operations
    protected void validateAdminAccess(String authHeader) {
        String role = getAuthRole(authHeader);
        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Admin privileges are required for this operation!");
        }
    }
}