package com.angelgomezsalazar.upcomingmoviesdemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.angelgomezsalazar.upcomingmoviesdemo.R;
import com.angelgomezsalazar.upcomingmoviesdemo.adapters.MovieRecyclerAdapter;
import com.angelgomezsalazar.upcomingmoviesdemo.retrofit.interfaces.MovieApi;
import com.angelgomezsalazar.upcomingmoviesdemo.retrofit.models.Genre;
import com.angelgomezsalazar.upcomingmoviesdemo.retrofit.models.GenreResponse;
import com.angelgomezsalazar.upcomingmoviesdemo.retrofit.models.Movie;
import com.angelgomezsalazar.upcomingmoviesdemo.retrofit.models.MovieResponse;
import com.angelgomezsalazar.upcomingmoviesdemo.utils.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movieList;

    private RecyclerView movieRecyclerView;
    private RecyclerView.Adapter movieAdapter;

    private Retrofit retrofit;
    private MovieApi movieApi;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        movieApi = retrofit.create(MovieApi.class);

        movieList = new ArrayList<>();

        movieRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        movieRecyclerView.setLayoutManager(linearLayoutManager);
        movieRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(
                linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.d(TAG, "On Load More Called");
                if (movieList.size() < 50) {
                    callUpcomingMovieApi(currentPage);
                }
            }
        });
        callGenreApi();
    }

    private void callUpcomingMovieApi(int page) {
        // The api_key would usually go in gradle.properties but since this is a demo I'll leave
        // it there
        Call<MovieResponse> upcomingMovieCall =
                movieApi.getUpcomingMovies(page, "1f54bd990f1cdfb230adb312546d765d");
        upcomingMovieCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieList.addAll(response.body().results);
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    private void callGenreApi() {
        MovieApi movieApi = retrofit.create(MovieApi.class);
        // The api_key would usually go in gradle.properties but since this is a demo I'll leave
        // it there
        Call<GenreResponse> genreCall =
                movieApi.getGenreList("1f54bd990f1cdfb230adb312546d765d");
        genreCall.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                HashMap<Integer, String> genreHashMap = new HashMap<>();
                for (Genre genre: response.body().getGenres()) {
                    genreHashMap.put(genre.getId(), genre.getName());
                }
                movieAdapter = new MovieRecyclerAdapter(MainActivity.this, movieList, genreHashMap);
                movieRecyclerView.setAdapter(movieAdapter);

                callUpcomingMovieApi(1);
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {

            }
        });
    }
}
