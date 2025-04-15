package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.exception.DataIntegrityViolationException;
import com.example.exception.InvalidLoginException;
import com.example.exception.InvalidModelFieldValuesException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 /**
  * A controller class that exposes endpoints and adds the proper handler method to each endpoint. This class also
  * defines exception handlers for potential exceptions that can be thrown by the endpoint handler methods.
  * @author Broden Horton
  */
@RestController
public class SocialMediaController {
    /**
     * Service class for Accounts. Holds business logic for interacting with Accounts in the DAO layer.
     */
    AccountService accountService;
    /**
     * Service class for Messages. Holds business logic for interacting with Messages in the DAO layer.
     */
    MessageService messageService;

    /**
     * Parameterized constructor annotated with @Autowired so Spring inject the AccountService and MessageService
     * dependencies.
     * @param accountService
     * @param messageService
     */
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Exposes the "/register" endpoint for POST requests. Registers a new account that is supplied in
     * the request body.
     * @param  account The account to be registered.
     * @return A ResponseEntity<Account> which represents the HTTP reponse. Includes a status code of
     *         200 (OK) and a response body that includes the newly registered account.
     */
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        Account registeredAccount = accountService.registerAccount(account);
        return ResponseEntity.status(200).body(registeredAccount);
    }

    /**
     * Exposes the "/login" endpoint for POST requests. Returns an Account that matches the credentials
     * specified in the request body.
     * @param  account The account attempting to be logged into.
     * @return A ResponseEntity<Account> which represents the HTTP reponse. Includes a status code of
     *         200 (OK) and a response body that includes the logged into account.
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account loginAccount = accountService.login(account);
        return ResponseEntity.status(200).body(loginAccount);
    }

    /**
     * Exposes the "/messages" endpoint for POST requests. Adds a new message that is specified in the
     * request body.
     * @param  message The message being posted to the social media app.
     * @return A ResponseEntity<Message> which represents the HTTP reponse. Includes a status code of
     *         200 (OK) and a response body that includes the newly added message.
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message postedMessage = messageService.createMessage(message);
        return ResponseEntity.status(200).body(postedMessage);
    }

    /**
     * Exposes the "/messages" endpoint for GET requests. Returns all messages stored in the database.
     * @return A ResponseEntity<List<Message>> which represents the HTTP reponse. Includes a status code of
     *         200 (OK) and a response body that includes all messages in the database.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    /**
     * Exposes the "/messages/{messageId}" endpoint for GET requests. Returns a Message that matches the messageId
     * path variable.
     * @param  messageId The id of the message that should be returned.
     * @return A ResponseEntity<Message> which represents the HTTP reponse. Includes a status code of 200 (OK)
     *         and a response body that includes the message with a matching messageId.
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
    }

    /**
     * Exposes the "/messages/{messageId}" endpoint for DELETE requests. Returns the number of Messages deleted
     * from the database.
     * @param  messageId The id of the message that should be deleted.
     * @return A ResponseEntity<Message> which represents the HTTP reponse. Includes a status code of 200 (OK)
     *         and a response body that includes the message that was deleted.
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId) {
        return ResponseEntity.status(200).body(messageService.deleteMessageById(messageId));
    }

    /**
     * Exposes the "/messages/{messageId}" endpoint for PATCH requests. Updates the message text of messages with a
     * matching messageId. Returns the number of Messages updated.
     * @param  messageId The id of the message that should have its messageText updated.
     * @param  message The message with the new messageText.
     * @return A ResponseEntity<Integer> which represents the HTTP reponse. Includes a status code of 200 (OK)
     *         and a response body that includes the number of updated Messages.
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageTextById(@PathVariable int messageId, @RequestBody Message message) {
        return ResponseEntity.status(200).body(messageService.updateMessageTextById(messageId, message.getMessageText()));
    }

    /**
     * Exposes the "/accounts/{accountId}/messages" endpoint for GET requests. Returns all Messages that were posted by
     * the given accountId.
     * @param  accountId The id of a given account.
     * @return A ResponseEntity<List<Message>> which represents the HTTP reponse. Includes a status code of 200 (OK)
     *         and a response body that includes all the messages with a postedBy value that matches the given accountId.
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByPostedBy(@PathVariable int accountId) {
        return ResponseEntity.status(200).body(messageService.getAllMessagesByPostedBy(accountId));
    }

    /**
     * Exception handler for a DataIntegrityViolationException. Returns a 409 (Conflict) status code and
     * an exception message.
     * @param  ex The DataIntegrityViolationException that was thrown.
     * @return A String of the exception message.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ex.getMessage();
    }

    /**
     * Exception handler for a InvalidModelFieldValuesException. Returns a 400 (Client Error) status code and
     * an exception message.
     * @param  ex The InvalidModelFieldValuesException that was thrown.
     * @return A String of the exception message.
     */
    @ExceptionHandler(InvalidModelFieldValuesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidModelFieldValuesException(InvalidModelFieldValuesException ex) {
        return ex.getMessage();
    }

    /**
     * Exception handler for a InvalidLoginException. Returns a 401 (Unauthorized) status code and
     * an exception message.
     * @param  ex The InvalidLoginException that was thrown.
     * @return A String of the exception message.
     */
    @ExceptionHandler(InvalidLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleInvalidLoginException(InvalidLoginException ex) {
        return ex.getMessage();
    }

}
