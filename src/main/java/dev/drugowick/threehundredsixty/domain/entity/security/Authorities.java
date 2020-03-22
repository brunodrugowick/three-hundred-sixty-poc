package dev.drugowick.threehundredsixty.domain.entity.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username_id", "authority"}))
@Data
@NoArgsConstructor
public class Authorities {

    @Id
    private Long id;

    @ManyToOne(optional = false)
    private User username;

    private String authority;
}
