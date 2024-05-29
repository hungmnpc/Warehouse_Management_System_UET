package com.monopoco.history.service;

import com.monopoco.common.model.HistoryEvent;
import com.monopoco.history.entity.History;
import com.monopoco.history.response.CommonResponse;
import com.monopoco.history.response.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface HistoryService {
    List<History> getAll(Query query);

    PageResponse<History> getPage(Query query, Pageable pageable);

    CommonResponse<?> save(HistoryEvent historyEvent);

}
