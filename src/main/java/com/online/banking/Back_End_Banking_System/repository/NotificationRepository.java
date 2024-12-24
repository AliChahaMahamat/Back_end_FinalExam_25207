package com.online.banking.Back_End_Banking_System.repository;

import com.online.banking.Back_End_Banking_System.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
}
