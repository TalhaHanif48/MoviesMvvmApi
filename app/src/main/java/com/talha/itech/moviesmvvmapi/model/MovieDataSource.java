package com.talha.itech.moviesmvvmapi.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.talha.itech.moviesmvvmapi.R;
import com.talha.itech.moviesmvvmapi.service.MovieDataService;
import com.talha.itech.moviesmvvmapi.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Long, Movie> {

    MovieDataService movieDataService;
    Application application;


    public MovieDataSource(MovieDataService movieDataService, Application application) {
        this.movieDataService = movieDataService;
        this.application = application;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Movie> callback) {

        movieDataService = RetrofitInstance.getInstance();
        Call<MovieDBResponse> call = movieDataService.getPopularMoviesWithpaging(application.getString(R.string.apiKey), (long) 1);
        call.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {

                MovieDBResponse movieDBResponse = response.body();
                ArrayList<Movie> movies = new ArrayList<>();

                if (movieDBResponse != null && movieDBResponse.getMovies() != null) {
                    movies = (ArrayList<Movie>) movieDBResponse.getMovies();
                    callback.onResult(movies, null, (long) 2);
                }

            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Movie> callback) {

        movieDataService = RetrofitInstance.getInstance();
        Call<MovieDBResponse> call = movieDataService.getPopularMoviesWithpaging(application.getString(R.string.apiKey), params.key);
        call.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {

                MovieDBResponse movieDBResponse = response.body();
                ArrayList<Movie> movies = new ArrayList<>();

                if (movieDBResponse != null && movieDBResponse.getMovies() != null) {
                    movies = (ArrayList<Movie>) movieDBResponse.getMovies();
                    callback.onResult(movies, params.key + 1);
                }

            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {

            }
        });


    }
}
