package com.monopoco.inventory.repository;

import com.monopoco.common.model.PageResponse;
import com.monopoco.inventory.filter.ImportRequestFilter;
import com.monopoco.inventory.response.model.ImportRequestDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Project: Server
 * Package: com.monopoco.inventory.repository
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 17:15
 */
public interface ImportRequestRepositoryDSL {

    public PageResponse<List<ImportRequestDTO>> searchOrder(ImportRequestFilter filter, Pageable pageable);
}
