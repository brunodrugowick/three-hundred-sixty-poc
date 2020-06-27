package dev.drugowick.threehundredsixty.controller;

import dev.drugowick.threehundredsixty.domain.entity.Feedback;
import dev.drugowick.threehundredsixty.domain.entity.Question;
import dev.drugowick.threehundredsixty.domain.repository.FeedbackRepository;
import dev.drugowick.threehundredsixty.domain.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/feedback/{evaluatedId}")
public class FeedbackController extends BaseController {

    private QuestionRepository questionRepository;
    private FeedbackRepository feedbackRepository;

    protected String feedbackListQuestionTemplate = "feedback-list-questions";

    public FeedbackController(QuestionRepository questionRepository, FeedbackRepository feedbackRepository) {
        this.questionRepository = questionRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @ModelAttribute("feedback")
    public Feedback feedback(Principal principal, Model model, @PathVariable Long evaluatedId) {
        Optional<Feedback> optionalFeedback = feedbackRepository.findByEvaluatedIdAndEvaluatorEmail(evaluatedId, principal.getName());
        return optionalFeedback.orElseGet(Feedback::new);
    }

    @GetMapping
    public String getFeedbacks(Principal principal, Model model, @PathVariable Long evaluatedId) {
        String username = principal.getName();
        List<Question> questions = questionRepository.findAllByEvaluatorEmailAndEvaluatedId(username, evaluatedId);
        if (questions.size() == 0) {
            throw new RuntimeException("Feedback inexistente ou inválido para o usuário " + principal.getName());
        }
        model.addAttribute("questions", questions);
        return feedbackListQuestionTemplate;
    }
}
