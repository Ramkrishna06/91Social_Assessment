package com.example.Assignment_91Social.Controller;

import com.example.Assignment_91Social.Models.AuditLog;
import com.example.Assignment_91Social.Services.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @Autowired
    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping("/entity/{entityId}")
    public ResponseEntity<List<AuditLog>> getHistoryForEntity(@PathVariable Long entityId) {
        return ResponseEntity.ok(auditLogService.getHistoryForEntity(entityId));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLog>> getHistoryForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(auditLogService.getHistoryForUser(userId));
    }
}