package com.placetracker.utility;

public class SessionObject {

    String username;
    String token;
    String email;
    String id;
    String mobileNumber;
    String mentorMobileNumber;
    String organization;
    String role;

    private static SessionObject sessionObject;

    private SessionObject(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static SessionObject  getInstance(){
        if(sessionObject == null)
            sessionObject = new SessionObject();
        return sessionObject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void clear(){
        setToken(null);
        setUsername(null);
        setId(null);
    }
}
