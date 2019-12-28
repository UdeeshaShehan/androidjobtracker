package com.placetracker.domain;

public class User {

    private String email;
    private String address;
    private String mobileNumber;
    private String password;
    private String fullname;
    private String mentorMobileNumber;
    private String organization;

    public User() {
    }

    public User(String email, String address, String mobileNumber, String password, String fullname,
                String mentorMobileNumber, String organization) {
        this.email = email;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.fullname = fullname;
        this.mentorMobileNumber = mentorMobileNumber;
        this.organization = organization;
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
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMentorMobileNumber() {
        return mentorMobileNumber;
    }

    public void setMentorMobileNumber(String mentorMobileNumber) {
        this.mentorMobileNumber = mentorMobileNumber;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
