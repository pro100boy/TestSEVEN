package com.seven.test.model;

import com.seven.test.util.EnsureNumber;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.data.annotation.Transient;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends NamedEntity {

    @NotEmpty(message = "*Please provide your last name")
    @Column(name = "lastname", nullable = false)
    @Length(min = 3, max = 255)
    @SafeHtml
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    @SafeHtml
    private String email;

    @Column(name = "password", nullable = false)
    @Length(min = 5, max = 64, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    //@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$", message = "*Password must contains latin symbols (in upper and lower case) and digits")
    @Transient
    @SafeHtml
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
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
        this(u.getId(), u.getName(), u.getLastname(), u.getEmail(), u.getPhone(), u.getPassword(), u.getRoles());
    }

    public User() {
    }

    public User(Integer id, String name, String lastname, String email, String password, String phone, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
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

    public void setRoles(Set<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : roles;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "User (" +
                "id=" + getId() +
                ", firstname='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", phone='" + phone + '\'' +
                '}';
    }
}
