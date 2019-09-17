package com.talha.itech.moviesmvvmapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.talha.itech.moviesmvvmapi.R;
import com.talha.itech.moviesmvvmapi.databinding.MovieListItemBinding;
import com.talha.itech.moviesmvvmapi.model.Movie;

import java.util.List;

public class MovieAdapter extends PagedListAdapter<Movie, MovieAdapter.MovieViewHolder> {
    //List<Movie> movieList;
    OnClickMovieListner listner;

    Context context;

    public MovieAdapter(Context context) {
        super(Movie.CALLBACK);
        this.context = context;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        MovieListItemBinding movieListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.movie_list_item,
                parent,
                false);

        return new MovieViewHolder(movieListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        final Movie currentMovie = getItem(position);

        holder.itemBinding.setMovie(currentMovie);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onMovieClicked(currentMovie);
            }
        });

    }



   /* public void setMovieList(List<Movie> movieList) {
        notifyDataSetChanged();
    }
*/
    public void setListner(OnClickMovieListner listner) {
        this.listner = listner;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        MovieListItemBinding itemBinding;

        public MovieViewHolder(@NonNull MovieListItemBinding movieListItemBinding) {
            super(movieListItemBinding.getRoot());
            this.itemBinding = movieListItemBinding;
        }
    }


    public interface OnClickMovieListner {
        void onMovieClicked(Movie movie);
    }


}
