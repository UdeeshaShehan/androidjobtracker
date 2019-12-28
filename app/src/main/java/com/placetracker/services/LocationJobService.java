package com.placetracker.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;

import com.placetracker.domain.Location;
import com.placetracker.domain.PlaceSelfieRest;
import com.placetracker.retrofit.SelfieApiClient;
import com.placetracker.retrofit.SelfieApiInterface;
import com.placetracker.utility.CurrentJob;
import com.placetracker.utility.SessionObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationJobService extends JobService {
    private static final String TAG = "LocationJobService";
    private boolean jobCancelled = false;
    private LocationService locationTrack;
    private SelfieApiInterface selfieApiInterface;
    private String id;

    @Override
    public void onCreate() {
        super.onCreate();
        id = CurrentJob.getInstance().getPlaceSelfie().getId();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        try {
            if (CurrentJob.getInstance().getPlaceSelfie() != null) {
                id = CurrentJob.getInstance().getPlaceSelfie().getId();
            }
        } catch (Exception e) {

        }
        selfieApiInterface = SelfieApiClient.getClient(SessionObject.getInstance().getToken()).
                create(SelfieApiInterface.class);
        locationTrack = new LocationService(this);
        doBackgroundWork(params);

        return true;
    }

    private void doBackgroundWork(final JobParameters params) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (jobCancelled) {
                    return;
                }
                if (locationTrack.canGetLocation()) {
                    //locationTrack.getLocation();
                    Double longitude = locationTrack.getLongitude();
                    Double latitude = locationTrack.getLatitude();
                    updateLocation(new Location(longitude, latitude));
                } else {

                    locationTrack.showSettingsAlert();
                }

                jobFinished(params, false);
            }
        }).start();
    }

    private void updateLocation(Location location) {
        Call<PlaceSelfieRest> putCall = selfieApiInterface.updateLocation(id, location);

        putCall.enqueue(new Callback<PlaceSelfieRest>() {
            @Override
            public void onResponse(Call<PlaceSelfieRest> call, final Response<PlaceSelfieRest> response) {
                try {
                    Log.e(TAG, "onResponse: " + response.body());
                    //Toast.makeText(getBaseContext(), "Added", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    //Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PlaceSelfieRest> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage() );
                //Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        if (locationTrack != null)
            locationTrack.stopListener();
        return true;
    }
}
