package com.example.thodasukoon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    // User Register
    @POST("api/users/register")
    Call<LoginResponse> registerUser(@Body LoginRequest request);

    // User Login
    @POST("api/users/login")
    Call<LoginResponse> loginUser(@Body LoginRequest request);

    @POST("api/users/logout")
    Call<Void> logoutUser();



    // Get nearby doctors by location
    @GET("api/appointments/doctors")
    Call<List<Doctor>> getDoctorsByLocation(
            @Query("lat") double lat,
            @Query("lng") double lng
    );

    // Get chatbot reply
    @POST("api/chat/message")
    Call<ChatResponse> getChatReply(@Body ChatRequest request);
}
