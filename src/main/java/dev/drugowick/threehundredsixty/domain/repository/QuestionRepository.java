package dev.drugowick.threehundredsixty.domain.repository;

import dev.drugowick.threehundredsixty.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Question findFirstByEvaluatorEmailAndEvaluatedEmail(String evaluator, String evaluated);
    List<Question> findAllByEvaluatorEmailAndEvaluatedId(String username, Long evaluatedId);
    List<Question> findAllByEvaluatedEmailAndDescriptionAndEvaluatorPosition(String evaluatedEmail, String description, String category);
    Optional<Question> findByEvaluatorEmailAndEvaluatedIdAndId(String username, Long evaluatedId, Long id);
    void deleteAllByEvaluatorEmailAndEvaluatedEmail(String evaluator, String evaluated);
}
