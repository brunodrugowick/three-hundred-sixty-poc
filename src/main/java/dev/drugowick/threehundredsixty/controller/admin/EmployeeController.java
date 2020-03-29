package dev.drugowick.threehundredsixty.controller.admin;

import dev.drugowick.threehundredsixty.domain.entity.Employee;
import dev.drugowick.threehundredsixty.domain.repository.EmployeeRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public EmployeeController(EmployeeRepository employeeRepository, BCryptPasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute("username")
    public String username(Principal principal) {
        return principal.getName();
    }

    @GetMapping
    @RequestMapping("/employees")
    public String list(Principal principal, Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "admin/employees-admin";
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
    public String get(Principal principal, Model model, @PathVariable Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        optionalEmployee.ifPresent(employee -> model.addAttribute("employee", employee));
        return "admin/employee-edit";
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.POST)
    public String saveOne(Principal principal, Model model,
                          Employee employee,
                          @PathVariable Long id
    ) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        optionalEmployee.ifPresent(savedEmployee -> employee.setPassword(savedEmployee.getPassword()));
        employeeRepository.save(employee);
        return "redirect:/admin/employees";
    }

    @RequestMapping(value = "/employees/new", method = RequestMethod.GET)
    public String newEmployee(Principal principal, Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "admin/employee-edit";
    }

    @RequestMapping(value = "/employees/new", method = RequestMethod.POST)
    public String save(Principal principal, Model model, Employee employee) {
        employee.setPassword(passwordEncoder.encode("password"));
        employeeRepository.save(employee);
        return "redirect:/admin/employees";
    }
}
