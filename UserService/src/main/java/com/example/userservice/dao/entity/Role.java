package com.example.userservice.dao.entity;

import com.example.userservice.dao.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles", schema = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id", name = "roleIdConstraint"),
        @UniqueConstraint(columnNames = "name", name = "roleNameConstraint")
})
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

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public ERole getName() {
        return name;
    }

    @Override
    public String getAuthority() {
        return name.name();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
