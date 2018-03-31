package nanodegree.android.com.movie;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.tv_title_detail) TextView mTvTitle;
    @BindView(R.id.tv_overview_detail) TextView mTvOverviewDetail;
    @BindView(R.id.iv_poster_detail) ImageView mIvPosterDetail;
    @BindView(R.id.tv_release_date_detail) TextView mTvDate_detail;
    @BindView(R.id.tv_vote_detail) TextView mTvVoteDetail;

    private final static String TAG = "Movie : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Movie mMovie = getIntent().getParcelableExtra(Movie.TAG);

        if (mMovie != null) {
            Uri uriPoster = NetworkUtils.buildImageUri(mMovie.getPoster());
            Picasso.with(DetailsActivity.this).load(uriPoster).into(mIvPosterDetail);
            setTitle(mMovie.getTitle());
            mTvTitle.setText(mMovie.getTitle());
            mTvDate_detail.setText(mMovie.getReleaseDate());
            mTvVoteDetail.setText(String.format(Locale.US,"%1$,.2f", mMovie.getVote()));
            mTvOverviewDetail.setText(mMovie.getDetails());
        } else {
            Log.e(TAG, "null");
        }
    }
}
