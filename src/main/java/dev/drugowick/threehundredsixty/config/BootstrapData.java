package dev.drugowick.threehundredsixty.config;

import dev.drugowick.threehundredsixty.domain.entity.BaseQuestion;
import dev.drugowick.threehundredsixty.domain.entity.Question;
import dev.drugowick.threehundredsixty.domain.repository.BaseQuestionRepository;
import dev.drugowick.threehundredsixty.domain.repository.FeedbackRepository;
import dev.drugowick.threehundredsixty.domain.repository.QuestionRepository;
import dev.drugowick.threehundredsixty.domain.repository.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for bootstraping the data based on the provided data on a database. It will create a set
 * of questions for each registered feedback (a pair user/employee).
 */
@Component
@Slf4j
public class BootstrapData implements CommandLineRunner {

    private BaseQuestionRepository baseQuestionRepository;
    private QuestionRepository questionRepository;
    private FeedbackRepository feedbackRepository;

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Value("${com.feedbackcentral.admin.password:Admin123}")
    private String adminPassword;

    public BootstrapData(BaseQuestionRepository baseQuestionRepository,
                         QuestionRepository questionRepository,
                         FeedbackRepository feedbackRepository,
                         UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.baseQuestionRepository = baseQuestionRepository;
        this.questionRepository = questionRepository;
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (baseQuestionRepository.count() == 0) {
            BaseQuestion baseQuestion = new BaseQuestion();
            baseQuestion.setCategory("Analista de Sistemas");
            baseQuestion.setDescription("A produtividade na realização das tarefas que estão sob sua responsabilidade, está de acordo com o esperado.");
            baseQuestion.setPosition("Analista de Sistemas");

            baseQuestionRepository.save(baseQuestion);
        }

        feedbackRepository.findAll().forEach(feedback -> {
            baseQuestionRepository.findAllByPosition(feedback.getEvaluated().getPosition()).forEach(baseQuestion -> {
                Question question = new Question(
                        baseQuestion.getPosition(),
                        baseQuestion.getCategory(),
                        baseQuestion.getDescription(),
                        feedback.getEvaluated(),
                        feedback.getEvaluator());
                questionRepository.save(question);
            });
        });
    }
}
