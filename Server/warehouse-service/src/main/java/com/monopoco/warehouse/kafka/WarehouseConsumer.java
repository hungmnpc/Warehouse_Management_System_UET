package com.monopoco.warehouse.kafka;

import com.monopoco.common.factory.HistoryFactory;
import com.monopoco.common.model.CommonResponse;
import com.monopoco.common.model.ProductDTO;
import com.monopoco.common.model.inventory.StockEvent;
import com.monopoco.warehouse.clients.ProductClient;
import com.monopoco.warehouse.entity.BinStorage;
import com.monopoco.warehouse.repository.BinStorageRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Project: Server
 * Package: com.monopoco.history.kafka
 * Author: hungdq
 * Date: 25/04/2024
 * Time: 11:39
 */

@Service
@Transactional
public class WarehouseConsumer {

    private static final Logger log = LoggerFactory.getLogger(WarehouseConsumer.class);

    @Autowired
    private BinStorageRepository binStorageRepository;

    @Autowired
    private WarehouseProducer warehouseProducer;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private Environment environment;


    @KafkaListener(topics = "stock", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(StockEvent stockEvent) {
        if (stockEvent != null && stockEvent.getBinId() != null) {
            log.info(String.format("Received Service => %s", stockEvent.toString()));
            BinStorage binStorage = binStorageRepository.findById(stockEvent.getBinId()).orElse(null);
            if (binStorage != null && stockEvent.getQuantity() > 0 && !binStorage.getOccupied()) {
                binStorage.setOccupied(true);
                ProductDTO product = null;
                try {
                    ResponseEntity<CommonResponse<ProductDTO>> productResponse = productClient.getProductById(
                            stockEvent.getProductId(),
                            environment.getProperty("superTokenIncludeBearer")
                    );
                    if (productResponse.getStatusCode().is2xxSuccessful()) {
                        product = productResponse.getBody().getData();
                    } else {
                        product = ProductDTO.builder()
                                .productName("null")
                                .unit("unit")
                                .build();
                    }
                } catch (Exception exception) {
                    product = ProductDTO.builder()
                            .productName("null")
                            .unit("unit").build();
                }
                warehouseProducer.sendMessage(HistoryFactory.createHistoryEventUPDATE(
                        stockEvent.getUserId(),
                        stockEvent.getUsername(),
                        binStorage.getWarehouseId(),
                        "warehouse",
                        String.format("Successfully added to inventory: %s(%d %s) into Bin (%s)",
                                product.getProductName(), stockEvent.getQuantity(), product.getUnit(), binStorage.getBarcode())
                        ));
            }

        }
    }
}
