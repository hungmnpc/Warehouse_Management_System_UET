package com.monopoco.history.controller;

import com.monopoco.common.model.HistoryEvent;
import com.monopoco.history.entity.History;
import com.monopoco.history.filter.FilterCondition;
import com.monopoco.history.repository.impl.GenericFilterCriteriaBuilder;
import com.monopoco.history.response.CommonResponse;
import com.monopoco.history.response.PageResponse;
import com.monopoco.history.service.FilterBuilderService;
import com.monopoco.history.service.HistoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Project: Server
 * Package: com.monopoco.history.controller
 * Author: hungdq
 * Date: 04/04/2024
 * Time: 17:32
 */

@RestController
@RequestMapping("/histories")
@CrossOrigin("*")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private FilterBuilderService filterBuilderService;


    @PostMapping("")
    public ResponseEntity<?> saveNewHistory(
            @RequestBody HistoryEvent historyEvent
    ) {
        try {
            return ResponseEntity.ok(historyService.save(
                    historyEvent
            ));
        } catch (Exception exception) {
            exception.printStackTrace();
            return internalServerError().body("Server xảy ra lỗi");
        }
    }



    @GetMapping(value = "/page")
    public ResponseEntity<PageResponse<History>> getSearchCriteriaPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9999") int size,
            @RequestParam(value = "filterOr", required = false) String filterOr,
            @RequestParam(value = "filterAnd", required = false) String filterAnd,
            @RequestParam(value = "orders", required = false) String orders) {
        Pageable pageable = filterBuilderService.getPageable(size, page, orders);
        GenericFilterCriteriaBuilder filterCriteriaBuilder = new GenericFilterCriteriaBuilder();


        List<FilterCondition> andConditions = filterBuilderService.createFilterCondition(filterAnd);
        List<FilterCondition> orConditions = filterBuilderService.createFilterCondition(filterOr);

        Query query = filterCriteriaBuilder.addCondition(andConditions, orConditions);

        log.error("query: {}", query);
        PageResponse<History> pg = historyService.getPage(query, pageable);

        return new ResponseEntity<>(pg, HttpStatus.OK);
    }

    /**
     * @param filterOr  string filter or conditions
     * @param filterAnd string filter and conditions
     * @return list of Employee
     */
    @GetMapping("")
    public ResponseEntity<List<History>> getAllSearchCriteria(
            @RequestParam(value = "filterOr", required = false) String filterOr,
            @RequestParam(value = "filterAnd", required = false) String filterAnd) {

        GenericFilterCriteriaBuilder filterCriteriaBuilder = new GenericFilterCriteriaBuilder();

        List<FilterCondition> andConditions = filterBuilderService.createFilterCondition(filterAnd);
        List<FilterCondition> orConditions = filterBuilderService.createFilterCondition(filterOr);

        Query query = filterCriteriaBuilder.addCondition(andConditions, orConditions);
        List<History> employees = historyService.getAll(query);

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
