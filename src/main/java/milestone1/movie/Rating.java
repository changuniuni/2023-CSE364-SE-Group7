package milestone1.movie;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ratings")
public class Rating {
    @Id
    private String id;
    private String userid;
    private String movieid;
    private double rating;
    private long timestamp;
    
    public Rating(String userid, String movieid, double rating, long timestamp) {
        this.userid = userid;
        this.movieid = movieid;
        this.rating = rating;
        this.timestamp = timestamp;
    }
    
    // getters and setters
    public String getUserId() {
        return userid;
    }

    public String getMovieId() {
        return movieid;
    }

    public double getRating() {
        return rating;
    }

    public long getTimestamp() {
        return timestamp;
    }

}