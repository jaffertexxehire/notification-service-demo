package dev.jaffer.demonotificationservice.notificationprocessingchain.handlers;

import dev.jaffer.demonotificationservice.entity.*;
import dev.jaffer.demonotificationservice.notificationprocessingchain.AbstractNotificationHandler;
import dev.jaffer.demonotificationservice.notificationprocessingchain.NotificationHandler;
import dev.jaffer.demonotificationservice.repositories.EventRepository;
import dev.jaffer.demonotificationservice.repositories.UserChannelPreferenceRepository;
import dev.jaffer.demonotificationservice.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/*
    * This class is responsible for handling notifications of type USER_
    * It saves the user details to the database for the
    * first time a user is created or when a user updates
    * their details
 */
@Slf4j
@Component
public class UserEventHandler extends AbstractNotificationHandler {



    private UserRepository userRepository;
    private UserChannelPreferenceRepository userChannelPreferenceRepository;
    private EventRepository eventRepository;

    public UserEventHandler(UserRepository userRepository, UserChannelPreferenceRepository userChannelPreferenceRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.userChannelPreferenceRepository = userChannelPreferenceRepository;
        this.eventRepository = eventRepository;

    }

    @Override
    @Transactional
    public void handle(Notification notification) {


        String notificationType = notification.getNotificationType();
        if(notificationType.contains("USER")) {

            Optional<User> user = userRepository.findByUserId(notification.getUserIds().getFirst());
            if(user.isPresent()) {
                updateExistingUser(notification, user.get());
                Optional<Event> event = eventRepository.findByEventId(notification.getEventId());
                if(event.isPresent()) {
                    event.get().setStatus(Status.COMPLETED);
                    eventRepository.save(event.get());
                }
                //remove this later
            //  passToNext(notification);

            } else {
                saveNewUser(notification);
                passToNext(notification);
            }
        }
    }

    private void saveNewUser(Notification notification) {
        User user = new User();
        user.setUserId(notification.getUserIds().getFirst());
        user.setName(notification.getMetadata().get("fullName").toString());
        user.setEmail(notification.getMetadata().get("email").toString());
        user.setPhoneNumber(notification.getMetadata().get("phoneNumber").toString());
        UserChannelPreference preference = userChannelPreferenceRepository.findByChannelName("EMAIL");
        List<UserChannelPreference> preferences = List.of(preference);
        user.setPreferences(preferences);
        userRepository.save(user);
    }

    private void updateExistingUser(Notification notification, User user) {
        user.setEmail(!Objects.equals(notification.getMetadata().get("email").toString(), "") ? notification.getMetadata().get("email").toString():user.getEmail());
        user.setPhoneNumber(!Objects.equals(notification.getMetadata().get("phoneNumber").toString(), "") ? notification.getMetadata().get("phoneNumber").toString():user.getPhoneNumber());
        user.setName(!Objects.equals(notification.getMetadata().get("fullName").toString(), "") ? notification.getMetadata().get("fullName").toString():user.getName());
        if(notification.getMetadata().get("preferences") != null) {
            List<String> newPreferences = (List<String>) notification.getMetadata().get("preferences");
            List<UserChannelPreference> preferences = userChannelPreferenceRepository.findAllByChannelNameIn(newPreferences);
            user.setPreferences(preferences);
        }
        userRepository.save(user);
        log.info("User updated: " + user);

    }


}
