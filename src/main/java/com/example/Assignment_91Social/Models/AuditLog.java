package com.example.Assignment_91Social.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;      //it tells who did it (just the id,)

    @Column(nullable = false)
    private String action;    // e.g."PRICE_UPDATED", "PART_ADDED"

    @Column(name = "entity_id", nullable = false)
    private Long entityId;    // teels aboout what has chaged

    @Column(nullable = false)
    private LocalDateTime timestamp;
}