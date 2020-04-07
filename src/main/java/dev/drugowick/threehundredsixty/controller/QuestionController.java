package dev.drugowick.threehundredsixty.controller;

import dev.drugowick.threehundredsixty.domain.entity.Feedback;
import dev.drugowick.threehundredsixty.domain.entity.FeedbackState;
import dev.drugowick.threehundredsixty.domain.entity.Question;
import dev.drugowick.threehundredsixty.domain.repository.FeedbackRepository;
import dev.drugowick.threehundredsixty.domain.repository.QuestionRepository;
import dev.drugowick.threehundredsixty.dto.AnswerDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/feedback/{evaluatedId}/question/{questionId}")
public class QuestionController extends BaseController {

    private QuestionRepository questionRepository;
    private FeedbackRepository feedbackRepository;

    public QuestionController(QuestionRepository questionRepository, FeedbackRepository feedbackRepository) {
        this.questionRepository = questionRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @ModelAttribute("feedback")
    public Feedback feedback(Principal principal, Model model, @PathVariable Long evaluatedId) {
        Optional<Feedback> optionalFeedback = feedbackRepository.findByEvaluatedIdAndEvaluatorEmail(evaluatedId, principal.getName());
        return optionalFeedback.orElseGet(Feedback::new);
    }

    @ModelAttribute("question")
    public AnswerDto question(Principal principal, @PathVariable Long evaluatedId, @PathVariable Long questionId) {
        Question question = questionRepository.findByEvaluatorEmailAndEvaluatedIdAndId(
                principal.getName(),
                evaluatedId,
                questionId).orElse(null);

        //TODO provide proper conversion strategy, not this "hardcoded" conversion
        AnswerDto answerDto = new AnswerDto();
        answerDto.setCategory(question.getCategory());
        answerDto.setDescription(question.getDescription());
        answerDto.setEvaluation(question.getEvaluation());
        answerDto.setExample(question.getExample());
        answerDto.setId(question.getId());
        answerDto.setImprovement(question.getImprovement());
        answerDto.setTitle(question.getTitle());

        answerDto.setEvaluated(question.getEvaluated());
        answerDto.setEvaluator(question.getEvaluator());

        return answerDto;
    }

    @ModelAttribute("evaluation")
    public String evaluation(Principal principal, @PathVariable Long evaluatedId, @PathVariable Long questionId) {
        Question question = questionRepository.findByEvaluatorEmailAndEvaluatedIdAndId(
                principal.getName(),
                evaluatedId,
                questionId).orElseThrow(RuntimeException::new);
        return question.getEvaluation();
    }

    @GetMapping
    public String getQuestion() {
        return "feedback-question";
    }

    @PostMapping
    public String saveQuestion(Principal principal,
                               @PathVariable Long evaluatedId,
                               @PathVariable Long questionId,
                               @ModelAttribute("question") @Valid AnswerDto answerDto,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "feedback-question";
        }
        Question question = questionRepository.getOne(questionId);
        // TODO DTO transformation and Service stuff happening on the controller.
        //  Should implement a proper mapping and analyze the need and architect a service layer.
        question.setTitle(answerDto.getTitle());
        question.setDescription(answerDto.getDescription());
        question.setEvaluation(answerDto.getEvaluation());
        question.setExample(answerDto.getExample());
        question.setImprovement(answerDto.getImprovement());
        questionRepository.save(question);

        Optional<Feedback> optionalFeedback = feedbackRepository.findByEvaluatedIdAndEvaluatorEmail(evaluatedId, principal.getName());
        optionalFeedback.ifPresent(feedback -> {
            feedback.setState(FeedbackState.STARTED);
            List<Question> feedbackQuestions = questionRepository.findAllByEvaluatorEmailAndEvaluatedId(principal.getName(), evaluatedId);
            //TODO implement verification if feedback is finished.
            if (feedbackQuestions
                    .stream().parallel()
                    .noneMatch(question1 -> question1.getEvaluation() == null || question1.getEvaluation().equals(""))) {
                feedback.setState(FeedbackState.FINISHED);
            }
            feedbackRepository.save(feedback);
        });

        return "redirect:/feedback/" + evaluatedId;
    }
}
