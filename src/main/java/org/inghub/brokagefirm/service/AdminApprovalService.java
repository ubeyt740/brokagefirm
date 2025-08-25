package org.inghub.brokagefirm.service;

import org.inghub.brokagefirm.dto.adminorder.AdminApprovalRequest;
import org.inghub.brokagefirm.dto.adminorder.AdminOrderAction;
import org.inghub.brokagefirm.model.Asset;
import org.inghub.brokagefirm.model.Order;
import org.inghub.brokagefirm.model.OrderSide;
import org.inghub.brokagefirm.model.OrderStatus;
import org.inghub.brokagefirm.dto.adminorder.ApprovalDecision;
import org.inghub.brokagefirm.repository.AdminOrderRepository;
import org.inghub.brokagefirm.repository.AssetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class AdminApprovalService {

    private final AdminOrderRepository orderRepository;
    private final AssetRepository assetRepository;

    public AdminApprovalService(AdminOrderRepository orderRepository, AssetRepository assetRepository) {
        this.orderRepository = orderRepository;
        this.assetRepository = assetRepository;
    }

    @Transactional
    public void processOrders(AdminApprovalRequest request) {
        List<Long> ids = request.getActions().stream()
                .map(AdminOrderAction::getOrderId)
                .toList();

        List<Order> pendingOrders = orderRepository.findByIdInAndStatus(ids, OrderStatus.PENDING);

        for (AdminOrderAction action : request.getActions()) {
            Order order = pendingOrders.stream()
                    .filter(o -> o.getId().equals(action.getOrderId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Order not found or not pending: " + action.getOrderId()));

            if (action.getDecision() == ApprovalDecision.REJECT) {
                order.setStatus(OrderStatus.CANCELED);
                revertReservedAssets(order); // CANCEL operation: usableSize is restored
            } else {
                order.setStatus(OrderStatus.MATCHED);
                applyMatchedAssets(order);  // APPROVE operation: size and usableSize are updated
            }

            orderRepository.save(order);
        }
    }

    private void revertReservedAssets(Order order) {
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY")
                .orElseGet(() -> new Asset());

        if (order.getOrderSide() == OrderSide.BUY) {
            // BUY operation canceled → TRY usable is restored
            tryAsset.setCustomerId(order.getCustomerId());
            tryAsset.setAssetName("TRY");
            tryAsset.setUsableSize((tryAsset.getUsableSize() != null ? tryAsset.getUsableSize() : 0.0) + order.getSize() * order.getPrice());
            assetRepository.save(tryAsset);
        } else { // SELL
            Asset sellAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName())
                    .orElseGet(() -> {
                        Asset a = new Asset();
                        a.setCustomerId(order.getCustomerId());
                        a.setAssetName(order.getAssetName());
                        return a;
                    });

            sellAsset.setSize(sellAsset.getSize() != null ? sellAsset.getSize() : 0.0);
            sellAsset.setUsableSize((sellAsset.getUsableSize() != null ? sellAsset.getUsableSize() : 0.0) + order.getSize());
            assetRepository.save(sellAsset);
        }
    }

    private void applyMatchedAssets(Order order) {
        if (order.getOrderSide() == OrderSide.BUY) {
            // BUY → TRY decreases, asset increases
            Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY")
                    .orElseThrow(() -> new RuntimeException("TRY asset not found for customer"));

            tryAsset.setUsableSize(tryAsset.getUsableSize() - order.getSize() * order.getPrice());
            tryAsset.setSize(tryAsset.getSize()); // size does not change, because the TRY investment was received
            assetRepository.save(tryAsset);

            Asset boughtAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName())
                    .orElseGet(() -> {
                        Asset a = new Asset();
                        a.setCustomerId(order.getCustomerId());
                        a.setAssetName(order.getAssetName());
                        a.setSize(0.0);
                        a.setUsableSize(0.0);
                        return a;
                    });

            boughtAsset.setSize(boughtAsset.getSize() + order.getSize());
            boughtAsset.setUsableSize(boughtAsset.getUsableSize() + order.getSize());
            assetRepository.save(boughtAsset);

        } else { // SELL
            Asset sellAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName())
                    .orElseThrow(() -> new RuntimeException("Asset not found for SELL"));

            sellAsset.setSize(sellAsset.getSize() - order.getSize());
            sellAsset.setUsableSize(sellAsset.getUsableSize() - order.getSize());
            assetRepository.save(sellAsset);

            Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY")
                    .orElseGet(() -> {
                        Asset a = new Asset();
                        a.setCustomerId(order.getCustomerId());
                        a.setAssetName("TRY");
                        a.setSize(0.0);
                        a.setUsableSize(0.0);
                        return a;
                    });


            double total = order.getSize() * order.getPrice();
            tryAsset.setUsableSize((tryAsset.getUsableSize() != null ? tryAsset.getUsableSize() : 0.0) + total);
            tryAsset.setSize((tryAsset.getSize() != null ? tryAsset.getSize() : 0.0) + total);
            assetRepository.save(tryAsset);
        }
    }

}
