package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findByMessageId(int messageId);
    
    @Transactional
    Long deleteByMessageId(int messageId);
}
