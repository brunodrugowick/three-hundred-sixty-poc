package dev.drugowick.threehundredsixty.service;

import dev.drugowick.threehundredsixty.domain.dto.QuestionPositionScore;
import dev.drugowick.threehundredsixty.domain.entity.Employee;
import dev.drugowick.threehundredsixty.domain.entity.Question;
import dev.drugowick.threehundredsixty.domain.repository.EmployeeRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Repository // Spring translates some persistence exceptions and give we're using EntityManager here... this @Repository sounds like a good idea.
public class ReportsServiceImpl implements ReportsService {

    @PersistenceContext
    private EntityManager manager;

    private final EmployeeRepository employeeRepository;

    public ReportsServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<QuestionPositionScore> getUserReport(String username) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(username);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();

            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<QuestionPositionScore> query = builder.createQuery(QuestionPositionScore.class);
            Root<Question> root = query.from(Question.class);

            CompoundSelection<QuestionPositionScore> selection = builder.construct(QuestionPositionScore.class,
                    root.get("title"),
                    root.get("description"),
                    root.get("evaluator").get("position"),
                    builder.avg(root.get("evaluationValue")));

            query.select(selection);
            query.where(
                    builder.equal(root.get("evaluated"), employee),
                    builder.notEqual(root.get("evaluationValue"), 0),
                    builder.notEqual(root.get("evaluator"), employee) // Excludes self evaluation
            );
            query.groupBy(
                    root.get("title"),
                    root.get("description"),
                    root.get("evaluator").get("position"));

            return manager.createQuery(query).getResultList();
        }

        return null;
    }
}
