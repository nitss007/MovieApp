package squats.movieapp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import squats.movieapp.R;
import squats.movieapp.model.genre.Genres;
import squats.movieapp.model.movie.Results;

public class MovieVerticalAdapter extends RecyclerView.Adapter<MovieVerticalAdapter.SimpleViewHolder> {

    private final Context mContext;
    private static List<Genres> mData;
    private static RecyclerView horizontalList;
    private List<List<Results>> resultsList;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        private MovieHorizontalAdapter horizontalAdapter;

        public SimpleViewHolder(View view) {
            super(view);
            Context context = itemView.getContext();
            title = (TextView) view.findViewById(R.id.course_item_name_tv);
            horizontalList = (RecyclerView) itemView.findViewById(R.id.horizontal_list);
            horizontalList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            horizontalAdapter = new MovieHorizontalAdapter(context);
            horizontalList.setAdapter(horizontalAdapter);
        }
    }

    public MovieVerticalAdapter(Context context, List<Genres> data) {
        mContext = context;
        if (data != null)
            mData = new ArrayList<>(data);
        else mData = new ArrayList<>();
        this.resultsList = new ArrayList<>();
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        return new SimpleViewHolder(view);
    }


    public void setList(List<List<Results>> results) {
        if (results != null) {
            resultsList = results;
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        Genres genres = mData.get(position);
        holder.title.setText(genres.getName());
        if (resultsList.size() > 0) {
            holder.horizontalAdapter.setData(resultsList.get(position)); // List of Strings
        }
        holder.horizontalAdapter.setRowIndex(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
