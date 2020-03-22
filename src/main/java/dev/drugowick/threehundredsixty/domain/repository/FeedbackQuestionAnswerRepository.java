package dev.drugowick.threehundredsixty.domain.repository;

import dev.drugowick.threehundredsixty.domain.entity.Employee;
import dev.drugowick.threehundredsixty.domain.entity.FeedbackQuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackQuestionAnswerRepository extends JpaRepository<FeedbackQuestionAnswer, Long> {

    List<FeedbackQuestionAnswer> findAllByQuestionEmployee(Employee employee);
}
