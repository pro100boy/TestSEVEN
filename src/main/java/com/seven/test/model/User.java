package com.seven.test.model;

import com.fasterxml.jackson.annotation.*;
import com.seven.test.util.validation.EnsureEmail;
import com.seven.test.util.validation.EnsureNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "roles", callSuper = true)
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends NamedEntity {

    @NotEmpty(message = "*Please provide your last name")
    @Column(name = "lastname", nullable = false)
    @Length(min = 3, max = 255, message = "*Last name must have at least 3 characters")
    @SafeHtml
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    @EnsureEmail(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide a valid Email")
    @SafeHtml
    private String email;

    @Column(name = "password", nullable = false)

    // https://security.stackexchange.com/a/39851
    // do not bound max length of the password
    @Length(min = 5, message = "*Password must be at least 5 characters")
//    @Length.List({
//            @Length(min = 5, message = "*Password must be at least 5 characters"),
//            @Length(max = 64, message = "*Password must be less than 64 characters")
//    })
    @NotEmpty(message = "*Please provide your password")
    //@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$", message = "*Password must contains latin symbols (in upper and lower case) and digits")
    //@Transient
    @SafeHtml
    @JsonIgnore
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    //@JsonIgnore
    private Set<Role> roles;

    @Column(name = "phone")
    @Length(min = 7, max = 30)
    @EnsureNumber(message = "Phone number is not valid")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference(value="company-users")
    private Company company;

    public void setRoles(Set<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : roles;
    }

    @Override
    public String toString() {
        return "User (" +
                "id=" + getId() +
                ", firstname='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                //", company ='" + getCompany().getName() + '\'' +
                //", password='" + password + '\'' +
                ", roles=" + roles.stream().map(Role::getRole).collect(Collectors.joining(", ")) +
                ", phone='" + phone + '\'' +
                '}';
    }
}
