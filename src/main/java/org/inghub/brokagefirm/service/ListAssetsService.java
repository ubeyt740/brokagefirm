package org.inghub.brokagefirm.service;

import org.inghub.brokagefirm.dto.listasset.ListAssetsRequest;
import org.inghub.brokagefirm.model.Asset;
import org.inghub.brokagefirm.repository.ListAssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAssetsService {

    private final ListAssetRepository listAssetRepository;

    public ListAssetsService(ListAssetRepository listAssetRepository) {
        this.listAssetRepository = listAssetRepository;
    }

    public List<Asset> listAssets(ListAssetsRequest request) {
        return listAssetRepository.findAssetsWithFilters(
                request.getCustomerId(),
                request.getAssetName(),
                request.getOnlyUsable(),
                request.getMinSize(),
                request.getMaxSize(),
                request.getMinUsable(),
                request.getMaxUsable()
        );
    }
}
