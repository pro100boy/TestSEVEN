package com.seven.test.to;

import com.seven.test.util.EnsureNumber;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @SafeHtml
    private String firstname;

    @NotBlank
    @SafeHtml
    private String lastname;

    @Email
    @NotBlank
    @SafeHtml
    private String email;

    @Size(min = 5, max = 64, message = " must between 5 and 64 characters")
    @SafeHtml
    private String password;

    @Length(min = 6, max = 30)
    @EnsureNumber(message = "Phone number is not valid")
    private String phone;

    public UserTo() {
    }

    public UserTo(Integer id, String firstname, String lastname, String email, String password, String phone) {
        super(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
