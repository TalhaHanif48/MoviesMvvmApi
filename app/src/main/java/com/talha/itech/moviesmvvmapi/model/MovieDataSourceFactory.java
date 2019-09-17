package com.talha.itech.moviesmvvmapi.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.talha.itech.moviesmvvmapi.service.MovieDataService;

public class MovieDataSourceFactory extends DataSource.Factory {

    MovieDataSource movieDataSource;
    Application application;
    MovieDataService movieDataService;

    MutableLiveData<MovieDataSource> mutableLiveData;

    public MovieDataSourceFactory(Application application, MovieDataService movieDataService) {
        this.application = application;
        this.movieDataService = movieDataService;
        mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {

        movieDataSource = new MovieDataSource(movieDataService, application);

       // mutableLiveData.postValue(movieDataSource);
        return movieDataSource;
    }

    public MutableLiveData<MovieDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
