package com.seven.test.model;

import com.seven.test.util.EnsureNumber;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends BaseEntity {

    @NotBlank // (check for symbols, except spaces)
    @Column(name = "firstname", nullable = false)
    @Length(min = 1, max = 255, message = "First name is not valid")
    @SafeHtml
    private String firstname;

    @NotBlank // (check for symbols, except spaces)
    @Column(name = "lastname", nullable = false)
    @Length(min = 1, max = 255, message = "Last name is not valid")
    @SafeHtml
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @SafeHtml
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Length(min = 5, max = 64)
    @SafeHtml
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 10)
    private Set<Role> roles;

    @Column(name = "phone")
    @Length(min = 6, max = 30)
    @EnsureNumber(message = "Phone number is not valid")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    public User(User u) {
        this(u.getId(), u.getFirstname(), u.getLastname(), u.getEmail(), u.getPhone(), u.getPassword(), u.getRoles());
    }

    public User(Integer id, String firstname, String lastname, String email, String password, String phone, Role role, Role... roles) {
        this(id, firstname, lastname, email, password, phone, EnumSet.of(role, roles));
    }

    public User() {
    }

    public User(Integer id, String firstname, String lastname, String email, String password, String phone, Set<Role> roles) {
        super(id);
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        setRoles(roles);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
    }
    @Override
    public String toString() {
        return "User (" +
                "id=" + getId() +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", phone='" + phone + '\'' +
                '}';
    }
}
