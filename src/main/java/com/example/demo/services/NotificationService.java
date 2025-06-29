package com.example.demo.services;

import com.example.demo.entities.Notification;
import com.example.demo.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService extends BasicService<Notification> {
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    protected JpaRepository<Notification, Long> getRepository() {
        return notificationRepository;
    }
} 