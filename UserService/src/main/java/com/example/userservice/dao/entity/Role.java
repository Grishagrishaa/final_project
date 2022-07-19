package com.example.userservice.dao.entity;

import com.example.userservice.dao.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role", schema = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id", name = "roleIdConstraint"),
        @UniqueConstraint(columnNames = "name", name = "roleNameConstraint")
})
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private ERole name;
    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<User> users;

    public Role(Long id, ERole name) {
        this.id = id;
        this.name = name;
    }

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public ERole getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public String getAuthority() {
        return name.name();
    }
}
