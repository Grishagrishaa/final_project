package com.example.userservice.dao.entity;

import com.example.userservice.dao.entity.enums.EStatus;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.*;

@Entity
@Table(name = "users", schema = "signed", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nick", name = "nickConstraint"),
        @UniqueConstraint(columnNames = "mail", name = "emailConstraint")
})
@EntityListeners(AuditingEntityListener.class)
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

    public User() {
    }



    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "mail='" + mail + '\'' +
                ", nick='" + nick + '\'' +
                ", roles=" + roles +
                ", status=" + status +
                ", password='" + password + '\'' +
                '}';
    }
}
