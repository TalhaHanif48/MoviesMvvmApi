package com.talha.itech.moviesmvvmapi.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.talha.itech.moviesmvvmapi.model.Movie;
import com.talha.itech.moviesmvvmapi.model.MovieDataSource;
import com.talha.itech.moviesmvvmapi.model.MovieDataSourceFactory;
import com.talha.itech.moviesmvvmapi.model.MovieRepository;
import com.talha.itech.moviesmvvmapi.service.MovieDataService;
import com.talha.itech.moviesmvvmapi.service.RetrofitInstance;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivityViewModel extends AndroidViewModel {

    MovieRepository movieRepository;

    LiveData<MovieDataSource> movieDataSourceLiveData;

    Executor executor;

    LiveData<PagedList<Movie>> moviesPageList;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);

        MovieDataService movieDataService = RetrofitInstance.getInstance();
        MovieDataSourceFactory factory = new MovieDataSourceFactory(application, movieDataService);

        //movieDataSourceLiveData = factory.getMutableLiveData();

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(4)
                .build();

        //thread pool for five threads..
        executor = Executors.newFixedThreadPool(5);

        moviesPageList = (new LivePagedListBuilder<Long, Movie>(factory, config))
                .setFetchExecutor(executor).build();

    }


    public LiveData<PagedList<Movie>> getMoviesPageList() {
        return moviesPageList;
    }

    public LiveData<List<Movie>> getMovies() {
        return movieRepository.getmovieList();
    }

}
