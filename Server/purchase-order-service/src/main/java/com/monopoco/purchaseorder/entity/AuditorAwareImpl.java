package com.monopoco.purchaseorder.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Project: Server
 * Package: com.monopoco.authservice.entity
 * Author: hungdq
 * Date: 21/03/2024
 * Time: 17:57
 */
@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Optional.of("anonymous");
        }
        return Optional.ofNullable(((Map<String, Object>)authentication.getPrincipal()).get("username"));
    }
}
