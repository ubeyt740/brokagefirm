package org.inghub.brokagefirm.service;

import org.inghub.brokagefirm.dto.listasset.ListAssetsRequest;
import org.inghub.brokagefirm.model.Asset;
import org.inghub.brokagefirm.repository.ListAssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ListAssetsServiceTest {

    private ListAssetRepository listAssetRepository;
    private ListAssetsService listAssetsService;

    @BeforeEach
    void setUp() {
        listAssetRepository = Mockito.mock(ListAssetRepository.class);
        listAssetsService = new ListAssetsService(listAssetRepository);
    }

    @Test
    void testListAssetsWithoutFilters() {
        Asset asset = new Asset();
        asset.setId(1L);
        asset.setCustomerId(1L);
        asset.setAssetName("ABC");
        asset.setSize(100.0);
        asset.setUsableSize(100.0);

        ListAssetsRequest request = new ListAssetsRequest();
        request.setCustomerId(null);
        request.setAssetName(null);
        request.setOnlyUsable(null);
        request.setMaxSize(null);
        request.setMinSize(null);
        request.setMaxUsable(null);
        request.setMinSize(null);

        when(listAssetRepository.findAssetsWithFilters(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull()
        )).thenReturn(List.of(asset));

        List<Asset> result = listAssetsService.listAssets(request);

        assertEquals(1, result.size());
        assertEquals("ABC", result.get(0).getAssetName());
    }
}
