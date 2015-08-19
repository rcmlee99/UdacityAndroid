package com.accenture.udacity1;

/**
 * Created by roger.chee.meng.lee on 19/08/2015.
 */

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A convenience class for containing data returned from the MovieFetcher API
 *  MovieFetcher JSON format:
 *    {
 *      adult        : false,
 *      backdrop_path: "/o4I5sHdjzs29hBWzHtS2MKD3JsM.jpg",
 *      genre_ids    : [ 878, 28, 53, 12 ],
 *      id           : 87101,
 *      original_language: "en",
 *      original_title : "Terminator Genisys",
 *      overview       : "The year is 2029. John Connor, leader of the resistance continues the war against the machines. At the Los Angeles offensive, John's fears of the unknown future begin to emerge when TECOM spies reveal a new plot by SkyNet that will attack him from both fronts; past and future, and will ultimately change warfare forever.",
 *      release_date   : "2015-07-01",
 *      poster_path    : "/5JU9ytZJyR3zmClGmVm9q4Geqbd.jpg",
 *      popularity     : 64.077523,
 *      title          : "Terminator Genisys",
 *      video          : false,
 *      vote_average   : 6.4,
 *      vote_count     : 223
 *    }
 *
 *    Important Attributes:
 *      id
 *      title
 *
 */
public class Movie implements Parcelable {

    // Parcel Keys
    private static final String PK_ADULT = "adult";
    private static final String PK_BACKDROP_PATH = "backdrop_path";
    private static final String PK_GENRE_IDS = "genre_ids";
    private static final String PK_ID = "id";
    private static final String PK_ORIGINAL_LANGUAGE = "original_language";
    private static final String PK_ORIGINAL_TITLE = "original_title";
    private static final String PK_OVERVIEW = "overview";
    private static final String PK_RELEASE_DATE = "release_date";
    private static final String PK_POSTER_PATH = "poster_path";
    private static final String PK_POPULARITY = "popularity";
    private static final String PK_TITLE = "title";
    private static final String PK_VIDEO = "video";
    private static final String PK_VOTE_AVERAGE = "vote_average";
    private static final String PK_VOTE_COUNT = "vote_count";

    private final static String PREFIX = "http://image.tmdb.org/t/p/";

    public enum ImageSize{
        ORIGINAL("original"),
        W92("w92"),
        W154("w154"),
        W185("w185"),
        W342("w342"),
        W500("w500"),
        W780("w780");

        private String size;
        ImageSize(String size){ this.size = size; }

        public String toString() { return this.size; }
    };

    // Variables
    boolean adult = false;
    String backdropPath = null;
    int[] genreIDs = null;
    int id = 0;
    String originalLanguage = null;
    String originalTitle = null;
    String overview = null;
    String releaseDate = null;
    String posterPath = null;
    double popularity = 0.0;
    String title = null;
    boolean video = false;
    int voteCount = 0;
    double voteAverage = 0.0;


    // default constructor
    private Movie(){
        super();
    }
    public Movie(boolean _adult, String _bPath, int _genreIDs[], int _id,
                 String _origLang, String _origTitle, String _overview,
                 String _releaseDate, String _posterPath, double _popularity,
                 String _title, boolean _video, int _voteCount, double _voteAverage) {
        this.adult = _adult;
        this.backdropPath = _bPath;
        this.genreIDs = _genreIDs;
        this.id = _id;
        this.originalLanguage = _origLang;
        this.originalTitle = _origTitle;
        this.overview = _overview;
        this.releaseDate = _releaseDate;
        this.posterPath = _posterPath;
        this.popularity = _popularity;
        this.title = _title;
        this.video = _video;
        this.voteCount = _voteCount;
        this.voteAverage = _voteAverage;
    }

    public boolean getAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public int[] getGenreIDs() {
        return genreIDs;
    }

