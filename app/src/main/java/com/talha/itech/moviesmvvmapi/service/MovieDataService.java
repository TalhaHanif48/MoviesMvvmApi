package com.talha.itech.moviesmvvmapi.service;

import com.talha.itech.moviesmvvmapi.model.MovieDBResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDataService {


    @GET("movie/popular")
    Call<MovieDBResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieDBResponse> getPopularMoviesWithpaging(@Query("api_key") String apiKey,@Query("page") Long page);


}
