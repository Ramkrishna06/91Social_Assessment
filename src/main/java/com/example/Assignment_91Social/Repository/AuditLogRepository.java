package com.example.Assignment_91Social.Repository;


import com.example.Assignment_91Social.Models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByEntityIdOrderByTimestampDesc(Long entityId);

    List<AuditLog> findByUserIdOrderByTimestampDesc(Long userId);
}