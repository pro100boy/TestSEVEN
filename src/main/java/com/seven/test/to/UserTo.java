package com.seven.test.to;

import com.seven.test.model.Company;
import com.seven.test.util.validation.EnsureEmail;
import com.seven.test.util.validation.EnsureNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserTo  extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "*Please provide name")
    @Length(min = 3, max = 255, message = "*Name must have at least 3 characters")
    @SafeHtml
    protected String name;

    @NotEmpty(message = "*Please provide your last name")
    @Length(min = 3, max = 255, message = "*Last name must have at least 3 characters")
    @SafeHtml
    private String lastname;

    @EnsureEmail(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide a valid Email")
    @SafeHtml
    private String email;

    @Length(min = 5, message = "*Password must be at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    @SafeHtml
    private String password;

    @Length(min = 7, max = 30)
    @EnsureNumber(message = "Phone number is not valid")
    private String phone;

    private Company company;
}
