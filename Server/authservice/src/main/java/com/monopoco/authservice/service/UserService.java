package com.monopoco.authservice.service;

import com.monopoco.authservice.filter.UserFilter;
import com.monopoco.authservice.request.LoginRequest;
import com.monopoco.authservice.request.UserRequest;
import com.monopoco.authservice.response.model.DropDown;
import com.monopoco.authservice.response.model.LoginResponse;
import com.monopoco.authservice.response.model.RoleDTO;
import com.monopoco.common.model.CommonResponse;
import com.monopoco.common.model.PageResponse;
import com.monopoco.common.model.user.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {

    /**
     * @param roleName
     * @return
     */
    public CommonResponse<RoleDTO> saveNewRole(String roleName);

    public CommonResponse<UserDTO> saveNewUser(UserRequest request);

    public CommonResponse<LoginResponse> login(LoginRequest request, HttpServletRequest httpRequest);

    /**
     *
     * @param userId
     * @return
     */
    public CommonResponse<?> deleteUser(UUID userId);

    public CommonResponse<UserDTO> getUser(UUID userId);

    /**
     *
     * @param userID
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public String updatePassword(UUID userID, String oldPassword, String newPassword);

    /**
     *
     * @param username
     * @param roleName
     */
    public void setRoleForUser(String username, String roleName);

    public CommonResponse<PageResponse<List<UserDTO>>> getAllUser(UserFilter filter, Pageable pageable);

    public CommonResponse<PageResponse<List<DropDown<UUID, String>>>> getRoleDropDown();

}
