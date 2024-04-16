package com.monopoco.history.repository;

import com.monopoco.history.entity.History;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.history.repository
 * Author: hungdq
 * Date: 04/04/2024
 * Time: 17:00
 */

@Repository
public interface HistoryRepository extends ResourceRepository<History, ObjectId> {

}
