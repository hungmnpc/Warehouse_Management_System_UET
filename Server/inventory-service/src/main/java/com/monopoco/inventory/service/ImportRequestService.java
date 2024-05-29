package com.monopoco.inventory.service;

import com.monopoco.common.model.CommonResponse;
import com.monopoco.common.model.PageResponse;
import com.monopoco.inventory.filter.ImportRequestFilter;
import com.monopoco.inventory.request.ImportRequestBody;
import com.monopoco.inventory.request.RequestDetail;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.service
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 15:51
 */
public interface ImportRequestService {

    public CommonResponse<?> pushNewRequest(ImportRequestBody requestBody);

    public CommonResponse<?> createNewImportFromPo(UUID poId);

    public boolean createNewImportExportDetail(RequestDetail requestDetail);

    public CommonResponse<PageResponse<?>> getImportRequestList(Pageable pageable, ImportRequestFilter filter);
}