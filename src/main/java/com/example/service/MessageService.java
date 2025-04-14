package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidModelFieldValuesException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        if(message.getMessageText().equals("") || message.getMessageText().length() > 255) {
            throw new InvalidModelFieldValuesException("Message text must be between 1 and 255 characters.");
        }
        if(accountRepository.findByAccountId(message.getPostedBy()) == null) {
            throw new InvalidModelFieldValuesException("Posted by user could not be found.");
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        return messageRepository.findByMessageId(id);
    }

}
