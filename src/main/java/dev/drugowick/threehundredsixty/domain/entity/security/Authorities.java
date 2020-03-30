package dev.drugowick.threehundredsixty.domain.entity.security;

import dev.drugowick.threehundredsixty.domain.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "authority"}))
@Data
@NoArgsConstructor
public class Authorities {

    @Id
    private Long id;

    @ManyToOne(optional = false)
    private Employee employee;

    private String authority;
}
