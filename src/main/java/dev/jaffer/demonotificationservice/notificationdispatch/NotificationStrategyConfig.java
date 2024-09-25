package dev.jaffer.demonotificationservice.notificationdispatch;

import dev.jaffer.demonotificationservice.notificationdispatch.dispatchstratergies.ChannelStrategy;
import dev.jaffer.demonotificationservice.notificationdispatch.dispatchstratergies.ChannelStrategyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class NotificationStrategyConfig {

    @Bean
    public ChannelStrategyFactory notificationStrategyFactory(List<ChannelStrategy> strategies) {
        return new ChannelStrategyFactory(strategies);
    }
}

