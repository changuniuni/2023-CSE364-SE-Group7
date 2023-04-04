package milestone1.movie;

import java.util.List;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings/{ratingsAVG}")
public class RatingController {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getMovieByRatingsAVG(@PathVariable Double ratingsAVG) {
        List<Movie> MByRAVG = new ArrayList<>();
        if (ratingsAVG < 1 || ratingsAVG > 5) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid ratingsAVG value");
        }
        else {
            for(Movie movie:movieRepository.findAll()) {
                List<Rating> movieRatings = ratingRepository.findByMovieId(movie.getId());
                double ratingsSum = 0;
                double movieRatingscnt = 0;
                for(Rating rating:movieRatings) {
                    ratingsSum += rating.getRating();
                    movieRatingscnt += 1;
                }
                if(ratingsSum/movieRatingscnt >= ratingsAVG)
                    MByRAVG.add(movie);
            }
        }
        return MByRAVG;
    }

}