package com.monopoco.warehouse.response;

import com.monopoco.warehouse.response.model.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.authservice.response
 * Author: hungdq
 * Date: 22/03/2024
 * Time: 18:41
 */
public class UserPrincipal implements UserDetails {

    private UserDTO userDTO;

    public UserPrincipal(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

//    public UserPrincipal(UserEntity userEntity) {
//        this.userDTO = UserDTO.builder()
//                .id(userEntity.getId())
//                .userName(userEntity.getUserName())
//                .firstName(userEntity.getFirstName())
//                .lastName(userEntity.getLastName())
//                .roleName(userEntity.getRole().getRoleName())
//                .build();
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userDTO.getRoleName()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return "Can't access to this property";
    }

    @Override
    public String getUsername() {
        return userDTO.getUserName();
    }

    public String getFullName() {
        return  userDTO.getFirstName() + " " + userDTO.getLastName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UUID getId() {
        return userDTO.getId();
    }
}
