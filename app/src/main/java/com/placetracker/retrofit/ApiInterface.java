package com.placetracker.retrofit;

import com.placetracker.domain.AuthTokenResponse;
import com.placetracker.domain.Login;
import com.placetracker.domain.PlaceSelfie;
import com.placetracker.domain.PlaceSelfieRest;
import com.placetracker.domain.SuccessResponse;
import com.placetracker.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    /*@GET("/todos")
    Call<List<User>> getTodos();

    @GET("/todos/{id}")
    Call<User> getTodo(@Path("id") int id);

    @GET("/todos")
    Call<List<User>> getTodosUsingQuery(@Query("userId") int userId, @Query("completed") boolean completed);
    */
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/placeselfie/add")
    Call<PlaceSelfieRest> selfieAdd( @Header("Authorization") String authHeader, @Body PlaceSelfieRest placeSelfie);

    @POST("/api/auth/login")
    Call<AuthTokenResponse> login(@Body Login login);

    @POST("/api/auth/register")
    Call<SuccessResponse> signup(@Body User user);

    @POST("/api/auth/updateregitration")
    Call<SuccessResponse> updateprofile(@Body User user);

    @GET("/api/auth/byid/{id}")
    Call<User> getuserById(
            //@Header("Authorization") String authHeader,
            @Path(value = "id") String id) ;

}
