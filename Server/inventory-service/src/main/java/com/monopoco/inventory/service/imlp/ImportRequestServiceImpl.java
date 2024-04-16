package com.monopoco.inventory.service.imlp;

import com.monopoco.inventory.clients.AuthClient;
import com.monopoco.inventory.clients.WarehouseClient;
import com.monopoco.inventory.entity.ImportExportDetail;
import com.monopoco.inventory.entity.ImportRequest;
import com.monopoco.inventory.entity.Status;
import com.monopoco.inventory.filter.ImportRequestFilter;
import com.monopoco.inventory.repository.ImportExportDetailRepository;
import com.monopoco.inventory.repository.ImportRequestRepository;
import com.monopoco.inventory.repository.ImportRequestRepositoryDSL;
import com.monopoco.inventory.request.ImportRequestBody;
import com.monopoco.inventory.request.RequestDetail;
import com.monopoco.inventory.response.CommonResponse;
import com.monopoco.inventory.response.PageResponse;
import com.monopoco.inventory.response.model.ImportRequestDTO;
import com.monopoco.inventory.response.model.UserDTO;
import com.monopoco.inventory.response.model.WarehouseDTO;
import com.monopoco.inventory.service.ImportRequestService;
import com.monopoco.inventory.service.ProductInventoryService;
import com.monopoco.inventory.util.CommonUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.service.imlp
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 16:10
 */

@Service
@Transactional
public class ImportRequestServiceImpl implements ImportRequestService {

    @Autowired
    private ImportRequestRepository importRequestRepository;

    @Autowired
    private ProductInventoryService productInventoryService;

    @Autowired
    private ImportExportDetailRepository importExportDetailRepository;

    @Autowired
    private ImportRequestRepositoryDSL importRequestRepositoryDSL;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private WarehouseClient warehouseClient;

    @Override
    public CommonResponse<?> pushNewRequest(ImportRequestBody requestBody) {
        ImportRequest importRequest = importRequestRepository.findByIsDeletedIsFalseAndImportRequestCode(
                requestBody.getImportRequestCode()
        ).orElse(null);
        if (importRequest == null) {
            importRequest = requestBody.mapToEntity();
            importRequest.setStatus(Status.DRAFT);
            importRequestRepository.save(importRequest);
            for (RequestDetail requestDetail : requestBody.getRequestDetailList()) {
                requestDetail.setRequestId(importRequest.getId());
                requestDetail.setType(0);
                createNewImportExportDetail(requestDetail);
            }
            return new CommonResponse<>().success("Create request successfully");
        } else {
            return new CommonResponse<>().badRequest("Request Code Existed");
        }
    }

    @Override
    public boolean createNewImportExportDetail(RequestDetail requestDetail) {
        ImportExportDetail detail = ImportExportDetail.builder()
                .id(CommonUtil.generateRandomUUID())
                .productId(requestDetail.getProductId())
                .productName(requestDetail.getProductName())
                .type(requestDetail.getType())
                .quantity(requestDetail.getQuantity())
                .requestId(requestDetail.getRequestId())
                .build();

        importExportDetailRepository.save(detail);
        return true;
    }

    @Override
    public CommonResponse<PageResponse<?>> getImportRequestList(Pageable pageable, ImportRequestFilter filter) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (filter.getWarehouseName() == null) {
            if (auth != null) {
                String roleName = auth.getAuthorities().stream().findFirst().get().toString();
                if (roleName.equals("ROLE_SUPER_ADMIN") || roleName.equals("ROLE_ADMIN")) {
                    filter.setWarehouseName(null);
                } else if (roleName.equals("ROLE_WAREHOUSE_MANAGER")) {
                    UUID userId = (UUID) ((Map<String, Object>) auth.getPrincipal()).get("id");
                    if (userId != null) {
                        UserDTO userDTO = Objects.requireNonNull(authClient.getUser(userId, "Bearer hungdzqua").getBody()).getData();
                        if (userDTO.getWarehouseId() != null) {
                            WarehouseDTO warehouseDTO = warehouseClient.getWarehouse(userDTO.getWarehouseId(), "Bearer hungdzqua").getBody().getData();
                            if (warehouseDTO != null) {
                                filter.setWarehouseName(warehouseDTO.getWarehouseName());
                            }
                        }

                    }
                }
            }
        }
        PageResponse<List<ImportRequestDTO>> pageResponse = importRequestRepositoryDSL.searchOrder(filter, pageable);
        return new CommonResponse<>().success().data(pageResponse);
    }
}
