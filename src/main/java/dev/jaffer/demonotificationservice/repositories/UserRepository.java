package dev.jaffer.demonotificationservice.repositories;

import dev.jaffer.demonotificationservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
