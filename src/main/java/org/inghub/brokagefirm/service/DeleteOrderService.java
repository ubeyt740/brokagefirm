package org.inghub.brokagefirm.service;

import org.inghub.brokagefirm.dto.deleteorder.DeleteOrderRequest;
import org.inghub.brokagefirm.dto.deleteorder.DeleteOrderResponse;
import org.inghub.brokagefirm.model.Asset;
import org.inghub.brokagefirm.model.Order;
import org.inghub.brokagefirm.model.OrderSide;
import org.inghub.brokagefirm.model.OrderStatus;
import org.inghub.brokagefirm.repository.AssetRepository;
import org.inghub.brokagefirm.repository.DeleteOrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteOrderService {

    private final DeleteOrderRepository deleteOrderRepository;
    private final AssetRepository assetRepository;

    public DeleteOrderService(DeleteOrderRepository deleteOrderRepository, AssetRepository assetRepository) {
        this.deleteOrderRepository = deleteOrderRepository;
        this.assetRepository = assetRepository;
    }
    public Long getCustomerIdByOrderId(Long orderId) {
        return deleteOrderRepository.findById(orderId)
                .map(Order::getCustomerId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
    public DeleteOrderResponse deleteOrder(DeleteOrderRequest request) {
        DeleteOrderResponse response = new DeleteOrderResponse();

        Optional<Order> optionalOrder = deleteOrderRepository.findById(request.getOrderId());
        if (optionalOrder.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("Order bulunamadı!");
            return response;
        }

        Order order = optionalOrder.get();

        if (order.getStatus() != OrderStatus.PENDING) {
            response.setSuccess(false);
            response.setMessage("Sadece PENDING statüsündeki order silinebilir!");
            return response;
        }

        // Usable size geri yükleme
        if (order.getOrderSide() == OrderSide.BUY) {
            Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), "TRY")
                    .orElseThrow(() -> new RuntimeException("Müşterinin TRY varlığı yok!"));

            double totalCost = order.getSize() * order.getPrice();
            tryAsset.setUsableSize(tryAsset.getUsableSize() + totalCost);
            assetRepository.save(tryAsset);

        } else if (order.getOrderSide() == OrderSide.SELL) {
            Asset asset = assetRepository.findByCustomerIdAndAssetName(order.getCustomerId(), order.getAssetName())
                    .orElseThrow(() -> new RuntimeException("Müşterinin asset’i yok!"));

            asset.setUsableSize(asset.getUsableSize() + order.getSize());
            assetRepository.save(asset);
        }

        deleteOrderRepository.delete(order);

        response.setSuccess(true);
        response.setMessage("Order başarıyla silindi!");
        return response;
    }
}
