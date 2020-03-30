package dev.drugowick.threehundredsixty.domain.repository;

import dev.drugowick.threehundredsixty.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByEvaluatorEmailAndEvaluatedId(String username, Long evaluatedId);
    Optional<Question> findByEvaluatorEmailAndEvaluatedIdAndId(String username, Long evaluatedId, Long id);
}
