package dev.drugowick.threehundredsixty.domain.repository;

import dev.drugowick.threehundredsixty.domain.entity.FeedbackQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackQuestionRepository extends JpaRepository<FeedbackQuestion, Long> {
}
