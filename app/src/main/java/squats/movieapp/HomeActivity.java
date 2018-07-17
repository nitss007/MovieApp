package squats.movieapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import squats.movieapp.WebService.RetrofitCallAPI;
import squats.movieapp.WebService.WebServiceAPI;
import squats.movieapp.adapter.MovieHorizontalAdapter;
import squats.movieapp.adapter.MovieVerticalAdapter;
import squats.movieapp.model.genre.GenreList;
import squats.movieapp.model.genre.Genres;
import squats.movieapp.model.movie.MovieList;
import squats.movieapp.model.movie.Results;
import squats.movieapp.ui.MovieDetailsActivity;

public class HomeActivity extends AppCompatActivity implements MovieHorizontalAdapter.MovieClickListener{

    @InjectView(R.id.rc_View)
    RecyclerView rcView;
    private MovieVerticalAdapter movieVerticalAdapter;
    private List<List<Results>> resultsList;
    private Dialog dialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {
            ButterKnife.inject(this);
            init();
            getMovieListResponse(WebServiceAPI.apiKey);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void getMovieListResponse(String apiKey) {
        dialog.setContentView (R.layout.custom_progress_dialog_trans);
        dialog.getWindow ().setBackgroundDrawableResource (android.R.color.transparent);
        dialog.show ();
        Call<GenreList> genreListCall = RetrofitCallAPI.getInstance(WebServiceAPI.SERVERBASE_URL).getMovieResponse(apiKey);
        genreListCall.enqueue(new Callback<GenreList>() {
            @Override
            public void onResponse(Call<GenreList> call, Response<GenreList> response) {

                List<Genres> genres = response.body().getGenres();
                movieVerticalAdapter = new MovieVerticalAdapter(HomeActivity.this, genres);
                rcView.setAdapter(movieVerticalAdapter);
                movieVerticalAdapter.notifyDataSetChanged();
                for (int i = 0; i < genres.size(); i++) {
                    fetchMoviesData(genres.get(i).getId(), i, genres.size());
                }

            }

            @Override
            public void onFailure(Call<GenreList> call, Throwable t) {

            }
        });


    }

    private void fetchMoviesData(Integer id, final int position, final int size) {

        Call<MovieList> movieListCall = RetrofitCallAPI.getInstance(WebServiceAPI.SERVERBASE_URL).getMovieList(id, WebServiceAPI.apiKey);
        movieListCall.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                Log.i("TAG", "onResponse: " + response.body().getResults().get(1).getOriginalTitle());
                List<Results> list = response.body().getResults();
                resultsList.add(list);
                if (position == size - 1) {
                    movieVerticalAdapter.setList(resultsList);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.i("TAG", "onFailure: ");
            }
        });
        //movieVerticalAdapter.notifyDataSetChanged();

    }

    private void init() {
        context = HomeActivity.this;
        dialog = new Dialog(this);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        resultsList = new ArrayList<>();
    }
    @Override
    public void onMovieClickListener(int movieId) {
        Intent intent = new Intent(this,MovieDetailsActivity.class);
        intent.putExtra("movieId",movieId);
        startActivity(intent);
    }
}
