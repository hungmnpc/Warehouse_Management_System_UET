package com.monopoco.purchaseorder.kafka;

import com.monopoco.common.factory.HistoryFactory;
import com.monopoco.common.model.HistoryEvent;
import com.monopoco.common.model.inventory.StockEvent;
import com.monopoco.purchaseorder.service.PurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.history.kafka
 * Author: hungdq
 * Date: 25/04/2024
 * Time: 11:39
 */

@Service
public class PurchaseOrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderConsumer.class);

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderProducer purchaseOrderProducer;


    @KafkaListener(topics = "stock", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(StockEvent stockEvent) {
        if (stockEvent != null) {
            log.info(String.format("Received Service => %s", stockEvent.toString()));
            purchaseOrderService.stockPurchaseOrder(
                    stockEvent.getUserId(),
                    stockEvent.getUsername(),
                    stockEvent.getPurchaseOrderId(), stockEvent.getProductId(),
                    stockEvent.getQuantity()
            );
        }
    }
}
