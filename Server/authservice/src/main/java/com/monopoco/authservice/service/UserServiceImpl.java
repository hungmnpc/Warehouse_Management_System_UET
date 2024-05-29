package com.monopoco.authservice.service;

import com.monopoco.authservice.entity.RoleEntity;
import com.monopoco.authservice.entity.UserEntity;
import com.monopoco.authservice.filter.UserFilter;
import com.monopoco.authservice.repository.RoleEntityRepository;
import com.monopoco.authservice.repository.UserEntityRepository;
import com.monopoco.authservice.repository.UserRepositoryDSL;
import com.monopoco.authservice.request.LoginRequest;
import com.monopoco.authservice.request.UserRequest;
import com.monopoco.authservice.response.UserPrincipal;
import com.monopoco.authservice.response.model.DropDown;
import com.monopoco.authservice.response.model.LoginResponse;
import com.monopoco.authservice.response.model.RoleDTO;
import com.monopoco.authservice.util.CommonUtil;
import com.monopoco.authservice.util.PrincipalUser;
import com.monopoco.common.model.CommonResponse;
import com.monopoco.common.model.PageResponse;
import com.monopoco.common.model.user.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Project: Server
 * Package: com.monopoco.authservice.service
 * Author: hungdq
 * Date: 21/03/2024
 * Time: 17:25
 */

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private RoleEntityRepository roleRepo;

    @Autowired
    private UserEntityRepository userRepo;

    @Autowired
    private UserRepositoryDSL userRepositoryDSL;

    @Autowired
    private Environment env;

    @Override
    public CommonResponse<RoleDTO> saveNewRole(String roleName) {
        RoleEntity role = roleRepo.findByIsDeletedIsFalseAndRoleName(roleName).orElse(null);
        if (role != null) {
            return new CommonResponse<>().badRequest("Role này đã tồn tại");
        } else {
            RoleEntity newRole = RoleEntity.builder()
                    .id(CommonUtil.generateRandomUUID())
                    .roleName(roleName)
                    .build();
            RoleEntity savedRole = roleRepo.save(newRole);
            if (savedRole.getId() != null) {
                return new CommonResponse<>().success("Tạo mới role thành công").data(
                        RoleDTO.builder()
                                .id(savedRole.getId())
                                .roleName(savedRole.getRoleName())
                                .build()
                );
            } else {
                return new CommonResponse<>().badRequest("Lỗi không xác định");
            }
        }
    }

    @Override
    public CommonResponse<UserDTO> saveNewUser(UserRequest request) {
        if (request != null && request.getUserName() != null && !StringUtils.isEmpty(request.getUserName())) {
            UserEntity user = userRepo.findByIsDeletedIsFalseAndUserName(request.getUserName()).orElse(null);
            if (user != null) {
                return new CommonResponse<>().badRequest("Username đã tồn tại");
            } else {
                RoleEntity role =roleRepo.findById(request.getRole().getKey()).orElse(null);
                if (role != null) {
                    String salt = CommonUtil.generateRandomString(16);
                    String hashPassword = CommonUtil.hashPassword(request.getPassword(), salt);
                    UserEntity newUser = UserEntity.builder()
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .userName(request.getUserName())
                            .passwordHash(hashPassword)
                            .id(CommonUtil.generateRandomUUID())
                            .passwordSalt(salt)
                            .role(role)
                            .warehouseId(request.getWarehouse() == null ? null : request.getWarehouse().getKey())
                            .build();
                    UserEntity savedUserEntity = userRepo.save(newUser);
                    return new CommonResponse<>().success("Tạo mới user thành công").data(UserDTO.builder()
                            .userName(savedUserEntity.getUserName())
                            .firstName(savedUserEntity.getFirstName())
                            .lastName(savedUserEntity.getLastName())
                            .build());
                } else {
                    return new CommonResponse<>().badRequest("Role không tồn tại");
                }
            }
        } else {
            return new CommonResponse<>().badRequest("Cú pháp không chính xác");
        }
    }

    @Override
    public CommonResponse<LoginResponse> login(LoginRequest request, HttpServletRequest httpRequest) {
        UserEntity user = userRepo.findByIsDeletedIsFalseAndUserName(
                request.getUsername()
        ).orElse(null);
        if (user != null) {
            if (CommonUtil.checkPassword(request.getPassword(), user.getPasswordSalt(), user.getPasswordHash())) {
                UserPrincipal userPrincipal = new UserPrincipal(user);
                String secret = Objects.requireNonNull(env.getProperty("secret"));
                long timeExp = Long.parseLong(Objects.requireNonNull(env.getProperty("accessTokenExp")));
                String accessToken = CommonUtil.generateAccessToken(
                        userPrincipal, secret,
                        timeExp, httpRequest, user.getWarehouseId()
                );
                return new CommonResponse<>().success("Đăng nhập thành công").data(
                        LoginResponse.builder()
                                .accessToken(accessToken)
                                .loginDate(LocalDateTime.now())
                                .build()
                );
            } else {
                return new CommonResponse<>().badRequest("Mật khẩu không chính xác.");
            }
        } else {
            return new CommonResponse<>().badRequest("Không tồn tại tên người dùng này");
        }
    }

    @Override
    public CommonResponse<PageResponse<List<DropDown<UUID, String>>>> getRoleDropDown() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String roleName = auth.getAuthorities().stream().findFirst().get().toString();
            Integer level = roleRepo.getLevel(roleName);
            List<RoleEntity> roleList = roleRepo.findRoleEntitiesByIsDeletedIsFalseAndLevelGreaterThanOrderByLevelAsc(level);
            if (roleList.isEmpty()) {
                return new CommonResponse<>().success().data(new PageResponse<List<DropDown<UUID, String>>>()
                        .data(new ArrayList<>())
                        .dataCount(0L)
                        .pageNumber(0)
                        .pageSize(0));
            } else {
                List<DropDown<UUID, String>> roleDropDown = roleList.stream().map((role) -> {
                    return new DropDown<UUID, String>(role.getId(), role.getDisplayName());
                }).toList();

                return new CommonResponse<>().success().data(new PageResponse<List<DropDown<UUID, String>>>()
                        .data(roleDropDown)
                        .dataCount(roleDropDown.stream().count())
                        .pageNumber(0)
                        .pageSize(0));
            }
        }
        return null;
    }

    @Override
    public CommonResponse<?> deleteUser(UUID userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String roleName = auth.getAuthorities().stream().findFirst().get().toString();
            Integer level = roleRepo.getLevel(roleName);
            UserEntity deleteUser = userRepo.findById(userId).orElse(null);
            if (deleteUser != null) {
                if (deleteUser.getRole().getLevel() > level) {
                    deleteUser.setIsDeleted(true);
                    return new CommonResponse<>().success("Delete user successfully");
                } else {
                    return new CommonResponse<>().badRequest("You can't delete user with higher role");
                }
            } else {
                return new CommonResponse<>().notFound("Can't find user");
            }
        }
        return new CommonResponse<>().badRequest();
    }

    @Override
    public CommonResponse<UserDTO> getUser(UUID userId) {
        UserEntity userEntity = userRepo.findByIsDeletedIsFalseAndId(userId).orElse(null);
        if (userEntity != null) {
            return new CommonResponse<>().success().data(UserDTO.builder()
                    .id(userEntity.getId())
                    .userName(userEntity.getUserName())
                    .firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName())
                    .createdBy(userEntity.getCreatedBy())
                    .createdDate(userEntity.getCreatedDate())
                    .lastModifiedBy(userEntity.getLastModifiedBy())
                    .lastModifiedDate(userEntity.getLastModifiedDate())
                    .warehouseId(userEntity.getWarehouseId())
                    .build());
        }
        return new CommonResponse<>().notFound("User not found");
    }

    @Override
    public String updatePassword(UUID userID, String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public void setRoleForUser(String username, String roleName) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByIsDeletedIsFalseAndUserName(username).orElse(null);
        if (user == null) {
            log.warn("User {} not found in the database", username);
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", user.getUserName());
            UserDTO userDTO = UserDTO.builder()
                    .userName(user.getUserName())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .roleName(user.getRole().getRoleName()).build();
            return new UserPrincipal(userDTO);
        }
    }

    @Override
    public CommonResponse<PageResponse<List<UserDTO>>> getAllUser(UserFilter filter, Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")
                    || a.getAuthority().equals("ROLE_SUPER_ADMIN"))) {
            } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_WAREHOUSE_MANAGER"))) {

                PrincipalUser principalUser = CommonUtil.getRecentUser();
                filter.setWarehouseId(principalUser.getWarehouseId());
                filter.setRole("ROLE_EMPLOYEE");
            }
            PageResponse<List<UserDTO>> response = userRepositoryDSL.searchOrder(filter, pageable);
            return new CommonResponse<>().success().data(response);
        }
        return new CommonResponse<>().badRequest("Không đủ quyền truy cập");
    }


}
