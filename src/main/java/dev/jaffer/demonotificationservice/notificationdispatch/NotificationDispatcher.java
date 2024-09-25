package dev.jaffer.demonotificationservice.notificationdispatch;

import dev.jaffer.demonotificationservice.entity.UserChannelPreference;
import dev.jaffer.demonotificationservice.notificationdispatch.dispatchstratergies.ChannelStrategy;
import dev.jaffer.demonotificationservice.notificationdispatch.dispatchstratergies.ChannelStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationDispatcher {


    private final ChannelStrategyFactory strategyFactory;

    public NotificationDispatcher(ChannelStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public void dispatch(DispatchPayload payload) {
        List<UserChannelPreference> channelPreferences = new ArrayList<>();
        if (payload.getUserDetails().size() == 1) {
            channelPreferences = payload.getUserDetails().get(0).getPreferences();

//        }else{
//            //use userchannelpreference repository to get the preference for bulk as email
//            //channelPreferences =
//        }
        }
        for (UserChannelPreference preference : channelPreferences) {
            ChannelStrategy strategy = strategyFactory.getStrategy(preference.getChannelName());
            if (strategy != null) {
                strategy.dispatchToChannel(payload);
            } else {
                System.out.println("No strategy found for preference: " + preference);
            }
        }
    }
}
