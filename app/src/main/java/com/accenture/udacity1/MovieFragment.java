package com.accenture.udacity1;

/**
 * Created by roger.chee.meng.lee on 19/08/2015.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.accenture.udacity0.R;


/**
 * Main Fragment containing the GridView of images for the Main activity.
 * Also declares the Adapter that binds to the View and syncs with remote data
 */
public class MovieFragment extends Fragment {

    // Logging
    private static final String LOG_TAG = MovieFragment.class.getSimpleName();

    // Variables
    private MovieGridAdapter movieAdapter=null;

    // Constructor
    public MovieFragment() {}

    /**
     * onCreate() called first -- creates Fragment instance
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Anything we want to handle from savedInstanceState?
        //   for now, we know data is updated in onStart so we don't
        setHasOptionsMenu(true);
    }

    /**
     * onCreateOptionsMenu -- since we specify setHasOptionsMenu(true)
     * we want to create and add those options to the action bar
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_movie, menu);
    }

    /**
     * onOptionsItemSelected -- handles events on menu options
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * onCreateView() called next -- sets up the visible View elements for Fragment
     *   - first, we create the GridView here
     *   - then, we create a MovieGridAdapter for the current context
     *   - last, we bind the View to the adapter
     *
     * We are implementing Master-Detail flows so clicking a gridView item should launc
     * the associated Detail Activity. By making the Movie (data object) class Parcelable
     * we can pass the entire Detail record to the Detail Activity via an Intent Extra
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        // TODO: should we init adapter in constructor?
        // (we need it for updateMovies, which is called from menu option handler too)
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movie);
        movieAdapter = new MovieGridAdapter(getActivity());
        gridView.setAdapter(movieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie movie = movieAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Movie.class.getSimpleName(), movie);
                startActivity(intent);
            }
        });

        return rootView;
    }


    /**
     * onStart() called next -- at this point the fragment is about to go visible
     *   let's take this time to update the data associated with the view
     */
    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    /**
     * Helper method -- runs the async fetch on the MovieAPI endpoint
     * and then updates the movieAdapter with the recent data results
     * (adapter also gets change notification, causing it to trigger view refresh)
     */
    private void updateMovies() {
        MovieFetcher fetchTask = new MovieFetcher(getActivity(), movieAdapter);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_sort_key),getString(R.string.pref_sort_default));
        fetchTask.execute(sortOrder);
    }






}

