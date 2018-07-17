package squats.movieapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import squats.movieapp.HomeActivity;
import squats.movieapp.R;
import squats.movieapp.WebService.RetrofitCallAPI;
import squats.movieapp.WebService.WebServiceAPI;
import squats.movieapp.model.moviedetails.Moviedetails;

public class MovieDetailsActivity extends AppCompatActivity {

    @InjectView(R.id.layBack)
    LinearLayout layBack;
    @InjectView(R.id.layoutHeader)
    LinearLayout layoutHeader;
    @InjectView(R.id.cover_poster)
    ImageView coverPoster;
    @InjectView(R.id.poster_path)
    ImageView posterPath;
    @InjectView(R.id.tv_Description)
    TextView tvDescription;
    @InjectView(R.id.tv_Language)
    TextView tvLanguage;
    @InjectView(R.id.tv_Popularity)
    TextView tvPopularity;
    @InjectView(R.id.tv_Release_date)
    TextView tvReleaseDate;
    @InjectView(R.id.tv_Revenue)
    TextView tvRevenue;
    private Context context;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.inject(this);
        init();
        int movieId = getIntent().getIntExtra("movieId", 0);
        getMovieDetails(String.valueOf(movieId));
    }

    private void getMovieDetails(String movieId) {
        dialog.setContentView (R.layout.custom_progress_dialog_trans);
        dialog.getWindow ().setBackgroundDrawableResource (android.R.color.transparent);
        dialog.show ();
        final Call<Moviedetails> movieDetails = RetrofitCallAPI.getInstance(WebServiceAPI.SERVERBASE_URL).getMovieDetails(Integer.parseInt(movieId), WebServiceAPI.apiKey);
        movieDetails.enqueue(new Callback<Moviedetails>() {
            @Override
            public void onResponse(Call<Moviedetails> call, Response<Moviedetails> response) {

             movieDetailsResponse(response);

            }

            @Override
            public void onFailure(Call<Moviedetails> call, Throwable t) {
                Log.i("TAG", "onFailure: ");
            }
        });
    }

    private void movieDetailsResponse(Response<Moviedetails> response) {

        String strTitle = response.body().getOriginalTitle();
        String dPopularity = String.valueOf(response.body().getPopularity());
        String strReleaseDate = response.body().getReleaseDate();
        String strLanguage = response.body().getOriginalLanguage();
        String strRevenue = String.valueOf(response.body().getRevenue());
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185/" + response.body().getPosterPath())
                .placeholder(R.drawable.ic_launcher_background)
                .into(posterPath);
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185/" + response.body().getBackdropPath())
                .placeholder(R.drawable.ic_launcher_background)
                .into(coverPoster);

        tvReleaseDate.setText(strReleaseDate);
        tvDescription.setText(strTitle);
        tvRevenue.setText(strRevenue);
        tvPopularity.setText(dPopularity);
        if (strLanguage.equalsIgnoreCase("en")) {
            tvLanguage.setText("English");
        } else if(strLanguage.equalsIgnoreCase("hi")) {
            tvLanguage.setText("Hindi");
        }else
        {
            tvLanguage.setText(strLanguage);
        }
        dialog.dismiss();
    }

    private void init() {
        context=MovieDetailsActivity.this;
        dialog = new Dialog(this);
    }

    @OnClick(R.id.layBack)
    public void onViewClicked() {
        finish();
        Intent intent = new Intent(MovieDetailsActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
