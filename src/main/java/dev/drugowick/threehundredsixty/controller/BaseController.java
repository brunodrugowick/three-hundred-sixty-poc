package dev.drugowick.threehundredsixty.controller;

import dev.drugowick.threehundredsixty.domain.entity.Employee;
import dev.drugowick.threehundredsixty.domain.repository.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.Optional;

/**
 * This is a base controller responsible for basic info that EVERY page must have.
 */
public class BaseController {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute("username")
    public String username(Principal principal) {
        return principal == null ? "" : principal.getName();
    }

    @ModelAttribute("fullname")
    public String fullname(Principal principal) {
        if (principal == null) {
            return "";
        } else {
            Optional<Employee> optionalEmployee = userRepository.findByEmail(principal.getName());
            return optionalEmployee.map(employee -> employee.getName()).orElse("");
        }
    }

    @ModelAttribute("admin")
    public boolean admin(Principal principal) {
        if (principal == null) {
            return false;
        } else {
            Optional<Employee> optionalEmployee = userRepository.findByEmail(principal.getName());

            return optionalEmployee.map(employee -> employee.getRoles().contains("ROLE_ADMIN")).orElse(false);
        }
    }
}
