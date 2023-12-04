package com.example.CarSale.Views;

import com.example.CarSale.utils.validUser.UniqueUsername;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class RegUserView {

    @UniqueUsername
    private String username;
    private String firstName;
    private String lastName;
    private String password;


    @NotEmpty(message = "User Username must not be null or empty!")
    @Length(min = 6, message = "Username must be minimum six characters!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = "User Firstname must not be null or empty!")
    @Length(min = 2, message = "FirstName must be minimum two characters!")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotEmpty(message = "User Lastname must not be null or empty!")
    @Length(min = 2, message = "LastName must be minimum two characters!")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotEmpty(message = "User password must not be null or empty!")
    @Length(min = 6, message = "Password must be minimum six characters!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
