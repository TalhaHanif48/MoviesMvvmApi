package com.talha.itech.moviesmvvmapi.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.talha.itech.moviesmvvmapi.R;
import com.talha.itech.moviesmvvmapi.adapter.MovieAdapter;
import com.talha.itech.moviesmvvmapi.databinding.ActivityMainBinding;
import com.talha.itech.moviesmvvmapi.model.Movie;
import com.talha.itech.moviesmvvmapi.model.MovieDBResponse;
import com.talha.itech.moviesmvvmapi.service.MovieDataService;
import com.talha.itech.moviesmvvmapi.service.RetrofitInstance;
import com.talha.itech.moviesmvvmapi.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    MainActivityViewModel mainActivityViewModel;
    ActivityMainBinding activityMainBinding;

    PagedList<Movie> movieList;
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        recyclerView = activityMainBinding.rv;
        movieAdapter = new MovieAdapter(this);

        setTitle("Movies");


     /*   mainActivityViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {

                Log.d(TAG, "onChanged: " + movies);
                movieList = movies;
                //Toast.makeText(MainActivity.this, String.valueOf(movieList.size()), Toast.LENGTH_SHORT).show();
                setRecycler();
            }
        });*/

        movieAdapter.setListner(new MovieAdapter.OnClickMovieListner() {
            @Override
            public void onMovieClicked(Movie movie) {
                //Toast.makeText(MainActivity.this, movie.getOriginalTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);

            }
        });

        getPoupulafMovies();


        //getMovies();
    }


    public void getPoupulafMovies() {

        mainActivityViewModel.getMoviesPageList().observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {

                movieList = movies;
                showOnRV();
            }
        });

    }

    private void showOnRV() {

        movieAdapter.submitList(movieList);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);

    }


    private void getMovies() {

        MovieDataService movieDataService = RetrofitInstance.getInstance();
        Call<MovieDBResponse> call = movieDataService.getPopularMovies(getResources().getString(R.string.apiKey));

        call.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {

                Log.d(TAG, "onResponse: " + response.body());

            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {

            }
        });

    }


    private void setRecycler() {
        // movieAdapter.setMovieList(movieList);
    }
}
