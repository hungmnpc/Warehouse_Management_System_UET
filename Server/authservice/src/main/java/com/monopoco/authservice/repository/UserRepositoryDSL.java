package com.monopoco.authservice.repository;

import com.monopoco.authservice.filter.UserFilter;
import com.monopoco.common.model.PageResponse;
import com.monopoco.common.model.user.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryDSL {

    public PageResponse<List<UserDTO>> searchOrder(UserFilter filter, Pageable pageable);
}
