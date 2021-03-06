package dev.drugowick.threehundredsixty.domain.repository.repository;

import dev.drugowick.threehundredsixty.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);
}
