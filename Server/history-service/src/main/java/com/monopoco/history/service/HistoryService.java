package com.monopoco.history.service;

import com.monopoco.history.entity.History;
import com.monopoco.history.response.CommonResponse;
import com.monopoco.history.response.PageResponse;
import com.monopoco.history.response.model.HistoryDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface HistoryService {
    List<History> getAll(Query query);

    PageResponse<History> getPage(Query query, Pageable pageable);

    CommonResponse<?> save(HistoryDTO historyDTO);

}
