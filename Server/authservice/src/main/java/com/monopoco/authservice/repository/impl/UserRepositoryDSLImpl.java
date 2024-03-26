package com.monopoco.authservice.repository.impl;

import com.monopoco.authservice.entity.QRoleEntity;
import com.monopoco.authservice.entity.QUserEntity;
import com.monopoco.authservice.filter.UserFilter;
import com.monopoco.authservice.repository.UserRepositoryDSL;
import com.monopoco.authservice.response.PageResponse;
import com.monopoco.authservice.response.model.QUserDTO;
import com.monopoco.authservice.response.model.UserDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Project: Server
 * Package: com.monopoco.authservice.repository.impl
 * Author: hungdq
 * Date: 25/03/2024
 * Time: 18:05
 */

@Repository
@Transactional
public class UserRepositoryDSLImpl implements UserRepositoryDSL {

    @PersistenceContext
    private EntityManager entityManager;

    private final QUserEntity userEntity = QUserEntity.userEntity;

    private final QRoleEntity roleEntity = QRoleEntity.roleEntity;

    @Override
    public PageResponse<List<UserDTO>> searchOrder(UserFilter filter, Pageable pageable) {
        JPAQuery<UserDTO> query = new JPAQuery<>(entityManager)
                .select(new QUserDTO(
                        userEntity.createdBy,
                        userEntity.createdDate,
                        userEntity.lastModifiedBy,
                        userEntity.lastModifiedDate,
                        userEntity.isDeleted,
                        userEntity.id,
                        userEntity.firstName,
                        userEntity.lastName,
                        userEntity.userName,
                        roleEntity.roleName
                ) )
                .from(userEntity)
                .innerJoin(roleEntity)
                .on(roleEntity.eq(userEntity.role))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(userEntity.isDeleted.isFalse());
        if (filter != null) {

        }
        query.where(booleanBuilder);
        List<UserDTO> result = query.fetch();
        long count = query.fetchCount();
        return new PageResponse<List<UserDTO>>()
                .data(result)
                .dataCount(count)
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize());
    }
}
