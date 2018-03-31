package nanodegree.android.com.movie;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener{

    @BindView(R.id.rv_main_movies) RecyclerView mRVMovies;
    @BindView(R.id.navigation) BottomNavigationView navigation;
    @BindView(R.id.pg_loading_movies) ProgressBar mProgressBar;
    @BindView(R.id.tv_error_loading) TextView mErrorLoading;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getMoviesData(NetworkUtils.NOW_PLAYING);
                    return true;
                case R.id.navigation_popular:
                    getMoviesData(NetworkUtils.POPULAR);
                    return true;
                case R.id.navigation_rated:
                    getMoviesData(NetworkUtils.TOP_RATED);
                    return true;
            }
            return false;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRVMovies.setLayoutManager(mLayoutManager);
        getMoviesData(NetworkUtils.NOW_PLAYING);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * Load movies from MovieTask
     * @param param the sorting criteria
     */
    private void getMoviesData(String param) {
        if(NetworkUtils.isConnect(MainActivity.this)) {
            new MovieTask().execute(param);
        } else {
            errorLoading();
        }
    }

    /**
     * Display an error if the internet connection is not working
     */
    private void errorLoading() {
        mRVMovies.setVisibility(View.GONE);
        mErrorLoading.setVisibility(View.VISIBLE);
        mErrorLoading.setText(getResources().getString(R.string.error_loading));
    }

    /**
     * Send the movie to {@link DetailsActivity}
     * @param movieClicked the movie selected
     */
    @Override
    public void onListItemClick(Movie movieClicked) {
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra(Movie.TAG, movieClicked);
        startActivity(intent);
    }

    /**
     * Fetch the data in the background
     */
    public class MovieTask extends AsyncTask<String,Void, List<Movie>> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            // http://www.dev2qa.com/android-progress-bar-example/
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String params = strings[0];
            URL movieUrl = NetworkUtils.buildUrl(params);
            List<Movie> moviesList = new ArrayList<>();
                try {
                    String jsonResponse = NetworkUtils.getResponsefromHttpUrl(movieUrl);
                    moviesList = JsonUtils.getMoviesFromJson(jsonResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            return moviesList;
        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            if (!movieList.isEmpty()) {
                // http://tips.androidgig.com/invisible-vs-gone-view-in-android/
                mRVMovies.setVisibility(View.VISIBLE);
                mErrorLoading.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                MovieAdapter mAdapter = new MovieAdapter(MainActivity.this, movieList, MainActivity.this);
                mRVMovies.setAdapter(mAdapter);
            } else {
                errorLoading();
            }

        }
    }


}
