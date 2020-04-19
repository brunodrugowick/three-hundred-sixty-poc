package dev.drugowick.threehundredsixty.controller;

import dev.drugowick.threehundredsixty.domain.entity.Employee;
import dev.drugowick.threehundredsixty.domain.repository.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Controller
@Slf4j
public class PasswordResetController {
    Logger logEx = LoggerFactory.getLogger("EXCEPTIONS");

    @Autowired
    UserRepository userRepository;

    @GetMapping("/password/update")
    public ModelAndView passwordUpdate(ModelAndView modelAndView,
                                       @RequestParam(defaultValue = "") String usermail) {
        Boolean isEmailValid = isEmailValid(usermail);
        modelAndView.getModel().put("isEmailValid", isEmailValid);
        modelAndView.setViewName("password-update");
        return modelAndView;
    }

    @GetMapping("/password/hello")
    public String bla() {
        log.info("OK: bla");
        return "password-update";
    }

    @PostMapping("/password/reset")
//    @GetMapping("/password/reset")
    public String passwordReset(
            @RequestParam(defaultValue = "") String currPassword,
            @RequestParam(defaultValue = "") String newPassword1,
            @RequestParam(defaultValue = "") String newPassword2,
            @RequestParam(defaultValue = "") String usermail) {
        log.info("currPassword: '" + currPassword + "'");
        log.info("newPassword1: '" + newPassword1 + "'");
        log.info("newPassword2: '" + newPassword2 + "'");

        if (usermail.equals("")){
            log.info("User is blank");
            //TODO: add fail page
        }

        Optional<Employee> employeeOptional = userRepository.findByEmail(usermail);
        if (!employeeOptional.isPresent()){
            log.info("Not found user with email '" + usermail + "'");
            return "password-update";
        }

        if (isDataBlank(currPassword, newPassword1, newPassword2, usermail)){
            log.info("Sent empty data");
            return "password-update";
        }

        String problem = checkPasswords(currPassword, newPassword1, newPassword2, usermail);
        if (!problem.equals("")){
            log.warn("Found error before resetting password: " + problem);
        }

        log.info("TODO: actually change the password now");
        return "password-update";
    }

    Boolean isEmailValid(String email) {
        return (email != null && !email.equals(""));
    }

    Boolean isDataBlank(String currPassword, String newPassword1, String newPassword2, String username) {
        return currPassword.equals("") || newPassword1.equals("") || newPassword2.equals("") || username.equals("");
    }

    String checkPasswords(String currPassword, String newPassword1, String newPassword2, String username) {
        String problem = "";
        if (!newPassword1.equals(newPassword2)){
            log.info("Two inserted passwords don't match");
        } else if (currPassword.equals(newPassword1)) {
            log.info("New password is equals to old one, must be different");
        }
        return problem;
    }
}
