package com.seven.test.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role extends BaseEntity{

    @Column(name = "role")
    private String role;

    @Override
    public String toString() {
        return role;
    }
}
