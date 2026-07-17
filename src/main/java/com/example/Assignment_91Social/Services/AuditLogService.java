package com.example.Assignment_91Social.Services;

import com.example.Assignment_91Social.Models.AuditLog;
import com.example.Assignment_91Social.Repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // Write one audit record - called by other services whenever something important happens
    public void log(Long userId, String action, Long entityId) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUserId(userId);
        auditLog.setAction(action);
        auditLog.setEntityId(entityId);
        auditLog.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(auditLog);
    }

    // History of everything that happened to one record (e.g. one Part's price changes)
    public List<AuditLog> getHistoryForEntity(Long entityId) {
        return auditLogRepository.findByEntityIdOrderByTimestampDesc(entityId);
    }

    // History of everything one user did (admin review)
    public List<AuditLog> getHistoryForUser(Long userId) {
        return auditLogRepository.findByUserIdOrderByTimestampDesc(userId);
    }
}