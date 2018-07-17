package squats.movieapp.model.genre;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class GenreList {
    @SerializedName("genres")
    @Expose
    private List<Genres> genres;

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public List<Genres> getGenres() {
        return genres;
    }
}