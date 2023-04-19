package com.example.userservice.dao.entity;

import com.example.userservice.dao.entity.enums.EStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.*;

@Entity
@Table(name = "users", schema = "signed", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nick", name = "nickConstraint"),
        @UniqueConstraint(columnNames = "mail", name = "emailConstraint")
})
@EntityListeners(AuditingEntityListener.class)
@Data @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseRecord{

    private String mail;
    private String nick;

    @ManyToMany(fetch = FetchType.EAGER)//for test
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_uuid"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Enumerated(value = EnumType.STRING)
    private EStatus status;

    private String password;
}
