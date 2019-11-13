package com.example.placetracker.utility;

import com.example.placetracker.MainActivity;
import com.example.placetracker.domain.PlaceSelfie;
import com.example.placetracker.domain.PlaceSelfieRest;

public class CurrentJob {
    private static CurrentJob currentJob;
    private PlaceSelfieRest placeSelfie;
    private MainActivity mainActivity;

    private CurrentJob() {
    }

    public PlaceSelfieRest getPlaceSelfie() {
        return placeSelfie;
    }

    public void setPlaceSelfie(PlaceSelfieRest placeSelfie) {
        this.placeSelfie = placeSelfie;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public static CurrentJob getInstance() {
        if(currentJob == null)
            currentJob = new CurrentJob();
        return currentJob;
    }
}
