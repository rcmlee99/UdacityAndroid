package com.accenture.udacity1;

import com.accenture.udacity0.R;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;

import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();

    private Movie movie=null;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_detail, container, false);

        // Inspect trigger Intent for Detail values.
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Movie.class.getSimpleName())) {
            this.movie = intent.getExtras().getParcelable(Movie.class.getSimpleName());

            Log.i(LOG_TAG, "Movie Data is: "+this.movie.voteCount);

            // R.id.movieImage
            ((TextView) rootView.findViewById(R.id.movieTitleText)).setText(this.movie.title);
            ((TextView) rootView.findViewById(R.id.movieRelease)).setText(this.movie.releaseDate);

            TextView textview= ((TextView) rootView.findViewById(R.id.movieOverview));
            textview.setText(this.movie.overview);
            textview.setVerticalScrollBarEnabled(true);
            textview.setLines(10);
            //textview.setMovementMethod(ScrollingMovementMethod.getInstance());

            ((TextView) rootView.findViewById(R.id.voteAvg)).setText(Double.toString(this.movie.voteAverage)+"/10");

            ImageView iview = (ImageView) rootView.findViewById(R.id.movieImage);
            Picasso.with(getActivity())
                    .load(this.movie.getImageUrl(null))
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .fit()
                    .noFade()
                    .into(iview);
        }

        return rootView;
    }
}
