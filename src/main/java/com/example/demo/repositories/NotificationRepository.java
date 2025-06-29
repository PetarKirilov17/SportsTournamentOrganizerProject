package com.example.demo.repositories;

import com.example.demo.entities.Notification;
import com.example.demo.enums.NotificationStatus;
import com.example.demo.enums.NotificationType;
import com.example.demo.enums.RecipientType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    // Derived query methods - using correct property names from Notification entity
    List<Notification> findByRecipientId(Long recipientId);
    List<Notification> findByRecipientType(RecipientType recipientType);
    List<Notification> findByType(NotificationType type);
    List<Notification> findByStatus(NotificationStatus status);
    List<Notification> findBySentAtAfter(LocalDateTime date);
    List<Notification> findBySentAtBefore(LocalDateTime date);
    List<Notification> findBySentAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Notification> findByRecipientIdAndStatus(Long recipientId, NotificationStatus status);
    List<Notification> findByRecipientTypeAndStatus(RecipientType recipientType, NotificationStatus status);
    List<Notification> findByTypeAndStatus(NotificationType type, NotificationStatus status);
} 