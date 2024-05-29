package com.monopoco.product.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monopoco.common.model.CommonResponse;
import com.monopoco.product.kafka.ProductProducer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.util.*;

/**
 * Project: Server
 * Package: com.monopoco.authservice.filter
 * Author: hungdq
 * Date: 25/03/2024
 * Time: 15:05
 */
@Slf4j
@Component
public class CustomerAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private Environment env;

    @Autowired
    private ProductProducer productProducer;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        if (requestWrapper.getServletPath().equals("/login")) {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } else {
            String authorizationHeader = requestWrapper.getHeader(AUTHORIZATION);

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Map<String, Object> principal = new HashMap<>();
                    if (token.equals(Objects.requireNonNull(env.getProperty("superToken")))) {
                        principal.put("username", "SuperAdmin");
                        principal.put("id", UUID.fromString("1c51b706-5253-4425-9b77-deeb5969a3e6"));
                        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
                        authorities.add(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"));
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(principal, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        filterChain.doFilter(requestWrapper, responseWrapper);
                    } else {
                        Algorithm algorithm = Algorithm.HMAC256(Objects.requireNonNull(env.getProperty("secret")).getBytes());
                        JWTVerifier verifier = JWT.require(algorithm).build();
                        DecodedJWT decodedJWT = verifier.verify(token);
                        String username = decodedJWT.getSubject();
                        UUID id = decodedJWT.getClaim("id").as(UUID.class);
                        principal.put("username", username);
                        principal.put("id", id);
                        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        stream(roles).forEach(role -> {
                            authorities.add(new SimpleGrantedAuthority(role));
                        });
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(principal, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        filterChain.doFilter(requestWrapper, responseWrapper);
                    }
                } catch (Exception ex) {
                    log.error("Error logging in: {}", ex.getMessage());
                    response.setHeader("error", ex.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", ex.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(responseWrapper.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(requestWrapper, responseWrapper);
            }
        }

        byte[] responseContent = responseWrapper.getContentAsByteArray();
        String responseBody = new String(responseContent, response.getCharacterEncoding());
        if (requestWrapper.getServletPath().startsWith("/products")) {
            // Ghi lại hoặc sử dụng nội dung phản hồi
            System.out.println("Response Body: " + responseBody);
            ObjectMapper objectMapper = new ObjectMapper();
            CommonResponse<?> res = objectMapper.readValue(responseBody, CommonResponse.class);
            if (res.getHistory() != null) {
                productProducer.sendMessage(res.getHistory());
            }
        }
        // Sao chép nội dung vào phản hồi thực tế
        responseWrapper.copyBodyToResponse();
    }
}
