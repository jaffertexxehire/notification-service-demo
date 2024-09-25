package dev.jaffer.demonotificationservice.notificationdispatch.dispatchstratergies;

import dev.jaffer.demonotificationservice.entity.UserChannelPreference;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChannelStrategyFactory {
    private final Map<String, ChannelStrategy> strategies = new HashMap<>();

    public ChannelStrategyFactory(List<ChannelStrategy> strategyList) {
        for (ChannelStrategy strategy : strategyList) {
            if (strategy instanceof EmailChannelStrategy) {
                strategies.put("EMAIL", strategy);
            } else if (strategy instanceof SmsChannelStrategy) {
                strategies.put("SMS", strategy);
            }
            // Add more strategies as needed
        }
    }

    public ChannelStrategy getStrategy(String channelName) {
        return strategies.get(channelName);
    }
}
