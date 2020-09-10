package com.bmahatchi.gadsleaderboard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface GadsApiService {
    @GET("/api/hours")
    @Headers("Content-Type: application/json")
    Call<List<Learner>> getLeadingLearners();

    @GET("/api/skilliq")
    @Headers("Content-Type: application/json")
    Call<List<Learner>> getLeadingSkillIq();
}
