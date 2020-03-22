package dev.drugowick.threehundredsixty.controller;

import dev.drugowick.threehundredsixty.domain.repository.FeedbackRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    private FeedbackRepository feedbackRepository;

    public HomePageController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping
    public String homePage(Model model) {
        String username = "HeitorMatsui";
        model.addAttribute("feedbacks", feedbackRepository.findAllByUserUsername(username));
        model.addAttribute("username", username);
        return "index";
    }
}
