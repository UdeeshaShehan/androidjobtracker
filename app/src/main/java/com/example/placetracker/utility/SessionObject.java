package com.example.placetracker.utility;

public class SessionObject {

    String username;
    String token;
    String email;

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

    public static SessionObject  getInstance(){
        if(sessionObject == null)
            sessionObject = new SessionObject();
        return sessionObject;
    }

    public void clear(){
        setToken(null);
        setUsername(null);
    }
}
