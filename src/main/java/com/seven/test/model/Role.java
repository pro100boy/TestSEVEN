package com.seven.test.model;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role extends BaseEntity{

    @Column(name = "role")
    private String role;

    public Role() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
