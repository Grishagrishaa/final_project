package com.example.userservice.dao.entity;

import com.example.userservice.dao.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Table(name = "roles", schema = "signed", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id", name = "roleIdConstraint"),
        @UniqueConstraint(columnNames = "name", name = "roleNameConstraint")
})
@Data
@NoArgsConstructor @Getter
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @JsonIgnore//INSTEAD OF NAME WE SHOW AUTHORITY - > THE SAME
    private ERole name;

    public Role(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return name.name();
    }
}
