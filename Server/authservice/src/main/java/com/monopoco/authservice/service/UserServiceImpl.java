package com.monopoco.authservice.service;

import com.ctc.wstx.util.StringUtil;
import com.monopoco.authservice.entity.RoleEntity;
import com.monopoco.authservice.entity.UserEntity;
import com.monopoco.authservice.filter.UserFilter;
import com.monopoco.authservice.repository.RoleEntityRepository;
import com.monopoco.authservice.repository.UserEntityRepository;
import com.monopoco.authservice.repository.UserRepositoryDSL;
import com.monopoco.authservice.request.LoginRequest;
import com.monopoco.authservice.request.UserRequest;
import com.monopoco.authservice.response.CommonResponse;
import com.monopoco.authservice.response.PageResponse;
import com.monopoco.authservice.response.UserPrincipal;
import com.monopoco.authservice.response.model.LoginResponse;
import com.monopoco.authservice.response.model.RoleDTO;
import com.monopoco.authservice.response.model.UserDTO;
import com.monopoco.authservice.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.authservice.service
 * Author: hungdq
 * Date: 21/03/2024
 * Time: 17:25
 */

@Service
@Slf4j
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
                RoleEntity role =roleRepo.findById(request.getRoleId()).orElse(null);
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
                        timeExp, httpRequest
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
    public CommonResponse<?> deleteUser(UUID userId) {
        return null;
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
//        String roleName = ((ArrayList<SimpleGrantedAuthority>)authentication.getAuthorities()).get(0).getAuthority();
        auth.getAuthorities().stream().forEach(a -> {
            System.out.println(a.getAuthority());
        });
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_SUPER_ADMIN"))) {
            PageResponse<List<UserDTO>> response = userRepositoryDSL.searchOrder(filter, pageable);
            return new CommonResponse<>().success().data(response);
        }
        return new CommonResponse<>().badRequest("Không đủ quyền truy cập");
    }
}
