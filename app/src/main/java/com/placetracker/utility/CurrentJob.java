package com.placetracker.utility;

import com.placetracker.MainActivity;
import com.placetracker.domain.PlaceSelfie;
import com.placetracker.domain.PlaceSelfieRest;

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
