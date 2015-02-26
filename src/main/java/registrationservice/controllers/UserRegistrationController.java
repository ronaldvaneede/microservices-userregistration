package registrationservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import registrationservice.models.RegisteredUser;
import registrationservice.models.RegistrationRequest;
import registrationservice.models.RegistrationResponse;
import registrationservice.notifications.UserDeletionNotification;
import registrationservice.notifications.UserNotification;
import registrationservice.notifications.UserRegistrationNotification;
import registrationservice.repositories.RegisteredUserRepository;

import java.util.List;

@RestController
public class UserRegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

    final static String queueName = "spring-boot";

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public RegistrationResponse registerUser(@RequestBody RegistrationRequest request) throws UserNotRegisteredException {
        final RegisteredUser user = new RegisteredUser(null, request.getEmailAddress(), request.getPassword());
        return register(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<RegisteredUser> getUsers() {
        final List<RegisteredUser> registeredUsers = registeredUserRepository.findAll();
        for (RegisteredUser registeredUser : registeredUsers) {
            registeredUser.setPassword("******");
        }
        return registeredUsers;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public RegisteredUser getUser(@PathVariable("id") String id) throws UserNotFoundException {
        final RegisteredUser user = registeredUserRepository.findOne(id);
        if (null == user) {
            throw new UserNotFoundException();
        }
        user.setPassword("******");
        return user;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") String id) throws UserNotFoundException {
        final RegisteredUser user = registeredUserRepository.findOne(id);
        if (null == user) {
            throw new UserNotFoundException();
        }
        registeredUserRepository.delete(user);
        rabbitTemplate.convertAndSend(queueName, new UserDeletionNotification(user.getId(), user.getEmailAddress()).toString());
    }

    @RequestMapping(value = "/register", params = {"email", "pass"}, method = RequestMethod.GET)
    public RegistrationResponse submitUser(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "pass") String pass) throws UserNotRegisteredException {
        final RegisteredUser user = new RegisteredUser(null, email, pass);
        return register(user);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "duplicate email address")
    @ExceptionHandler(DuplicateKeyException.class)
    public void duplicateEmailAddress() {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "user not found")
    @ExceptionHandler(UserNotFoundException.class)
    public void userNotFound() {
    }

    @ResponseStatus(value = HttpStatus.NOT_MODIFIED, reason = "user not registered")
    @ExceptionHandler(UserNotRegisteredException.class)
    public void userNotRegistered() {
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "illegal argument")
    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgument() {
    }

    private RegistrationResponse register(RegisteredUser user) throws UserNotRegisteredException {
        final RegisteredUser registeredUser = registeredUserRepository.save(user);
        if(registeredUser != null) {
            submitNotification(new UserRegistrationNotification(registeredUser.getId(), registeredUser.getEmailAddress()));
            return new RegistrationResponse(registeredUser.getId(), registeredUser.getEmailAddress());
        } else {
            throw new UserNotRegisteredException();
        }
    }
    
    private void submitNotification(UserNotification notification) {
        rabbitTemplate.convertAndSend(queueName, notification.toString());
    }

    private class UserNotFoundException extends Exception {}

    private class UserNotRegisteredException extends Exception {}
}