package dev.drugowick.threehundredsixty.config;

import dev.drugowick.threehundredsixty.domain.entity.FeedbackQuestion;
import dev.drugowick.threehundredsixty.domain.entity.FeedbackQuestionAnswer;
import dev.drugowick.threehundredsixty.domain.repository.BaseQuestionRepository;
import dev.drugowick.threehundredsixty.domain.repository.FeedbackQuestionAnswerRepository;
import dev.drugowick.threehundredsixty.domain.repository.FeedbackQuestionRepository;
import dev.drugowick.threehundredsixty.domain.repository.FeedbackRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for bootstraping the data based on the provided data on a database. It will create a set
 * of questions for each registered feedback (a pair user/employee).
 */
@Component
public class BootstrapData implements CommandLineRunner {

    private BaseQuestionRepository baseQuestionRepository;
    private FeedbackQuestionRepository feedbackQuestionRepository;
    private FeedbackRepository feedbackRepository;
    private FeedbackQuestionAnswerRepository answerRepository;

    public BootstrapData(BaseQuestionRepository baseQuestionRepository,
                         FeedbackQuestionRepository feedbackQuestionRepository,
                         FeedbackRepository feedbackRepository,
                         FeedbackQuestionAnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
        this.baseQuestionRepository = baseQuestionRepository;
        this.feedbackQuestionRepository = feedbackQuestionRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        feedbackRepository.findAll().forEach(feedback -> {
            baseQuestionRepository.findAllByCategory(feedback.getEmployee().getPosition()).forEach(baseQuestion -> {
                FeedbackQuestion feedbackQuestion = new FeedbackQuestion(
                        baseQuestion.getCategory(),
                        baseQuestion.getTitle(),
                        baseQuestion.getDescription(),
                        feedback.getEmployee(),
                        feedback.getUser());
                feedbackQuestionRepository.save(feedbackQuestion);
                answerRepository.save(new FeedbackQuestionAnswer(feedbackQuestion));
            });
        });
    }
}
