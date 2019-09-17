package com.talha.itech.moviesmvvmapi.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.talha.itech.moviesmvvmapi.R;
import com.talha.itech.moviesmvvmapi.databinding.ActivityMovieDetailBinding;
import com.talha.itech.moviesmvvmapi.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    ActivityMovieDetailBinding activityMovieDetailBinding;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        activityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        Toolbar toolbar = activityMovieDetailBinding.toolbar;
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra("movie")) {

            movie = intent.getParcelableExtra("movie");
            setTitle(movie.getOriginalTitle());
            activityMovieDetailBinding.setMovie(movie);

        }

    }
}
