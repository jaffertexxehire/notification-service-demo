package dev.jaffer.demonotificationservice.repositories;

import dev.jaffer.demonotificationservice.entity.UserChannelPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserChannelPreferenceRepository extends JpaRepository<UserChannelPreference, Long> {
    UserChannelPreference findByChannelName(String email);

    List<UserChannelPreference> findAllByChannelNameIn(List<String> newPreferences);
}
