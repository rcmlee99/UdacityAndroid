package com.accenture.udacity1;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.accenture.udacity0.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieFetcher
        extends AsyncTask<String, Void, Movie[]> {

    private final static String LOG_TAG = MovieFetcher.class.getSimpleName();

    // API related constants
    final static String BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
    final static String PARAM_APIKEY = "api_key";
    final static String PARAM_SORTBY = "sort_by";
    final static String PARAM_PAGE = "page";

    // To fetch data for specific movie, append ID to this URL (with api key as usual)
    final static String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";

    // TODO: hide the API Key in github
    final static String apiKey = "";

    // Maintain handle to adapter (to notify it when new data is fetched)
    private MovieGridAdapter myAdapter = null;

    // Maintain handle to application/activity context in which used
    private Context context = null;

    // Constructor
    public MovieFetcher(Context _context, MovieGridAdapter _myAdapter) {
        this.context = _context;
        this.myAdapter = _myAdapter;
    }

    // Helper to craft URI for making server calls
    private String buildUri(String sortOrder, int page) {

        if (sortOrder == null || sortOrder.isEmpty())
            sortOrder = this.context.getResources().getString(R.string.pref_sort_default);

        String pageNum = "1";
        if (page > 1) {
            pageNum = Integer.toString(page);
        }
        ;

        Uri builtUri = Uri.parse(MovieFetcher.BASE_URL)
                .buildUpon()
                .appendQueryParameter(MovieFetcher.PARAM_APIKEY, MovieFetcher.apiKey)
                .appendQueryParameter(MovieFetcher.PARAM_SORTBY, sortOrder)
                .appendQueryParameter(MovieFetcher.PARAM_PAGE, pageNum)
                .build();
        return builtUri.toString();
    }

    // Parses JSON returned from API, constructs usable Movie objects
    public static Movie[] getMoviesFromJSON (String jsonData)
            throws JSONException {
        if (jsonData==null)
            return null;

        Log.i(LOG_TAG, "... parsing server json");
        JSONObject json = new JSONObject(jsonData);
        JSONArray movies = json.getJSONArray("results");
        int totalPages = json.getInt("total_pages");
        int totalResults = json.getInt("total_results");
        int pageNum = json.getInt("page");
        Log.i(LOG_TAG, "... totalResults="+totalResults+", totalPages="+totalPages);

        Movie obj[] = new Movie[movies.length()];
        JSONObject m=null;
        for (int i=0; i<movies.length(); i++){
            m = movies.getJSONObject(i);
            try {
                obj[i] = Movie.parseJSON(m);
            } catch(Exception e){
                Log.e(LOG_TAG, "Invalid Json for Movie at position "+i);
                obj[i] = null;
            }
        }
        return obj;
    }

    /**
     * The critical method of this fetcher that talks to the server API
     * (Reusing HTTP snippet from UD853 for convenience)
     * @param params
     * @return
     */
    @Override
    protected Movie[] doInBackground (String...params){

        // params must have sort order
        if (params.length == 0)
            return null;
        Log.i(LOG_TAG, "... execute with sort.order=" + params[0]);

        // fetch the data
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJson = null;

        try {
            URL url = new URL(this.buildUri(params[0], 1));
            //Log.i(LOG_TAG, "... built URI=" + url);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //Log.i(LOG_TAG, "... connected to server");

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
                return null;
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0)
                return null;
            moviesJson = buffer.toString();
            //Log.i(LOG_TAG, "... movies json \n" + moviesJson);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return this.getMoviesFromJSON(moviesJson);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * On completing the background task, post updated results to the associated Adapter
     * @param results
     */
    @Override
    protected void onPostExecute (Movie results[]){
        if (results != null) {
            myAdapter.updateResults(results);
        }
    }

}