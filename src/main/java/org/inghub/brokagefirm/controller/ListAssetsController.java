package org.inghub.brokagefirm.controller;

import org.inghub.brokagefirm.dto.listasset.ListAssetsRequest;
import org.inghub.brokagefirm.model.Asset;
import org.inghub.brokagefirm.security.JwtUtil;
import org.inghub.brokagefirm.service.ListAssetsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
public class ListAssetsController extends BaseController {

    private final ListAssetsService listAssetsService;

    public ListAssetsController(JwtUtil jwtUtil, ListAssetsService listAssetsService) {
        super(jwtUtil);
        this.listAssetsService = listAssetsService;
    }

    @PostMapping("/list")
    public ResponseEntity<List<Asset>> listAssets(@RequestBody(required = false) ListAssetsRequest request,
                                                  @RequestHeader("Authorization") String authHeader) {

        if (request == null) request = new ListAssetsRequest();

        String role = getAuthRole(authHeader);


        if (!"ADMIN".equals(role) && request.getCustomerId() == null) {
            request.setCustomerId(getAuthCustomerId(authHeader));
        }


        validateAccess(authHeader, request.getCustomerId());


        List<Asset> assets = listAssetsService.listAssets(request);
        return ResponseEntity.ok(assets);
    }
}