    public int getId() {
        return id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getImageUrl (ImageSize size){
        if (size==null)
            size=ImageSize.W185;
        return PREFIX+ size.toString()+ this.posterPath;
    }


    // Implement Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(PK_ADULT, this.adult);
        bundle.putString(PK_BACKDROP_PATH, this.backdropPath);
        bundle.putIntArray(PK_GENRE_IDS, this.genreIDs);
        bundle.putInt(PK_ID, this.id);
        bundle.putString(PK_ORIGINAL_LANGUAGE, this.originalLanguage);
        bundle.putString(PK_ORIGINAL_TITLE, this.originalTitle);
        bundle.putString(PK_OVERVIEW, this.overview);
        bundle.putString(PK_RELEASE_DATE,this.releaseDate);
        bundle.putString(PK_POSTER_PATH,this.posterPath);
        bundle.putDouble(PK_POPULARITY, this.popularity);
        bundle.putString(PK_TITLE, this.title);
        bundle.putBoolean(PK_VIDEO, this.video);
        bundle.putInt(PK_VOTE_COUNT, this.voteCount);
        bundle.putDouble(PK_VOTE_AVERAGE, this.voteAverage);
        dest.writeBundle(bundle);
    }

    /**
     * Handles creation of object from Parcelable object.
     */
    public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            Bundle bundle = source.readBundle();
            return new Movie(
                    bundle.getBoolean(PK_ADULT),
                    bundle.getString(PK_BACKDROP_PATH),
                    bundle.getIntArray(PK_GENRE_IDS),
                    bundle.getInt(PK_ID),
                    bundle.getString(PK_ORIGINAL_LANGUAGE),
                    bundle.getString(PK_ORIGINAL_TITLE),
                    bundle.getString(PK_OVERVIEW),
                    bundle.getString(PK_RELEASE_DATE),
                    bundle.getString(PK_POSTER_PATH),
                    bundle.getDouble(PK_POPULARITY),
                    bundle.getString(PK_TITLE),
                    bundle.getBoolean(PK_VIDEO),
                    bundle.getInt(PK_VOTE_COUNT),
                    bundle.getDouble(PK_VOTE_AVERAGE)
            );
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }

    };

    /**
     * Create Movie object from JSON data
     *
     {
     adult: false,
     backdrop_path: "/dkMD5qlogeRMiEixC4YNPUvax2T.jpg",
     genre_ids: [
     28,
     12,
     878,
     53
     ],
     id: 135397,
     original_language: "en",
     original_title: "Jurassic World",
     overview: "Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.",
     release_date: "2015-06-12",
     poster_path: "/uXZYawqUsChGSj54wcuBtEdUJbh.jpg",
     popularity: 73.703834,
     title: "Jurassic World",
     video: false,
     vote_average: 7,
     vote_count: 1034
     }
     */
    public static Movie parseJSON(JSONObject data) throws JSONException {
        Movie m = new Movie();
        m.adult = data.getBoolean(PK_ADULT) || false;
        m.backdropPath = data.getString(PK_BACKDROP_PATH);

        JSONArray genreIDs = data.getJSONArray(PK_GENRE_IDS);
        m.genreIDs = new int[genreIDs.length()];
        for (int i=0; i<genreIDs.length(); i++){
            m.genreIDs[i] = genreIDs.getInt(i);
        }

        m.id = data.getInt(PK_ID);
        m.originalLanguage = data.getString(PK_ORIGINAL_LANGUAGE);
        m.originalTitle = data.getString(PK_ORIGINAL_TITLE);
        m.overview = data.getString(PK_OVERVIEW);
        m.releaseDate = data.getString(PK_RELEASE_DATE);
        m.posterPath = data.getString(PK_POSTER_PATH);
        m.popularity = data.getDouble(PK_POPULARITY);
        m.title = data.getString(PK_TITLE);
        m.video = data.getBoolean(PK_VIDEO);
        m.voteAverage = data.getInt(PK_VOTE_AVERAGE);
        m.voteCount = data.getInt(PK_VOTE_COUNT);

        return m;
    }

}

