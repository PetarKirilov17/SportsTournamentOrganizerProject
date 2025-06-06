package com.example.demo.repositories;

import com.example.demo.entities.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
} 