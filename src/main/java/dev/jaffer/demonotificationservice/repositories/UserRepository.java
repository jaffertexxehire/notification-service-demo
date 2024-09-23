package dev.jaffer.demonotificationservice.repositories;

import dev.jaffer.demonotificationservice.entity.User;
import dev.jaffer.demonotificationservice.entity.UserChannelPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.preferences FROM User u WHERE u.userId = :userId")
    List<UserChannelPreference> findPreferencesByUserId(@Param("userId") Long userId);

    Optional<User> findByUserId(Long first);
}
