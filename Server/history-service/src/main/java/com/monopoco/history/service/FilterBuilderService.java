package com.monopoco.history.service;

/**
 * Project: Server
 * Package: com.monopoco.history.service
 * Author: hungdq
 * Date: 15/04/2024
 * Time: 16:51
 */
import com.monopoco.history.enums.FilterOperationEnum;
import com.monopoco.history.exceptions.BadRequestException;
import com.monopoco.history.filter.FilterCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service for Filtering Page
 * This class is used to extract any filters requested by the client.
 */
@Service
@Slf4j
public class FilterBuilderService {

    private static final int DEFAULT_SIZE_PAGE = 20;


    /**
     * Prepare filter condition.  extract the different filters used in the controller via @RequestParam
     *
     * @param criteria search Criteria.
     * @return a list of {@link com.monopoco.history.filter.FilterCondition}
     */
    public List<FilterCondition> createFilterCondition(String criteria) {
        List<FilterCondition> filters = new ArrayList<>();
        try {
            if (criteria != null && !criteria.isEmpty()) {
                final String FILTER_SHEARCH_DELIMITER = "&";
                final String FILTER_CONDITION_DELIMITER = "\\|";

                List<String> values = split(criteria, FILTER_SHEARCH_DELIMITER);
                log.error("{}", values);
                if (!values.isEmpty()) {
                    values.forEach(x -> {
                        List<String> filter = split(x, FILTER_CONDITION_DELIMITER);
                        if (FilterOperationEnum.fromValue(filter.get(1)) != null) {
                            if (filter.get(1).endsWith("num")) {
                                filters.add(new FilterCondition(filter.get(0), FilterOperationEnum.fromValue(filter.get(1)), Double.valueOf(filter.get(2))));
                            } else if (filter.get(1).endsWith("date")) {
                                log.error("new date, {}", new Date(Long.parseLong(filter.get(2))));
                                filters.add(new FilterCondition(filter.get(0), FilterOperationEnum.fromValue(filter.get(1)), new Date(Long.parseLong(filter.get(2)))));
                            } else {
                                filters.add(new FilterCondition(filter.get(0), FilterOperationEnum.fromValue(filter.get(1)), filter.get(2)));
                            }
                        }
                    });
                }
            }

            return filters;

        } catch (Exception ex) {
            throw new BadRequestException("Cannot create condition filter " + ex.getMessage());
        }

    }


    private static List<String> split(String search, String delimiter) {
        return Stream.of(search.split(delimiter))
                .collect(Collectors.toList());
    }


    /**
     * Get request pageable. Page Request Builder. custom pageable
     *
     * @param size  the number of items to collect
     * @param page  page number
     * @param order search order filter (eg: field|ASC)
     * @return PageRequest
     */
    public PageRequest getPageable(int size, int page, String order) {

        int pageSize = (size <= 0) ? DEFAULT_SIZE_PAGE : size;
        int currentPage = (page <= 0) ? 1 : page;

        try {
            if (order != null && !order.isEmpty()) {

                final String FILTER_CONDITION_DELIMITER = "\\|";

                List<String> values = split(order, FILTER_CONDITION_DELIMITER);
                String column = values.get(0);
                String sortDirection = values.get(1);

                if (sortDirection.equalsIgnoreCase("ASC")) {
                    return PageRequest.of((currentPage - 1), pageSize, Sort.by(Sort.Direction.ASC, column));
                } else if (sortDirection.equalsIgnoreCase("DESC")) {
                    return PageRequest.of((currentPage - 1), pageSize, Sort.by(Sort.Direction.DESC, column));
                } else {
                    throw new IllegalArgumentException(String.format("Value for param 'order' is not valid : %s , must be 'asc' or 'desc'", sortDirection));
                }

            } else {
                return PageRequest.of((currentPage - 1), pageSize);
            }
        } catch (Exception ex) {
            throw new BadRequestException("Cannot create condition filter " + ex.getMessage());
        }
    }


}
