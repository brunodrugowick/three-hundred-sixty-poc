package dev.drugowick.threehundredsixty.controller.admin;

import dev.drugowick.threehundredsixty.controller.BaseController;
import dev.drugowick.threehundredsixty.domain.entity.Employee;
import dev.drugowick.threehundredsixty.domain.entity.FeedbackState;
import dev.drugowick.threehundredsixty.domain.repository.EmployeeRepository;
import dev.drugowick.threehundredsixty.domain.repository.FeedbackRepository;
import dev.drugowick.threehundredsixty.domain.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/reports")
public class FeedbackReportTest extends BaseController {

    private final EmployeeRepository employeeRepository;
    private final FeedbackRepository feedbackRepository;
    private final QuestionRepository questionRepository;

    public FeedbackReportTest(EmployeeRepository employeeRepository, FeedbackRepository feedbackRepository, QuestionRepository questionRepository) {
        this.employeeRepository = employeeRepository;
        this.feedbackRepository = feedbackRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping
    public String list(Principal principal, Model model) {
        List<EmployeeReportListData> employeeReportListData = new ArrayList<>();
        employeeRepository.findAll().forEach(employee -> {
            employeeReportListData.add(new EmployeeReportListData(
                    employee,
                    feedbackRepository.findAllByEvaluatorEmail(employee.getEmail()).size(),
                    feedbackRepository.findAllByEvaluatedEmailAndState(
                            employee.getEmail(),
                            FeedbackState.FINISHED
                    ).size()
            ));
        });
        model.addAttribute("employeesFeedbacks", employeeReportListData);
        return "admin/reports";
    }

    @Data @AllArgsConstructor
    public class EmployeeReportListData {
        private Employee employee;
        private int numberOfFeedbacks;
        private int numberOfFinishedFeedbacks;
    }

    @GetMapping("/{employeeId}")
    public String getReport(@PathVariable Long employeeId, Model model) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            model.addAttribute("questions",
                    questionRepository.findAllByEvaluatedId(employee.getId()));
            model.addAttribute("evaluated", employee);

        }

        return "admin/report";
    }
}
