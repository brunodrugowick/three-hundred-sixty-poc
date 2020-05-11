package dev.drugowick.threehundredsixty.domain.repository;

import dev.drugowick.threehundredsixty.domain.entity.Feedback;
import dev.drugowick.threehundredsixty.domain.entity.FeedbackState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Feedback.Key> {

    List<Feedback> findAllByEvaluatorEmail(String evaluator);
    boolean existsByEvaluatedEmailAndEvaluatorEmail(String evaluatedEmail, String evaluatorEmail);
    List<Feedback> findAllByEvaluatorEmailAndStateNot(String evaluator, FeedbackState feedbackState);
    List<Feedback> findAllByEvaluatorEmailAndState(String evaluator, FeedbackState feedbackState);
    List<Feedback> findAllByEvaluatedEmail(String email);
    List<Feedback> findAllByEvaluatedEmailAndState(String evaluated, FeedbackState feedbackState);
    Optional<Feedback> findByEvaluatedIdAndEvaluatorEmail(Long evaluatedId, String evaluator);
    Optional<Feedback> findByEvaluatorIdAndEvaluatedId(Long evaluatorId, Long evaluatedId);

    Optional<Feedback> findByEvaluatorEmailAndEvaluatedEmail(String evaluator, String evaluated);


    void deleteFeedbackByEvaluatorEmailAndEvaluatedEmail(String evaluatorEmail, String evaluatedEmail);
}
