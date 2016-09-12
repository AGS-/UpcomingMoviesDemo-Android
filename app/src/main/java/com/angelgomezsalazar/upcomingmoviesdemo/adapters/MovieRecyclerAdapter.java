package com.angelgomezsalazar.upcomingmoviesdemo.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angelgomezsalazar.upcomingmoviesdemo.R;
import com.angelgomezsalazar.upcomingmoviesdemo.activities.DetailActivity;
import com.angelgomezsalazar.upcomingmoviesdemo.retrofit.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by angelgomez on 8/18/16.
 */
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private List<Movie> movieList;
    private HashMap<Integer, String> genreHashMap;

    private final Activity context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView posterImageView;
        TextView nameTextView;
        TextView genreTextView;
        TextView dateTextView;

        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.item_main_card_view);
            posterImageView = (ImageView) v.findViewById(R.id.item_main_image_poster);
            nameTextView = (TextView) v.findViewById(R.id.item_main_text_movie_name);
            genreTextView = (TextView) v.findViewById(R.id.item_main_text_genre);
            dateTextView = (TextView) v.findViewById(R.id.item_main_text_date);
        }
    }

    public MovieRecyclerAdapter(Activity context, List<Movie> movieList, HashMap<Integer,
            String> genreHashMap) {
        this.movieList = movieList;
        this.genreHashMap = genreHashMap;
        this.context = context;
    }

    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Movie movie = movieList.get(position);
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .placeholder(R.drawable.sample_movie_poster)
                .into(holder.posterImageView);
        holder.nameTextView.setText(movie.getTitle());
        String genreString = "";
        for (Integer genreId: movie.getGenreIds()) {
            if (genreString.equals("")) {
                genreString += genreHashMap.get(genreId);
            } else {
                genreString += ", " + genreHashMap.get(genreId);
            }
        }
        final String finalGenreString = genreString;
        holder.genreTextView.setText(genreString);
        holder.dateTextView.setText(movie.getRelease_date());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra(DetailActivity.NAME_EXTRA, movie.getTitle());
                detailIntent.putExtra(DetailActivity.GENRE_EXTRA, finalGenreString);
                detailIntent.putExtra(DetailActivity.RELEASE_EXTRA, movie.getRelease_date());
                detailIntent.putExtra(DetailActivity.IMAGE_EXTRA, movie.getPosterPath());
                detailIntent.putExtra(DetailActivity.OVERVIEW_EXTRA, movie.getOverview());
                context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

}
