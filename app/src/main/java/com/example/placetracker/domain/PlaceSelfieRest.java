package com.example.placetracker.domain;

import java.io.Serializable;
import java.util.Date;

public class PlaceSelfieRest implements Serializable {

    private String id;
    private String firstSelfie;
    private String lastSelfie;
    private double latitude1;
    private double longitude1;
    private double latitude2;
    private double longitude2;
    private Date firstSelfieDate;
    private Date lastSelfieDate;
    private String jobName;
    private int isJobAdded;
    private String jobDescription;
    private Date dateOfJob;
    private String userName;
    private String email;

    public PlaceSelfieRest() {
    }

    public PlaceSelfieRest(String _id, String firstSelfie, String lastSelfie, double latitude1,
                           double longitude1, double latitude2, double longitude2,
                           Date firstSelfieDate, Date lastSelfieDate, String jobName,
                           int isJobAdded, String jobDescription, Date dateOfJob, String userName,
                           String email) {
        this.id = _id;
        this.firstSelfie = firstSelfie;
        this.lastSelfie = lastSelfie;
        this.latitude1 = latitude1;
        this.longitude1 = longitude1;
        this.latitude2 = latitude2;
        this.longitude2 = longitude2;
        this.firstSelfieDate = firstSelfieDate;
        this.lastSelfieDate = lastSelfieDate;
        this.jobName = jobName;
        this.isJobAdded = isJobAdded;
        this.jobDescription = jobDescription;
        this.dateOfJob = dateOfJob;
        this.userName = userName;
        this.email = email;
    }

    public PlaceSelfieRest( String firstSelfie, String lastSelfie, double latitude1,
                           double longitude1, double latitude2, double longitude2,
                           Date firstSelfieDate, Date lastSelfieDate, String jobName,
                           int isJobAdded, String jobDescription, Date dateOfJob, String userName,
                           String email) {
        this.firstSelfie = firstSelfie;
        this.lastSelfie = lastSelfie;
        this.latitude1 = latitude1;
        this.longitude1 = longitude1;
        this.latitude2 = latitude2;
        this.longitude2 = longitude2;
        this.firstSelfieDate = firstSelfieDate;
        this.lastSelfieDate = lastSelfieDate;
        this.jobName = jobName;
        this.isJobAdded = isJobAdded;
        this.jobDescription = jobDescription;
        this.dateOfJob = dateOfJob;
        this.userName = userName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstSelfie() {
        return firstSelfie;
    }

    public void setFirstSelfie(String firstSelfie) {
        this.firstSelfie = firstSelfie;
    }

    public String getLastSelfie() {
        return lastSelfie;
    }

    public void setLastSelfie(String lastSelfie) {
        this.lastSelfie = lastSelfie;
    }

    public double getLatitude1() {
        return latitude1;
    }

    public void setLatitude1(double latitude1) {
        this.latitude1 = latitude1;
    }

    public double getLongitude1() {
        return longitude1;
    }

    public void setLongitude1(double longitude1) {
        this.longitude1 = longitude1;
    }

    public double getLatitude2() {
        return latitude2;
    }

    public void setLatitude2(double latitude2) {
        this.latitude2 = latitude2;
    }

    public double getLongitude2() {
        return longitude2;
    }

    public void setLongitude2(double longitude2) {
        this.longitude2 = longitude2;
    }

    public Date getFirstSelfieDate() {
        return firstSelfieDate;
    }

    public void setFirstSelfieDate(Date firstSelfieDate) {
        this.firstSelfieDate = firstSelfieDate;
    }

    public Date getLastSelfieDate() {
        return lastSelfieDate;
    }

    public void setLastSelfieDate(Date lastSelfieDate) {
        this.lastSelfieDate = lastSelfieDate;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getIsJobAdded() {
        return isJobAdded;
    }

    public void setIsJobAdded(int isJobAdded) {
        this.isJobAdded = isJobAdded;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Date getDateOfJob() {
        return dateOfJob;
    }

    public void setDateOfJob(Date dateOfJob) {
        this.dateOfJob = dateOfJob;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}