package com.seven.test.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"users", "reports"}, callSuper = true)
@NamedEntityGraph(name = Company.GRAPH_WITH_USERS_REPORTS, attributeNodes =
        {
                @NamedAttributeNode("users"),
                @NamedAttributeNode("reports")
        })
@Table(name = "company", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "email"}, name = "idx_company_name_email")})
public class Company extends NamedEntity {
    public static final String GRAPH_WITH_USERS_REPORTS = "Company.withUsersReports";
    @Column(name = "email", nullable = false)
    @Email
    @NotBlank
    @SafeHtml
    private String email;

    @Column(name = "address", nullable = false)
    @NotBlank
    @Length(min = 5, max = 255, message = "*Address must have at least 3 characters")
    @SafeHtml
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    //@JsonBackReference(value = "company-users")
    private Set<User> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    //@JsonBackReference(value = "company-reports")
    private Set<Report> reports;

    public Company(Integer id, String name, String email, String address) {
        super(id, name);
        this.email = email;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
