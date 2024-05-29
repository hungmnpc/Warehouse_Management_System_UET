package com.monopoco.inventory.kafka;

import com.monopoco.common.model.inventory.PickEvent;
import com.monopoco.common.model.inventory.StockEvent;
import com.monopoco.inventory.clients.WarehouseClient;
import com.monopoco.inventory.entity.ProductBinInventory;
import com.monopoco.inventory.entity.ProductInventory;
import com.monopoco.inventory.repository.ProductBinInventoryRepository;
import com.monopoco.inventory.repository.ProductInventoryRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project: Server
 * Package: com.monopoco.history.kafka
 * Author: hungdq
 * Date: 25/04/2024
 * Time: 11:39
 */

@Service
@Transactional
public class InventoryConsumer {

    private static final Logger log = LoggerFactory.getLogger(InventoryConsumer.class);

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    @Autowired
    private ProductBinInventoryRepository productBinInventoryRepository;

    @Autowired
    private WarehouseClient warehouseClient;

    @Autowired
    private Environment env;

    @KafkaListener(topics = "pick", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(PickEvent pickEvent) {
        if (pickEvent != null && pickEvent.getProductId() != null) {
            log.info(String.format("Received Service => %s", pickEvent.toString()));
            ProductInventory productInventory = productInventoryRepository.findByIsDeletedIsFalseAndProductIdAndWarehouseId(
                    pickEvent.getProductId(), pickEvent.getWarehouseId()
            ).orElse(null);
            if (productInventory != null) {
                productInventory.setQuantityAvailable(productInventory.getQuantityAvailable() - pickEvent.getQuantity());
            }

            List<ProductBinInventory> productBinInventories = productBinInventoryRepository.findAllByBinId(pickEvent.getBinId());
            if (productBinInventories.size() == 0) {
                warehouseClient.updateOccupied(pickEvent.getBinId(), false, env.getProperty("superTokenWithBeare"));
            }
        }
    }
}
