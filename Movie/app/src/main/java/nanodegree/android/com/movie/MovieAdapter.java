package nanodegree.android.com.movie;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

// Udacity Course : Lesson 3 : RecyclerView
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private final Context mContext;
    private final List<Movie> mListMovies;
    private final ListItemClickListener mOnClickListener;

    public MovieAdapter(Context context, List<Movie> listMovies, ListItemClickListener onClickListener) {
        mContext = context;
        mListMovies = listMovies;
        mOnClickListener = onClickListener;
    }
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.poster_movie;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Uri poster = NetworkUtils.buildImageUri(mListMovies.get(position).getPoster());
        Picasso.with(mContext).load(poster).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mListMovies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.iv_image_movie)
        ImageView mImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Movie movieClicked = mListMovies.get(clickedPosition);
            mOnClickListener.onListItemClick(movieClicked);
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(Movie movieClicked);
    }

}
