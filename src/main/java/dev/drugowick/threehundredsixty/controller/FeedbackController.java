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
import java.util.Optional;

@Controller
@RequestMapping("{person}/feedback")
public class FeedbackController {

    private QuestionRepository questionRepository;
    private FeedbackRepository feedbackRepository;

    public FeedbackController(QuestionRepository questionRepository, FeedbackRepository feedbackRepository) {
        this.questionRepository = questionRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping
    public String getFeedbacks(Model model, @PathVariable String person) {

        model.addAttribute("questions", questionRepository.findAllByUserUsernameAndEmployeeName("BrunoMuniz", person));
        model.addAttribute("username", "BrunoMuniz");
        return "feedback-list-questions";
    }

    @GetMapping("/{questionId}")
    public String getQuestion(Model model, @PathVariable String person, @PathVariable Long questionId) {

        Optional<Question> question = questionRepository.findByUserUsernameAndEmployeeNameAndId(
                "BrunoMuniz",
                person,
                questionId);
        question.ifPresent(value -> model.addAttribute("question", value));

        model.addAttribute("username", "BrunoMuniz");
        return "feedback-question";
    }

    @PostMapping("/{questionId}")
    public String saveQuestion(Model model,
                               @PathVariable String person,
                               @PathVariable Long questionId,
                               @Valid AnswerDto answerDto) {

        Question question = questionRepository.getOne(questionId);
        question.setTitle(answerDto.getTitle());
        question.setDescription(answerDto.getDescription());
        question.setEvaluation(answerDto.getEvaluation());
        question.setExample(answerDto.getExample());
        question.setImprovement(answerDto.getImprovement());
        questionRepository.save(question);

        Optional<Feedback> optionalFeedback = feedbackRepository.findByEmployeeNameAndUserUsername(person, "BrunoMuniz");
        optionalFeedback.ifPresent(feedback -> feedback.setState(FeedbackState.STARTED));
        optionalFeedback.ifPresent((feedback -> feedbackRepository.save(feedback)));

        return "redirect:/" + person + "/feedback";
    }
}
