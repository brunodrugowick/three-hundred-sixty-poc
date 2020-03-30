package dev.drugowick.threehundredsixty.controller;

import dev.drugowick.threehundredsixty.domain.entity.Feedback;
import dev.drugowick.threehundredsixty.domain.entity.FeedbackState;
import dev.drugowick.threehundredsixty.domain.entity.Question;
import dev.drugowick.threehundredsixty.domain.repository.FeedbackRepository;
import dev.drugowick.threehundredsixty.domain.repository.QuestionRepository;
import dev.drugowick.threehundredsixty.dto.AnswerDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/feedback/{evaluatedId}")
public class FeedbackController extends BaseController {

    private QuestionRepository questionRepository;
    private FeedbackRepository feedbackRepository;

    public FeedbackController(QuestionRepository questionRepository, FeedbackRepository feedbackRepository) {
        this.questionRepository = questionRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping
    public String getFeedbacks(Principal principal, Model model, @PathVariable Long evaluatedId) {
        String username = principal.getName();
        List<Question> questions = questionRepository.findAllByEvaluatorEmailAndEvaluatedId(username, evaluatedId);
        if (questions.size() == 0) {
            throw new RuntimeException("Feedback inexistente ou inválido para o usuário " + principal.getName());
        }
        model.addAttribute("questions", questions);
        return "feedback-list-questions";
    }

    @GetMapping("/question/{questionId}")
    public String getQuestion(Principal principal, Model model, @PathVariable Long evaluatedId, @PathVariable Long questionId) {

        Optional<Question> optionalQuestion = questionRepository.findByEvaluatorEmailAndEvaluatedIdAndId(
                principal.getName(),
                evaluatedId,
                questionId);
        if (optionalQuestion.isPresent()) {
            model.addAttribute("question", optionalQuestion.get());
        } else {
            throw new RuntimeException("Pergunta inexistente ou inválida para o usuário " + principal.getName());
        }

        optionalQuestion.ifPresent(question -> model.addAttribute("evaluation", question.getEvaluation()));

        return "feedback-question";
    }

    @PostMapping("/question/{questionId}")
    public String saveQuestion(Principal principal,
                               Model model,
                               @PathVariable Long evaluatedId,
                               @PathVariable Long questionId,
                               @Valid AnswerDto answerDto) {

        Question question = questionRepository.getOne(questionId);
        question.setTitle(answerDto.getTitle());
        question.setDescription(answerDto.getDescription());
        question.setEvaluation(answerDto.getEvaluation());
        question.setExample(answerDto.getExample());
        question.setImprovement(answerDto.getImprovement());
        questionRepository.save(question);

        Optional<Feedback> optionalFeedback = feedbackRepository.findByEvaluatedIdAndEvaluatorEmail(evaluatedId, principal.getName());
        optionalFeedback.ifPresent(feedback -> feedback.setState(FeedbackState.STARTED));
        optionalFeedback.ifPresent((feedback -> feedbackRepository.save(feedback)));

        return "redirect:/feedback/" + evaluatedId;
    }
}
