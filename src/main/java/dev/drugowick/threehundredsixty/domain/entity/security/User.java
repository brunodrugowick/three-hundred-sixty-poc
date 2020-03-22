package dev.drugowick.threehundredsixty.domain.entity.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Data
@NoArgsConstructor
public class User {

    @Id
    private Long id;

    private String username;
    private String password;

    @NotNull
    private boolean enabled;
}
