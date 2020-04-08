package dev.drugowick.threehundredsixty.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotBlank
    @Size(min = 1, max = 120, message = "Nome deve conter no mínimo 1 e no máximo 120 caracteres")
    private String name;

    @NotBlank
    @Size(min = 1, max = 60, message = "Cargo deve conter no mínimo 1 e no máximo 60 caracteres")
    private String position;

    // User stuff
    @Column(nullable = false, unique = true)
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 4, max = 60, message = "Senha deve conter no mínimo 4 e no máximo 60 caracteres")
    private String password;

    private String roles;

    @NotNull
    private boolean enabled = true;

    @OneToMany(mappedBy = "evaluator", cascade = CascadeType.REFRESH, orphanRemoval = true)
    private List<Feedback> feedbacks = new ArrayList<>();

    @Override
    public String toString() {
        return name + " [" +
                position + ']';
    }
}
