package com.placetracker.retrofit;

import com.placetracker.domain.Location;
import com.placetracker.domain.PlaceSelfieRest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SelfieApiInterface {
    //@Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/placeselfie/email/{email}")
    Call<List<PlaceSelfieRest>> getplaceSelfiesByEmail(
            //@Header("Authorization") String authHeader,
            @Path(value = "email") String email) ;

    @DELETE("/placeselfie/{id}")
    Call<Map<String, Boolean>> deleteSelfie(@Path(value = "id") String id);

    //@Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/placeselfie/add")
    Call<PlaceSelfieRest> selfieAdd(
            //@Header("Authorization") String authHeader,
            @Body PlaceSelfieRest placeSelfie);

    @PUT("/placeselfie/{id}")
     Call<PlaceSelfieRest> updateSelfie(
            @Path(value = "id") String id,  @Body PlaceSelfieRest userDetails);

    @PUT("/placeselfie/location/{id}")
    Call<PlaceSelfieRest> updateLocation(
            @Path(value = "id") String id,  @Body Location location);
}
