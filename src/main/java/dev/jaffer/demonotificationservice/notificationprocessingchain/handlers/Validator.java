package dev.jaffer.demonotificationservice.notificationprocessingchain.handlers;

import dev.jaffer.demonotificationservice.entity.Notification;
import dev.jaffer.demonotificationservice.entity.User;
import dev.jaffer.demonotificationservice.notificationprocessingchain.AbstractNotificationHandler;
import dev.jaffer.demonotificationservice.notificationprocessingchain.NotificationHandler;
import dev.jaffer.demonotificationservice.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Validator extends AbstractNotificationHandler {


    private final UserRepository userRepository;

    public Validator(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void handle(Notification notification){
        List<Long> userIds = notification.getUserIds();

        //geting all users in one DB call to reduce multiple DB calling for each individual userId
        List<User> usersFromDB = userRepository.findAllByUserIdIn(userIds);

        //Creating set of valid userIds and storing in set for O(1) lookup time
        Set<Long> validUserIds = usersFromDB.stream()
                .map(User::getUserId)
                .collect(Collectors.toSet());

        List<User> usersDetails = usersFromDB.stream()
                .filter(user -> validUserIds.contains(user.getUserId()))
                .toList();

        notification.setUserDetails(usersDetails);

        // Pass to the next handler in the chain
        passToNext(notification);

    }
}