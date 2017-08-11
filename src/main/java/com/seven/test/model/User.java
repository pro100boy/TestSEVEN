package com.seven.test.model;

import com.seven.test.util.validation.EnsureEmail;
import com.seven.test.util.validation.EnsureNumber;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"password", "company"}, callSuper = true)
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
    //@JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private Set<Role> roles;

    @Column(name = "phone")
    @Length(min = 7, max = 30)
    @EnsureNumber(message = "Phone number is not valid")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonManagedReference(value="company-users")
    private Company company;

    public User(Integer id, String name, String lastname, String email, String password, String phone, Role role, Role... roles) {
        this(id, name, lastname, email, password, phone, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String lastname, String email, String password, String phone, Set<Role> roles) {
        super(id, name);
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        setRoles(roles);
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
    }
}
