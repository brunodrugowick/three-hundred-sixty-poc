package dev.drugowick.threehundredsixty.controller.admin;

import dev.drugowick.threehundredsixty.controller.BaseController;
import dev.drugowick.threehundredsixty.controller.util.FeedbackInput;
import dev.drugowick.threehundredsixty.domain.entity.Feedback;
import dev.drugowick.threehundredsixty.domain.entity.FeedbackState;
import dev.drugowick.threehundredsixty.domain.entity.Question;
import dev.drugowick.threehundredsixty.domain.repository.BaseQuestionRepository;
import dev.drugowick.threehundredsixty.domain.repository.EmployeeRepository;
import dev.drugowick.threehundredsixty.domain.repository.FeedbackRepository;
import dev.drugowick.threehundredsixty.domain.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@RequestMapping("/admin/feedbacks")
public class FeedbackAdminController extends BaseController {

    private final FeedbackRepository feedbackRepository;
    private final EmployeeRepository employeeRepository;
    private final BaseQuestionRepository baseQuestionRepository;
    private final QuestionRepository questionRepository;

    public FeedbackAdminController(FeedbackRepository feedbackRepository, EmployeeRepository employeeRepository, BaseQuestionRepository baseQuestionRepository, QuestionRepository questionRepository) {
        this.feedbackRepository = feedbackRepository;
        this.employeeRepository = employeeRepository;
        this.baseQuestionRepository = baseQuestionRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping
    public String list(Principal principal, Model model) {
        model.addAttribute("feedbacks", feedbackRepository.findAll());
        return "admin/feedbacks-admin";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newFeedback(Principal principal, Model model) {
        model.addAttribute("feedback", new FeedbackInput());
        model.addAttribute("employees", employeeRepository.findAll());
        return "admin/feedback-new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String save(Principal principal, Model model, FeedbackInput feedbackInput) {
        Feedback feedback = new Feedback();
        employeeRepository.findByEmail(feedbackInput.getEvaluatorUsername())
                .ifPresent(feedback::setEvaluator);
        employeeRepository.findByEmail(feedbackInput.getEvaluatedUsername())
                .ifPresent(feedback::setEvaluated);
        feedback.setState(FeedbackState.NOT_PROCESSED);
        feedback.setRelationship(feedbackInput.getRelationship());
        //TODO Move this mess to a service class.
        generateFeedbackQuestions(feedback);
        return "redirect:/admin/feedbacks";
    }

    @Transactional
    @RequestMapping(value = "/processAll", method = RequestMethod.POST)
    public String processAll() {
        //TODO Shame on me... again, create a proper service class.
        questionRepository.deleteAll();
        feedbackRepository.findAll().forEach(this::generateFeedbackQuestions);
        return "redirect:/admin/feedbacks";
    }

    @Transactional
    @RequestMapping(value = "/processOne", method = RequestMethod.POST)
    public String processOne(FeedbackInput feedbackInput) {
        //TODO Shame on me... again, create a proper service class.
        feedbackRepository.findByEvaluatorEmailAndEvaluatedEmail(
            feedbackInput.getEvaluatorUsername(),
            feedbackInput.getEvaluatedUsername()
        ).ifPresent(feedback -> {
            questionRepository.deleteAllByEvaluatorEmailAndEvaluatedEmail(
                    feedback.getEvaluator().getEmail(),
                    feedback.getEvaluated().getEmail()
            );
            generateFeedbackQuestions(feedback);
        });

        return "redirect:/admin/feedbacks";
    }

    private void generateFeedbackQuestions(Feedback feedback) {
        baseQuestionRepository.findAllByPosition(feedback.getEvaluated().getPosition()).forEach(baseQuestion -> {
            Question question = new Question(
                    baseQuestion.getPosition(),
                    baseQuestion.getCategory(),
                    baseQuestion.getDescription(),
                    feedback.getEvaluated(),
                    feedback.getEvaluator());
            questionRepository.save(question);
        });
        feedback.setState(FeedbackState.NOT_STARTED);
        feedbackRepository.save(feedback);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String delete(FeedbackInput feedbackInput) {
        // TODO this should be in a service layer.
        feedbackRepository.deleteFeedbackByEvaluatorEmailAndEvaluatedEmail(
                feedbackInput.getEvaluatorUsername(),
                feedbackInput.getEvaluatedUsername());
        questionRepository.deleteAllByEvaluatorEmailAndEvaluatedEmail(
                feedbackInput.getEvaluatorUsername(),
                feedbackInput.getEvaluatedUsername());
        return "redirect:/admin/feedbacks";
    }
}
