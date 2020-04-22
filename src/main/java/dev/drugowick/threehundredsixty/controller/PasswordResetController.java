package dev.drugowick.threehundredsixty.controller;

import dev.drugowick.threehundredsixty.domain.entity.Employee;
import dev.drugowick.threehundredsixty.domain.repository.ResetTokenRepository;
import dev.drugowick.threehundredsixty.domain.repository.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@Slf4j
public class PasswordResetController {
    Logger logEx = LoggerFactory.getLogger("EXCEPTIONS");
    Logger logBu = LoggerFactory.getLogger("BUSINESS");

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/password/update")
    public ModelAndView passwordUpdate(ModelAndView modelAndView,
                                       @RequestParam(defaultValue = "") String usermail) {
        Boolean isEmailValid = isEmailValid(usermail);
        modelAndView.getModel().put("updateSuccessful", null);
        modelAndView.getModel().put("updateDesc", "");
        modelAndView.getModel().put("isEmailValid", isEmailValid);
        modelAndView.setViewName("password-update");
        return modelAndView;
    }

    @PostMapping("/password/reset")
    public ModelAndView passwordReset(
            ModelAndView modelAndView,
            @RequestParam(defaultValue = "") String currPassword,
            @RequestParam(defaultValue = "") String newPassword1,
            @RequestParam(defaultValue = "") String newPassword2,
            @RequestParam(defaultValue = "") String usermail) {

        if (usermail.equals("")){
            return sendBackToView(modelAndView, false, "User informed is blank");
        }

        Optional<Employee> employeeOptional = userRepository.findByEmail(usermail);
        if (!employeeOptional.isPresent()){
            return sendBackToView(modelAndView, false, "Not found user with email '" + usermail + "'");
        }

        if (isDataBlank(currPassword, newPassword1, newPassword2, usermail)){
            return sendBackToView(modelAndView, false, "Sent empty data");
        }

        String problem = checkPasswords(currPassword, newPassword1, newPassword2, usermail);
        if (!problem.equals("")){
            return sendBackToView(modelAndView, false, "Found error before resetting password: " + problem);
        }

        Employee employee = employeeOptional.get();
        employee.setPassword(passwordEncoder.encode(newPassword1));
        try {
            userRepository.save(employee);
            logBu.info("Updated password of user " + employee);
        } catch (Exception e){
            String msg = "Error when updating user '" + employee + "' password";
            logBu.error(msg, e);
            logEx.error(msg, e);
        }

        /// update ok:
        return sendBackToView(modelAndView, true, "Successfully changed password");
    }

    private ModelAndView sendBackToView(ModelAndView modelAndView, Boolean updateSuccessful, String updateDesc) {
        log.info(updateDesc);
        modelAndView.getModel().put("updateSuccessful", updateSuccessful);
        modelAndView.getModel().put("updateDesc", updateDesc);
        modelAndView.setViewName("password-update");
        return modelAndView;
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
