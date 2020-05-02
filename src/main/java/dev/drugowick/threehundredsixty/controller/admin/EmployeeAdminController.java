package dev.drugowick.threehundredsixty.controller.admin;

import dev.drugowick.threehundredsixty.controller.BaseController;
import dev.drugowick.threehundredsixty.domain.entity.Employee;
import dev.drugowick.threehundredsixty.domain.repository.EmployeeRepository;
import dev.drugowick.threehundredsixty.dto.EmployeeDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class EmployeeAdminController extends BaseController {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeAdminController(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
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
        optionalEmployee.ifPresent(employee -> model.addAttribute("employee", employeeToEmployeeNewDto(employee)));
        return "admin/employee-edit";
    }

    @RequestMapping(value = "/employees/{id}", method = RequestMethod.POST)
    public String saveOne(@ModelAttribute("employee") @Valid EmployeeDto employeeDto,
                          BindingResult bindingResult,
                          @PathVariable Long id
    ) {
        if (bindingResult.hasErrors()) return "admin/employee-edit";
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        optionalEmployee.ifPresent(savedEmployee -> {
            Employee employee = employeeNewDtoToEmployee(employeeDto);
            employee.setPassword(savedEmployee.getPassword());
            employeeRepository.save(employee);
        });
        return "redirect:/admin/employees";
    }

    @RequestMapping(value = "/employees/new", method = RequestMethod.GET)
    public String newEmployee(Model model) {
        EmployeeDto employeeDto = new EmployeeDto();
        model.addAttribute("employee", employeeDto);
        return "admin/employee-edit";
    }

    @RequestMapping(value = "/employees/new", method = RequestMethod.POST)
    public String save(@ModelAttribute("employee") @Valid EmployeeDto employeeDto,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "admin/employee-edit";
        Employee employee = employeeNewDtoToEmployee(employeeDto);
        employee.setPassword(passwordEncoder.encode("password"));
        employeeRepository.save(employee);
        return "redirect:/admin/employees";
    }

    @Transactional
    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public String toggleActive(Long id) {
        employeeRepository.findById(id).ifPresent(employee -> {
            if (employee.isEnabled()) employee.setEnabled(false);
            else employee.setEnabled(true);
        });
        return "redirect:/admin/employees";
    }

    /**
     * This is a DTO transformation on a controller. An obvious TODO is to do it on a proper transformation layer.
     *
     * @param employeeDto a DTO to be transformed.
     * @return an Employee.
     */
    private Employee employeeNewDtoToEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();

        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setPosition(employeeDto.getPosition());
        employee.setEmail(employeeDto.getEmail());
        employee.setRoles(employeeDto.getRoles());
        employee.setEnabled(employeeDto.isEnabled());

        return employee;
    }

    /**
     * This is a DTO transformation on a controller. An obvious TODO is to do it on a proper transformation layer.
     *
     * @param employee
     * @return
     */
    private Object employeeToEmployeeNewDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setPosition(employee.getPosition());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setRoles(employee.getRoles());
        employeeDto.setEnabled(employee.isEnabled());

        return employeeDto;
    }
}
