package dev.drugowick.threehundredsixty.controller;

import dev.drugowick.threehundredsixty.domain.entity.Feedback;
import dev.drugowick.threehundredsixty.domain.entity.FeedbackState;
import dev.drugowick.threehundredsixty.domain.repository.FeedbackRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class HomePageController extends BaseController {

    private FeedbackRepository feedbackRepository;

    public HomePageController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping
    public String homePage(Principal principal, Model model) {
        String username = principal.getName();
        List<Feedback> feedbacks = feedbackRepository.findAllByEvaluatorEmailAndStateNot(username, FeedbackState.NOT_PROCESSED);
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("feedbackCount", feedbacks.size());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
