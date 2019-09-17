package com.talha.itech.moviesmvvmapi.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.talha.itech.moviesmvvmapi.R;
import com.talha.itech.moviesmvvmapi.service.MovieDataService;
import com.talha.itech.moviesmvvmapi.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    Application application;
    List<Movie> movieList;

    MutableLiveData<List<Movie>> mutableLiveData;


    public MovieRepository(Application application) {
        this.application = application;
        movieList = new ArrayList<>();
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Movie>> getmovieList() {

        MovieDataService movieDataService = RetrofitInstance.getInstance();
        Call<MovieDBResponse> call = movieDataService.getPopularMovies(application.getString(R.string.apiKey));
        call.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {

                MovieDBResponse movieDBResponse = response.body();

                if (movieDBResponse != null && movieDBResponse.getMovies() != null) {

                    movieList = movieDBResponse.getMovies();

                    mutableLiveData.setValue(movieList);

                }

            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }


}
