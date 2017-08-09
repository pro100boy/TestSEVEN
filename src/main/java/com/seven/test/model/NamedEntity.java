package com.seven.test.model;

import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EqualsAndHashCode(callSuper=true)
public class NamedEntity extends BaseEntity {

    @NotEmpty(message = "*Please provide name")
    @Column(name = "name", nullable = false)
    @Length(min = 3, max = 255, message = "*Name must have at least 3 characters")
    @SafeHtml
    protected String name;

    public NamedEntity() {
    }

    protected NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}
