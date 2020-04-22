package dev.drugowick.threehundredsixty.controller.profile;

import dev.drugowick.threehundredsixty.controller.BaseController;
import dev.drugowick.threehundredsixty.domain.entity.Employee;
import dev.drugowick.threehundredsixty.domain.repository.EmployeeRepository;
import dev.drugowick.threehundredsixty.dto.PasswordChange;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class PasswordChangeController extends BaseController {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordChangeController(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute("passwordChange")
    public PasswordChange passwordChangeDto() {
        return new PasswordChange();
    }

    @GetMapping("/password")
    public String passwordChange() {
        return "profile/password-change";
    }

    @PostMapping("/password")
    public String passwordChangePost(@ModelAttribute("passwordChange") @Valid PasswordChange passwordChange,
                                     BindingResult bindingResult,
                                     Principal principal) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(principal.getName());
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if (!passwordEncoder.matches(passwordChange.getCurrentPassword(), employee.getPassword()))
                bindingResult.addError(new ObjectError("currentPassword", "Senha atual está incorreta"));
            if (bindingResult.hasErrors())
                return "profile/password-change";
            String newPassword = passwordChange.getNewPassword();
            employee.setPassword(passwordEncoder.encode(newPassword));
            employeeRepository.save(employee);
        }

        return "redirect:/";
    }
}
