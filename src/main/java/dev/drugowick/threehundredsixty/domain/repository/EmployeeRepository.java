package dev.drugowick.threehundredsixty.domain.repository;

import dev.drugowick.threehundredsixty.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
