package dev.drugowick.threehundredsixty.config;

import dev.drugowick.threehundredsixty.domain.entity.*;
import dev.drugowick.threehundredsixty.domain.entity.config.Config;
import dev.drugowick.threehundredsixty.domain.repository.BaseQuestionRepository;
import dev.drugowick.threehundredsixty.domain.repository.ConfigRepository;
import dev.drugowick.threehundredsixty.domain.repository.EmployeeRepository;
import dev.drugowick.threehundredsixty.domain.repository.FeedbackRepository;
import dev.drugowick.threehundredsixty.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * This class is responsible for bootstraping the data based on the provided data on a database. It will create a set
 * of questions for each registered feedback (a pair user/employee).
 */
@Component
@Slf4j
public class BootstrapData implements CommandLineRunner {

    public static final String FIRST_RUN_CONFIG_NAME = "firstRun";
    public static final String FIRST_RUN_CONFIG_VALUE = "true";

    private ConfigRepository configRepository;
    private BaseQuestionRepository baseQuestionRepository;
    private EmployeeRepository employeeRepository;
    private FeedbackRepository feedbackRepository;
    private FeedbackService feedbackService;
    private PasswordEncoder passwordEncoder;

    public BootstrapData(BaseQuestionRepository baseQuestionRepository, EmployeeRepository employeeRepository, ConfigRepository configRepository, FeedbackRepository feedbackRepository, FeedbackService feedbackService, PasswordEncoder passwordEncoder) {
        this.baseQuestionRepository = baseQuestionRepository;
        this.employeeRepository = employeeRepository;
        this.configRepository = configRepository;
        this.feedbackRepository = feedbackRepository;
        this.feedbackService = feedbackService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        Optional<Config> firstRunConfig = configRepository.findByName(FIRST_RUN_CONFIG_NAME);
        if (!firstRunConfig.isPresent() || firstRunConfig.get().getValue().equals(FIRST_RUN_CONFIG_VALUE)) {
            loadDemoData();

            Config firstRun = new Config();
            firstRun.setName(FIRST_RUN_CONFIG_NAME);
            firstRun.setValue(FIRST_RUN_CONFIG_VALUE);
            configRepository.save(firstRun);
        }
    }

    private void loadDemoData() {
        BaseQuestion baseQuestion1 = new BaseQuestion();
        baseQuestion1.setPosition("Analista de Sistemas");
        baseQuestion1.setCategory("Levantamento de Requisitos");
        baseQuestion1.setDescription("É capaz de entender seus usuários finais, mapeando suas necessidades em funcionalidades de forma satisfatória.");
        baseQuestionRepository.save(baseQuestion1);

        BaseQuestion baseQuestion2 = new BaseQuestion();
        baseQuestion2.setPosition("Coordenador");
        baseQuestion2.setCategory("Comunicação");
        baseQuestion2.setDescription("Se comunica com a frequência adequada com seus subordinados.");
        baseQuestionRepository.save(baseQuestion2);

        Employee employee1 = new Employee();
        employee1.setName("Heitor Marrakesh");
        employee1.setPosition("Coordenador");
        employee1.setEmail("heitor.marrakesh@email.com");
        employee1.setPassword(passwordEncoder.encode("password"));
        employee1.setRoles("ROLE_USER,ROLE_ADMIN");
        employee1.setEnabled(true);
        employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setName("Bruno Mahoney");
        employee2.setPosition("Coordenador");
        employee2.setEmail("bruno.mahoney@email.com");
        employee2.setPassword(passwordEncoder.encode("password"));
        employee2.setRoles("ROLE_USER,ROLE_ADMIN");
        employee2.setEnabled(true);
        employeeRepository.save(employee2);

        Employee employee3 = new Employee();
        employee3.setName("Enrique Iglesias");
        employee3.setPosition("Analista de Sistemas");
        employee3.setEmail("enrique.iglesias@email.com");
        employee3.setPassword(passwordEncoder.encode("password"));
        employee3.setRoles("ROLE_USER,ROLE_ADMIN");
        employee3.setEnabled(true);
        employeeRepository.save(employee3);

        Feedback feedback1 = new Feedback();
        feedback1.setState(FeedbackState.NOT_STARTED);
        feedback1.setRelationship(FeedbackRelationship.PEER);
        feedback1.setEvaluator(employee1);
        feedback1.setEvaluated(employee2);
        feedbackRepository.save(feedback1);
        feedbackService.generateFeedbackQuestions(feedback1);

        Feedback feedback2 = new Feedback();
        feedback2.setState(FeedbackState.NOT_STARTED);
        feedback2.setRelationship(FeedbackRelationship.SUPERIOR);
        feedback2.setEvaluator(employee1);
        feedback2.setEvaluated(employee3);
        feedbackRepository.save(feedback2);
        feedbackService.generateFeedbackQuestions(feedback2);

        Feedback feedback3 = new Feedback();
        feedback3.setState(FeedbackState.NOT_STARTED);
        feedback3.setRelationship(FeedbackRelationship.PEER);
        feedback3.setEvaluator(employee2);
        feedback3.setEvaluated(employee1);
        feedbackRepository.save(feedback3);
        feedbackService.generateFeedbackQuestions(feedback3);

        Feedback feedback4 = new Feedback();
        feedback4.setState(FeedbackState.NOT_STARTED);
        feedback4.setRelationship(FeedbackRelationship.SUPERIOR);
        feedback4.setEvaluator(employee2);
        feedback4.setEvaluated(employee3);
        feedbackRepository.save(feedback4);
        feedbackService.generateFeedbackQuestions(feedback4);

        Feedback feedback5 = new Feedback();
        feedback5.setState(FeedbackState.NOT_STARTED);
        feedback5.setRelationship(FeedbackRelationship.SUBORDINATE);
        feedback5.setEvaluator(employee3);
        feedback5.setEvaluated(employee1);
        feedbackRepository.save(feedback5);
        feedbackService.generateFeedbackQuestions(feedback5);

        Feedback feedback6 = new Feedback();
        feedback6.setState(FeedbackState.NOT_STARTED);
        feedback6.setRelationship(FeedbackRelationship.SUPERIOR);
        feedback6.setEvaluator(employee3);
        feedback6.setEvaluated(employee2);
        feedbackRepository.save(feedback6);
        feedbackService.generateFeedbackQuestions(feedback6);
    }
}
