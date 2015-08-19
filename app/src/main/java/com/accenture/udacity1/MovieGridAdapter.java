package com.accenture.udacity1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.accenture.udacity0.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Adapter that binds Image data from the server, to the GridView in the MainFragment
 */
public class MovieGridAdapter extends BaseAdapter {

    private final String LOG_TAG = MovieGridAdapter.class.getSimpleName();

    private Context myContext;
    private ArrayList<Movie> movies = null;

    public MovieGridAdapter(Context context){
        this.myContext = context;
        this.movies = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Describes how to generate the View for the specified position of the data set.
     *  position = index with the bound dataset
     *  convertView = old view for reuse (if it exists)
     *  parent = parent view that this view attaches to (typically ViewGroup)
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view;

        // recycle view if it already exists, else create
        if (convertView!=null)
            view = (ImageView) convertView;
        else  {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (ImageView) inflater.inflate(R.layout.list_item_movie, parent, false);
        }

        // then use Picasso to load the content into that view
        // Picasso does this efficiently on background thread.
        Movie data = this.movies.get(position);
        Picasso.with(myContext)
                .load(data.getImageUrl(null))
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.error)
                .fit()
                .noFade()
                .into(view);
        return view;
    }

    // Method called by MovieFetcher associated with this adapter, when it fetches new results
    public void updateResults(Movie[] results) {
        this.movies.clear();
        for(Movie m : results) {
            this.movies.add(m);
        }
        notifyDataSetChanged();
    }

}
