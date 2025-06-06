package com.example.webapp.models;

import jakarta.validation.constraints.*;

public class ClientDto {
    @NotEmpty(message = "The first name is requiered")
    @Pattern(regexp = "^[A-Z].*$", message = "First name must start with a capital letter")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "First name must contain only letters")
    private String firstName;

    @Pattern(regexp = "^[A-Z].*$", message = "Last name must start with a capital letter")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Last name must contain only letters")
    @NotEmpty(message = "The last name is requiered")
    private String lastName;


    @NotEmpty(message = "Email required")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Invalid email format")
    private String email;

    private String phone;
    private String address;
    @NotEmpty(message = "the status is required")
    private String status;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}