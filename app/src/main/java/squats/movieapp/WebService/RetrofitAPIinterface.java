package squats.movieapp.WebService;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import squats.movieapp.model.genre.GenreList;
import squats.movieapp.model.movie.MovieList;
import squats.movieapp.model.moviedetails.Moviedetails;

public interface RetrofitAPIinterface {

    @GET(WebServiceAPI.GETMOVIERESPONSE_URL)
    Call<GenreList> getMovieResponse(@Query("api_key") String apiKey);


    @GET("genre/{genre_id}/movies")
    Call<MovieList>getMovieList(@Path("genre_id")int id, @Query("api_key") String API_KEY);

    @GET("movie/{movie_id}")
    Call<Moviedetails>getMovieDetails(@Path("movie_id")int id, @Query("api_key") String API_KEY);


}
