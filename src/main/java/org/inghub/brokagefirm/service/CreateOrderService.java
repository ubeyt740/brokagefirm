package org.inghub.brokagefirm.service;

import org.inghub.brokagefirm.dto.createorder.CreateOrderRequest;
import org.inghub.brokagefirm.dto.createorder.CreateOrderResponse;
import org.inghub.brokagefirm.model.Asset;
import org.inghub.brokagefirm.model.Order;
import org.inghub.brokagefirm.model.OrderSide;
import org.inghub.brokagefirm.model.OrderStatus;
import org.inghub.brokagefirm.repository.AssetRepository;
import org.inghub.brokagefirm.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateOrderService {

    private final AssetRepository assetRepository;
    private final OrderRepository orderRepository;

    public CreateOrderService(AssetRepository assetRepository, OrderRepository orderRepository) {
        this.assetRepository = assetRepository;
        this.orderRepository = orderRepository;
    }

    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        CreateOrderResponse response = new CreateOrderResponse();
        try {
            // Order objesi oluştur
            Order order = new Order();
            order.setCustomerId(request.getCustomerId());
            order.setAssetName(request.getAssetName());
            order.setOrderSide(request.getSide());
            order.setSize(request.getSize());
            order.setPrice(request.getPrice());
            order.setStatus(OrderStatus.PENDING);
            order.setCreateDate(LocalDateTime.now());

            if (order.getOrderSide() == OrderSide.BUY) {
                // TRY check
                Asset tryAsset = assetRepository.findByCustomerIdAndAssetName(request.getCustomerId(), "TRY")
                        .orElseThrow(() -> new RuntimeException("The customer has no TRY assets!"));

                double totalCost = request.getSize() * request.getPrice();
                if (tryAsset.getUsableSize() < totalCost) {
                    throw new RuntimeException("Insufficient TRY balance!");
                }
                tryAsset.setUsableSize(tryAsset.getUsableSize() - totalCost);
                assetRepository.save(tryAsset);

            } else if (order.getOrderSide() == OrderSide.SELL) {
                // Asset to be sold control
                Asset asset = assetRepository.findByCustomerIdAndAssetName(request.getCustomerId(), request.getAssetName())
                        .orElseThrow(() -> new RuntimeException("The customer has no asset to sell!"));

                if (asset.getUsableSize() < request.getSize()) {
                    throw new RuntimeException("Insufficient asset balance!");
                }
                asset.setUsableSize(asset.getUsableSize() - request.getSize());
                assetRepository.save(asset);
            }

            // Order save
            orderRepository.save(order);

            response.setSuccess(true);
            response.setMessage("The order has been created successfully.");
        } catch (RuntimeException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }

        return response;
    }
}
