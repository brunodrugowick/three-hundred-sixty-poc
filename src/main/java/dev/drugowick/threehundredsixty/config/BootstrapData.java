package dev.drugowick.threehundredsixty.config;

import dev.drugowick.threehundredsixty.domain.entity.BaseQuestion;
import dev.drugowick.threehundredsixty.domain.entity.Employee;
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

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Value("${com.feedbackcentral.admin.password:Admin123}")
    private String adminPassword;

    public BootstrapData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {
            Employee admin = new Employee();
            admin.setEmail("admin@drugowick.dev");
            admin.setEnabled(true);
            admin.setName("Admin");
            admin.setPosition("Admin");
            admin.setRoles("ROLE_ADMIN,ROLE_USER");
            admin.setPassword(passwordEncoder.encode(adminPassword));
            userRepository.save(admin);
        }
    }
}