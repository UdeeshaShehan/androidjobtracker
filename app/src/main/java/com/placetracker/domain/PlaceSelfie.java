package com.placetracker.domain;

import com.placetracker.utility.CommonUtility;
import com.placetracker.utility.SessionObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlaceSelfie implements Serializable {

    private int id;
    private byte[] firstSelfie;
    private byte[] lastSelfie;
    /*private double latitude1;
    private double longitude1;
    private double latitude2;
    private double longitude2;*/
    private Location firstLocation;
    private Location lastLocation;
    private Date firstSelfieDate;
    private Date lastSelfieDate;
    private String jobName;
    private int isJobAdded;
    private String jobDescription;
    private Date dateOfJob;
    private String address1;
    private String address2;
    private List<Location> locations = new ArrayList<>();

    public PlaceSelfie(int id, byte[] firstSelfie, byte[] lastSelfie, Location firstLocation,
                       Location lastLocation, Date firstSelfieDate,
                       Date lastSelfieDate, String jobName, int isJobAdded, String jobDescription,
                       Date dateOfJob, List<Location> locations) {
        this.id = id;
        this.firstSelfie = firstSelfie;
        this.lastSelfie = lastSelfie;
        /*this.latitude1 = latitude1;
        this.longitude1 = longitude1;
        this.latitude2 = latitude2;
        this.longitude2 = longitude2*/;
        this.firstLocation = firstLocation;
        this.lastLocation = lastLocation;
        this.firstSelfieDate = firstSelfieDate;
        this.lastSelfieDate = lastSelfieDate;
        this.jobName = jobName;
        this.isJobAdded = isJobAdded;
        this.jobDescription = jobDescription;
        this.dateOfJob = dateOfJob;
        this.locations = locations;
    }

    public PlaceSelfie( byte[] firstSelfie, byte[] lastSelfie, Location firstLocation,
                        Location lastLocation, Date firstSelfieDate,
                       Date lastSelfieDate, String jobName, int isJobAdded, String jobDescription,
                        Date dateOfJob, List<Location> locations) {
        this.firstSelfie = firstSelfie;
        this.lastSelfie = lastSelfie;
       /* this.latitude1 = latitude1;
        this.longitude1 = longitude1;
        this.latitude2 = latitude2;
        this.longitude2 = longitude2;*/
        this.firstLocation = firstLocation;
        this.lastLocation = lastLocation;
        this.firstSelfieDate = firstSelfieDate;
        this.lastSelfieDate = lastSelfieDate;
        this.jobName = jobName;
        this.isJobAdded = isJobAdded;
        this.jobDescription = jobDescription;
        this.dateOfJob = dateOfJob;
        this.locations = locations;
    }

    public PlaceSelfie(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getFirstSelfie() {
        return firstSelfie;
    }

    public void setFirstSelfie(byte[] firstSelfie) {
        this.firstSelfie = firstSelfie;
    }

    public byte[] getLastSelfie() {
        return lastSelfie;
    }

    public void setLastSelfie(byte[] lastSelfie) {
        this.lastSelfie = lastSelfie;
    }

    public Location getFirstLocation() {
        return firstLocation;
    }

    public void setFirstLocation(Location firstLocation) {
        this.firstLocation = firstLocation;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public PlaceSelfieRest getPlaceSelfieForRest(PlaceSelfieRest placeSelfieRest) {
        String _id = placeSelfieRest.getId();

        String fs = getFirstSelfie() == null || getFirstSelfie().length == 0 ? "" : CommonUtility.encodeImage(firstSelfie);
        String ls = getLastSelfie() == null || getLastSelfie().length == 0 ? "" : CommonUtility.encodeImage(lastSelfie);
        firstSelfieDate = new Date();
        lastSelfieDate = new Date();
        return new PlaceSelfieRest( _id, fs , ls, firstLocation, lastLocation, firstSelfieDate, lastSelfieDate, jobName, isJobAdded,
                jobDescription, dateOfJob, SessionObject.getInstance().getUsername(),
                SessionObject.getInstance().getMobileNumber(), locations, SessionObject.getInstance().getMentorMobileNumber(), address1, address2);
    }
}
