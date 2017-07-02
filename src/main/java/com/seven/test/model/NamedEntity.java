package com.seven.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class NamedEntity extends BaseEntity {

    @NotEmpty(message = "*Please provide name")
    @Column(name = "name", nullable = false)
    @Length(min = 3, max = 255, message = "*Name must have at least 3 characters")
    @SafeHtml
    protected String name;
}
