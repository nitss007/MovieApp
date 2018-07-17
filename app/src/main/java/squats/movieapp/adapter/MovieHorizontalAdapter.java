package squats.movieapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import squats.movieapp.R;
import squats.movieapp.model.movie.Results;

public class MovieHorizontalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Results> mDataList;
    private int mRowIndex = -1;
    private Context context;
    private MovieClickListener movieClickListener;

    public interface MovieClickListener{
        void onMovieClickListener(int movieId);
    }

    public MovieHorizontalAdapter(Context context) {
        this.context = context;
        movieClickListener = (MovieClickListener)context;
    }

    public void setData(List<Results> data) {
        if (mDataList != data) {
            mDataList = data;
            notifyDataSetChanged();
        }
    }

    public void setRowIndex(int index) {
        mRowIndex = index;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView text;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.poster_image);
            text = (TextView) itemView.findViewById(R.id.horizontal_item_text);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.horizontal_item, parent, false);
        ItemViewHolder holder = new ItemViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder rawHolder, int position) {
        ItemViewHolder holder = (ItemViewHolder) rawHolder;
        final Results results = mDataList.get(position);
        if (results != null) {
            holder.text.setText(results.getOriginalTitle());

            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w185/" + results.getPosterPath())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movieClickListener.onMovieClickListener(results.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList != null && mDataList.size() > 0 ? mDataList.size() : 0;
    }

}