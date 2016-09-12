package com.angelgomezsalazar.upcomingmoviesdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.angelgomezsalazar.upcomingmoviesdemo.R;
import com.squareup.picasso.Picasso;

/**
 * Created by angelgomez on 8/18/16.
 */
public class DetailActivity extends AppCompatActivity {

    public static final String NAME_EXTRA = "name_extra";
    public static final String IMAGE_EXTRA = "image_extra";
    public static final String GENRE_EXTRA = "genre_extra";
    public static final String RELEASE_EXTRA = "release_extra";
    public static final String OVERVIEW_EXTRA = "overview_extra";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent detailIntent = getIntent();
        String name = detailIntent.getStringExtra(NAME_EXTRA);
        String image = detailIntent.getStringExtra(IMAGE_EXTRA);
        String genre = detailIntent.getStringExtra(GENRE_EXTRA);
        String release = detailIntent.getStringExtra(RELEASE_EXTRA);
        String overview = detailIntent.getStringExtra(OVERVIEW_EXTRA);

        TextView nameTextView = (TextView) findViewById(R.id.activity_detail_text_movie_name);
        TextView genreTextView = (TextView) findViewById(R.id.activity_detail_text_genre);
        TextView releaseTextView = (TextView) findViewById(R.id.activity_detail_text_release_date);
        TextView overviewTextView = (TextView) findViewById(R.id.activity_detail_text_overview);
        ImageView posterImageView =
                (ImageView) findViewById(R.id.activity_detail_image_poster);

        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/w500" + image)
                .placeholder(R.drawable.sample_movie_poster)
                .into(posterImageView);

        assert nameTextView != null;
        nameTextView.setText(name);
        assert genreTextView != null;
        genreTextView.setText(genre);
        assert releaseTextView != null;
        releaseTextView.setText(release);
        assert overviewTextView != null;
        overviewTextView.setText(overview);
    }
}
