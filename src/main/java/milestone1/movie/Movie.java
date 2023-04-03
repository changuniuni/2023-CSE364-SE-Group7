package milestone1.movie;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movies")
public class Movie {
    @Id
    private String id;
    private String movieid;
    private String title;
    private Double meanratings;
    private String[] genres;

    public Movie(String movieid, String title, Double meanratings, String[] genres) {
        this.movieid = movieid;
        this.title = title;
        this.meanratings = meanratings;
        this.genres = genres;
    }

    public String getMovieId() {
        return movieid;
    }

    public String getTitle() {
        return title;
    }

    public Double getMeanRatings() {
        return meanratings;
    }

    public String[] getGenres() {
        return genres;
    }

}