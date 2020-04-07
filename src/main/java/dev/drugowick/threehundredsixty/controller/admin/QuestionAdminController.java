package dev.drugowick.threehundredsixty.controller.admin;

import dev.drugowick.threehundredsixty.controller.BaseController;
import dev.drugowick.threehundredsixty.domain.entity.BaseQuestion;
import dev.drugowick.threehundredsixty.domain.repository.BaseQuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class QuestionAdminController extends BaseController {

    private final BaseQuestionRepository baseQuestionRepository;

    public QuestionAdminController(BaseQuestionRepository baseQuestionRepository) {
        this.baseQuestionRepository = baseQuestionRepository;
    }

    @GetMapping
    @RequestMapping("/questions")
    public String list(Principal principal, Model model) {
        model.addAttribute("questions", baseQuestionRepository.findAll());
        return "admin/questions-admin";
    }

    @RequestMapping(value = "/questions/{id}", method = RequestMethod.GET)
    public String get(Principal principal, Model model, @PathVariable Long id) {
        Optional<BaseQuestion> optionalQuestion = baseQuestionRepository.findById(id);
        optionalQuestion.ifPresent(baseQuestion -> model.addAttribute("question", baseQuestion));
        return "admin/question-edit";
    }

    @RequestMapping(value = "/questions/{id}", method = RequestMethod.POST)
    public String saveOne(Principal principal, Model model,
                          BaseQuestion baseQuestion,
                          @PathVariable Long id
    ) {
        Optional<BaseQuestion> optionalQuestion = baseQuestionRepository.findById(id);
        baseQuestionRepository.save(baseQuestion);
        return "redirect:/admin/questions";
    }

    @RequestMapping(value = "/questions/new", method = RequestMethod.GET)
    public String newQuestion(Principal principal, Model model) {
        BaseQuestion baseQuestion = new BaseQuestion();
        model.addAttribute("question", baseQuestion);
        return "admin/question-edit";
    }

    @RequestMapping(value = "/questions/new", method = RequestMethod.POST)
    public String save(Principal principal, Model model, BaseQuestion baseQuestion) {
        baseQuestionRepository.save(baseQuestion);
        return "redirect:/admin/questions";
    }

    @Transactional
    @RequestMapping(value = "/questions", method = RequestMethod.POST)
    public String delete(Long id) {
        baseQuestionRepository.deleteById(id);
        return "redirect:/admin/questions";
    }
}
