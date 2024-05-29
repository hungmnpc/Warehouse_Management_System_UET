package com.monopoco.history.service;

import com.monopoco.common.model.HistoryEvent;
import com.monopoco.history.entity.History;
import com.monopoco.history.repository.HistoryRepository;
import com.monopoco.history.response.CommonResponse;
import com.monopoco.history.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Project: Server
 * Package: com.monopoco.history.service
 * Author: hungdq
 * Date: 15/04/2024
 * Time: 16:57
 */
@Service
public class HistoryServiceImpl implements HistoryService{

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public List<History> getAll(Query query) {

        return historyRepository.findAll(query);
    }

    @Override
    public PageResponse<History> getPage(Query query, Pageable pageable) {
        Page<History> page = historyRepository.findAll(query, pageable);
        PageResponse<History> response = new PageResponse<>()
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .data(page.get())
                .dataCount(page.getTotalElements());
        return response;

    }

    @Override
    public CommonResponse<?> save(HistoryEvent historyEvent) {
        History history = History.builder()
                .type(historyEvent.getType())
                .username(historyEvent.getUsername())
                .userId(historyEvent.getUserId().toString())
                .agentId(historyEvent.getAgentId().toString())
                .agentType(historyEvent.getAgentType())
                .content(historyEvent.getContent())
                .ts(System.currentTimeMillis())
                .build();
        historyRepository.save(history);
        return new CommonResponse<>().success().data("Success");
    }
}
