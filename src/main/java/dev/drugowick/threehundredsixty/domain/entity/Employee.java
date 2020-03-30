package dev.drugowick.threehundredsixty.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String position;

    // User stuff
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String roles;

    @NotNull
    private boolean enabled = true;

    @OneToMany(mappedBy = "evaluator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbacks = new ArrayList<>();
}
