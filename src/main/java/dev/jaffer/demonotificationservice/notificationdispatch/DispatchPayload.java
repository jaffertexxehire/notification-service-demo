package dev.jaffer.demonotificationservice.notificationdispatch;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.jaffer.demonotificationservice.entity.User;
import dev.jaffer.demonotificationservice.entity.UserChannelPreference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DispatchPayload {

    @JsonProperty("userDetails")
    private List<User> userDetails;
//
//    private List<UserChannelPreference> channelPreferences;
    private String message;
    private Map<String, Object> metadata;

}
