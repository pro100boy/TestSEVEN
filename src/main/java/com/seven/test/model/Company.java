package com.seven.test.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "company", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "email"}, name = "idx_company_name_email")})
public class Company extends NamedEntity {
    @Column(name = "email", nullable = false)
    @Email
    @NotBlank
    @SafeHtml
    private String email;

    @Column(name = "address", nullable = false)
    @NotBlank
    @Length(min = 5, max = 255)
    @SafeHtml
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    private Set<User> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    private Set<Report> reports;

    public Company() {
    }

    // TODO продумать конструкторы

    public Company(Integer id, String name, String email, String address) {
        super(id, name);
        this.email = email;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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
