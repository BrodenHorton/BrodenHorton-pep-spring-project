package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findByMessageId(int messageId);
    Long deleteByMessageId(int messageId);
    @Modifying
    @Query("update Message m set m.messageText = ?1 where m.messageId = ?2")
    Long setMessageTextByMessageId(String messageText, int messageId);
}
