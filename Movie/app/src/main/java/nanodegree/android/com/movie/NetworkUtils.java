package nanodegree.android.com.movie;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkUtils {

    private final static String MOVIE_DB_API = "https://api.themoviedb.org/3/movie";
    private final static String API_KEY = "[YOUR_API_KEY]"; // TODO replace by your api key
    private final static String IMAGE_URL = "http://image.tmdb.org/t/p/";
    private final static String API_KEY_PARAM = "api_key";
    private final static String IMG_SIZE = "w500";
    public final static String POPULAR = "popular";
    public final static String TOP_RATED = "top_rated";
    public final static String NOW_PLAYING = "now_playing";
    private static final String LOG_NETWORK = "Network : ";

    /**
     * Build the url to fetch the movies
     * @param param the sorting criteria
     * @return the requested URL
     */
    public static URL buildUrl(String param) {
        Uri builtUri = Uri.parse(MOVIE_DB_API).buildUpon()
                .appendPath(param)
                .appendQueryParameter(API_KEY_PARAM,API_KEY)
                .build();

        URL url  = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_NETWORK, "An error has occurred when creating the url");
        }

        return url;
    }

    /**
     * Build the URI to fetch the poster
     * @param posterUrl the poster number
     * @return the requested URI
     */
    public static Uri buildImageUri(String posterUrl) {
        Uri builtUri = Uri.parse(IMAGE_URL).buildUpon()
                .appendPath(IMG_SIZE)
                .appendEncodedPath(posterUrl)
                .build();

        return builtUri;
    }

    // Udacity course:  Lesson 2 : Network
    public static String getResponsefromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    // https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
    /**
     * Check if the connection is working
     * @param context the activity context
     * @return true if the connection is working, false otherwise
     */
    public static boolean isConnect(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();


    }


}
