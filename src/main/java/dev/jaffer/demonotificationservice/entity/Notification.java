package dev.jaffer.demonotificationservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification{

    @JsonProperty("event_id")
    private Long eventId;

    @JsonProperty("userIds")
    private List<Long> userIds;

    @JsonProperty("message")
    private String message;

    //private NotificationTypeEnum notificationType; or string

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("metadata")
    private Map<String,Object> metadata;

    @JsonProperty("notificationType")
    private String notificationType;

    private List<User> userDetails;

    private List<UserChannelPreference> bulkChannelPreferences;

}
