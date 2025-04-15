package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.InvalidModelFieldValuesException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

/**
 * MessageService is a service class that holds the business logic for working with Message objects.
 * @author Broden Horton
 */
@Service
public class MessageService {
    /**
     * Repository class for interacting with Messages in the DAO layer.
     */
    private MessageRepository messageRepository;
    /**
     * Repository class for interacting with Accounts in the DAO layer.
     */
    private AccountRepository accountRepository;

    /**
     * Parameterized constructor annotated with @Autowired so Spring injects the MessageRepository and
     * AccountRepository dependencies.
     * @param messageRepository
     * @param accountRepository
     */
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Registers a new message in the database.
     * @param  message The message that is being registered to the database.
     * @throws InvalidModelFieldValuesException when The messageText is blank or over 255 characters,
     *         and when the the postedBy id does not match to an existing account id.
     * @return Returns the message that was registered to the database.
     */
    public Message createMessage(Message message) {
        if(message.getMessageText().equals("") || message.getMessageText().length() > 255) {
            throw new InvalidModelFieldValuesException("Message text must be between 1 and 255 characters.");
        }
        if(accountRepository.findByAccountId(message.getPostedBy()) == null) {
            throw new InvalidModelFieldValuesException("Posted by user could not be found.");
        }

        return messageRepository.save(message);
    }

    /**
     * Returns all messages in the database.
     * @return Returns a list<Message> of all messages in the database
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Returns a message with a specified messageId.
     * @param  id The messageId being searched for.
     * @return Returns the Message with a messageId that matches id.
     */
    public Message getMessageById(int id) {
        return messageRepository.findByMessageId(id);
    }

    /**
     * Deletes a message with a specified id
     * @param  id The messageId of the message that will be deleted.
     * @return Returns the number of deleted messages.
     */
    public Integer deleteMessageById(int id) {
        long messagesDeleted = messageRepository.deleteByMessageId(id);
        return  messagesDeleted > 0 ? (int)messagesDeleted : null;
    }

    /**
     * Updates the messageText of a message with the messageId of id.
     * @param  id The messageId of the message being updated.
     * @param  messageText The updated text.
     * @throws InvalidModelFieldValuesException when the messageText is blank or over 255 characters,
     *         and when a message with the messageId of id does not exist.
     * @return Returns the number of updated messages.
     */
    public Integer updateMessageTextById(int id, String messageText) {
        if(messageText.equals("") || messageText.length() > 255) {
            throw new InvalidModelFieldValuesException("Message text must be between 1 and 255 characters.");
        }
        Message message = messageRepository.findByMessageId(id);
        if(message == null) {
            throw new InvalidModelFieldValuesException("User could not be found.");
        }

        message.setMessageText(messageText);
        messageRepository.save(message);
        return 1;
    }

    /**
     * Returns all messages posted by a given account id.
     * @param  postedBy The postedBy value being searched for.
     * @return Returns all messages with the given postedBy value.
     */
    public List<Message> getAllMessagesByPostedBy(int postedBy) {
        return messageRepository.findAllByPostedBy(postedBy);
    }

}
