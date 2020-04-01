package dev.drugowick.threehundredsixty.domain.repository;

import dev.drugowick.threehundredsixty.domain.entity.Feedback;
import dev.drugowick.threehundredsixty.domain.entity.FeedbackState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Feedback.Key> {

    List<Feedback> findAllByEvaluatorEmail(String evaluator);
    List<Feedback> findAllByEvaluatorEmailAndStateNot(String evaluator, FeedbackState feedbackState);
    Optional<Feedback> findByEvaluatedIdAndEvaluatorEmail(Long evaluatedId, String evaluator);
    Optional<Feedback> findByEvaluatorIdAndEvaluatedId(Long evaluatorId, Long evaluatedId);

}
