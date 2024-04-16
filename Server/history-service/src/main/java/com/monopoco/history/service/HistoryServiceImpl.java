package com.monopoco.history.service;

import com.monopoco.history.entity.History;
import com.monopoco.history.repository.HistoryRepository;
import com.monopoco.history.response.CommonResponse;
import com.monopoco.history.response.PageResponse;
import com.monopoco.history.response.model.HistoryDTO;
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
    public CommonResponse<?> save(HistoryDTO historyDTO) {
        History history = History.builder()
                .type(historyDTO.getType())
                .ts(Instant.now().getEpochSecond())
                .title(historyDTO.getTitle())
                .agentType(historyDTO.getAgentType())
                .userId(historyDTO.getUser().getId().toString())
                .description(historyDTO.getDescription())
                .agentId(historyDTO.getAgentId().toString())
                .build();
        historyRepository.save(history);
        return new CommonResponse<>().success().data("Success");
    }
}
