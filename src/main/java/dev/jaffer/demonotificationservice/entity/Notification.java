package dev.jaffer.demonotificationservice.entity;

import java.util.List;
import java.util.Map;

public class Notification{

    private Long id;

    private Long eventId;

    private List<Long> userIds;

    private String message;

    //private NotificationTypeEnum notificationType; or string

    private String priority;

    private Map<String,String> metadata;

    private List<User> users;

}
