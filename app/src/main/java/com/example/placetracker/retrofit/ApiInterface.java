package com.example.placetracker.retrofit;

import com.example.placetracker.domain.AuthTokenResponse;
import com.example.placetracker.domain.Login;
import com.example.placetracker.domain.SuccessResponse;
import com.example.placetracker.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    /*@GET("/todos")
    Call<List<User>> getTodos();

    @GET("/todos/{id}")
    Call<User> getTodo(@Path("id") int id);

    @GET("/todos")
    Call<List<User>> getTodosUsingQuery(@Query("userId") int userId, @Query("completed") boolean completed);
    */
    @POST("/api/auth/login")
    Call<AuthTokenResponse> login(@Body Login login);

    @POST("/api/auth/register")
    Call<SuccessResponse> signup(@Body User user);

}
