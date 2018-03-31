package nanodegree.android.com.movie;

import android.os.Parcel;
import android.os.Parcelable;


// Google Webcast
public class Movie implements Parcelable {
    public static String TAG = Movie.class.getName();
    private String mTitle;
    private String mPoster;
    private String mDetails;
    private String mReleaseDate;
    private double mVote;

    public Movie(String title, String poster, String details, String releaseDate, double vote) {
        mTitle = title;
        mPoster = poster;
        mDetails = details;
        mReleaseDate = releaseDate;
        mVote = vote;
    }
    public String getTitle() {
        return mTitle;
    }

    // public void setTitle(String title) {this.mTitle = title;}

    public String getPoster() {
        return mPoster;
    }

   // public void setPoster(String poster) { this.mPoster = poster;}

    public String getDetails() {
        return mDetails;
    }

    // public void setDetails(String details) { this.mDetails = details; }

    public double getVote() {
        return mVote;
    }

    // public void setVote(double vote) { this.mVote = vote; }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    // public void setReleaseDate(String releaseDate) { this.mReleaseDate = releaseDate;}

    private Movie(Parcel in) {
        this.mTitle = in.readString();
        this.mPoster = in.readString();
        this.mDetails = in.readString();
        this.mReleaseDate = in.readString();
        this.mVote = in.readDouble();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mTitle);
        parcel.writeString(this.mPoster);
        parcel.writeString(this.mDetails);
        parcel.writeString(this.mReleaseDate);
        parcel.writeDouble(this.mVote);
    }
}
