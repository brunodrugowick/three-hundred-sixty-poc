package dev.drugowick.threehundredsixty.controller;

import dev.drugowick.threehundredsixty.domain.repository.FeedbackRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomePageController {

    private FeedbackRepository feedbackRepository;

    public HomePageController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping
    public String homePage(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("feedbacks", feedbackRepository.findAllByEvaluatorEmail(username));
        model.addAttribute("username", username);
        return "index";
    }
}
