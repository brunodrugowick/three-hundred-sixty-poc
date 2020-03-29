package dev.drugowick.threehundredsixty.domain.repository;

import dev.drugowick.threehundredsixty.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByEvaluatorNameAndEvaluatedName(String username, String employeeName);
    Optional<Question> findByEvaluatorNameAndEvaluatedNameAndId(String username, String employee, Long id);
}
