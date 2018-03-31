package nanodegree.android.com.movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {
    private static final String RESULTS = "results";
    private static final String TITLE = "title";
    private static final String POSTER_PATH = "poster_path";
    private static final String VOTE = "vote_average";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";

    /**
     * Read the json data and create the movie object
     * @param json the json data from NetworkUtils.getResponsefromHttpUrl()
     * @return list of movies
     * @throws JSONException to indicate a problem with the JSON API
     */
    public static List<Movie> getMoviesFromJson(String json) throws JSONException {
        List<Movie> moviesList = new ArrayList<>();
        JSONObject moviesJson = new JSONObject(json);
        JSONArray moviesArray = moviesJson.getJSONArray(RESULTS);

        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieJson = moviesArray.getJSONObject(i);
            String mTitle = movieJson.optString(TITLE);
            String mPoster = movieJson.optString(POSTER_PATH);
            String mDetails = movieJson.optString(OVERVIEW);
            double mVote = movieJson.optDouble(VOTE);
            String mReleaseDate = movieJson.optString(RELEASE_DATE);

            Movie movie = new Movie(mTitle, mPoster, mDetails, mReleaseDate, mVote);

            moviesList.add(movie);
        }

        return moviesList;
    }
}
