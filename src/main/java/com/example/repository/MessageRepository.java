package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;

/**
 * MessageRepository is a repository class that handles operations in the DAO layer for
 * the message table.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    /**
     * Property expression for finding the message with a given accountId value.
     * @param  messageId The messageId value that is being searched for.
     * @return Returns the Message that has the given messageId.
     */
    Message findByMessageId(int messageId);
    
    /**
     * Property expression for finding all Messages with a given postedBy value.
     * @param  postedBy The postedBy value that is being search for.
     * @return Returns a list of all messages with the given postedBy value.
     */
    List<Message> findAllByPostedBy(int postedBy);
    
    /**
     * Property expression for deleting a message with a given messageId. Method must be
     * annotated with @Transactional
     * @param  messageId The messageId value that is being searched for.
     * @return Returns the number of deleted messages.
     */
    @Transactional
    Long deleteByMessageId(int messageId);

}
