package com.example.offlinemovies.ui;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.offlinemovies.R;
import com.example.offlinemovies.data.local.entity.MovieEntity;
import com.example.offlinemovies.data.remote.ApiConstants;

import java.util.List;


public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private List<MovieEntity> movieList;
    private Context ctx;

    public MovieRecyclerViewAdapter(Context context, List<MovieEntity> items) {
        movieList = items;
        ctx = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie, parent, false); //fragment_movie design of one movie
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.movie = movieList.get(position);
        //setear imagen del elemento que se esta recorriendo
        Glide.with(ctx)
                .load(ApiConstants.IMAGE_API_PREFIX + holder.movie.getPosterPath())
                .into(holder.imageViewCover);


    }

    //actualizar a vista con nueva data
    public void setData(List<MovieEntity> movies){
        this.movieList = movies;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        if(movieList != null){
            return movieList.size();
        }else{
            System.out.println("CANTIDAD DE ELEMENTOS EN LA LISTA 0");
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageViewCover;
        public MovieEntity movie;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageViewCover = view.findViewById(R.id.image_view_cover);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
