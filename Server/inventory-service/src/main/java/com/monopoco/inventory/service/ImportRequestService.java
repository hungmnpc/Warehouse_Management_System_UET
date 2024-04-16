package com.monopoco.inventory.service;

import com.monopoco.inventory.filter.ImportRequestFilter;
import com.monopoco.inventory.request.ImportRequestBody;
import com.monopoco.inventory.request.RequestDetail;
import com.monopoco.inventory.response.CommonResponse;
import com.monopoco.inventory.response.PageResponse;
import org.springframework.data.domain.Pageable;

/**
 * Project: Server
 * Package: com.monopoco.inventory.service
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 15:51
 */
public interface ImportRequestService {

    public CommonResponse<?> pushNewRequest(ImportRequestBody requestBody);

    public boolean createNewImportExportDetail(RequestDetail requestDetail);

    public CommonResponse<PageResponse<?>> getImportRequestList(Pageable pageable, ImportRequestFilter filter);
}