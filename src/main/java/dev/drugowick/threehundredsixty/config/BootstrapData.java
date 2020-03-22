package dev.drugowick.threehundredsixty.config;

import dev.drugowick.threehundredsixty.domain.entity.Question;
import dev.drugowick.threehundredsixty.domain.repository.BaseQuestionRepository;
import dev.drugowick.threehundredsixty.domain.repository.FeedbackRepository;
import dev.drugowick.threehundredsixty.domain.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for bootstraping the data based on the provided data on a database. It will create a set
 * of questions for each registered feedback (a pair user/employee).
 */
@Component
public class BootstrapData implements CommandLineRunner {

    private BaseQuestionRepository baseQuestionRepository;
    private QuestionRepository questionRepository;
    private FeedbackRepository feedbackRepository;

    public BootstrapData(BaseQuestionRepository baseQuestionRepository,
                         QuestionRepository questionRepository,
                         FeedbackRepository feedbackRepository) {
        this.baseQuestionRepository = baseQuestionRepository;
        this.questionRepository = questionRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        feedbackRepository.findAll().forEach(feedback -> {
            baseQuestionRepository.findAllByPosition(feedback.getEmployee().getPosition()).forEach(baseQuestion -> {
                Question question = new Question(
                        baseQuestion.getPosition(),
                        baseQuestion.getCategory(),
                        baseQuestion.getDescription(),
                        feedback.getEmployee(),
                        feedback.getUser());
                questionRepository.save(question);
            });
        });
    }
}
