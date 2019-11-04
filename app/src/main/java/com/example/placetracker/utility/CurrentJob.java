package com.example.placetracker.utility;

import com.example.placetracker.MainActivity;
import com.example.placetracker.domain.PlaceSelfie;

public class CurrentJob {
    private static CurrentJob currentJob;
    private PlaceSelfie placeSelfie;
    private MainActivity mainActivity;

    private CurrentJob() {
    }

    public PlaceSelfie getPlaceSelfie() {
        return placeSelfie;
    }

    public void setPlaceSelfie(PlaceSelfie placeSelfie) {
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
