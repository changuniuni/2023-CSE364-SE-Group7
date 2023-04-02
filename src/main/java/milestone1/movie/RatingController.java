package milestone1.movie;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @GetMapping
    public List<Rating> getAllRatings() {
        return ratingService.getAllRatings();
    }

    @GetMapping("/{id}")
    public Rating getRatingById(@PathVariable String id) {
        return ratingService.getRatingById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Rating> getRatingsByUserId(@PathVariable int userId) {
        return ratingService.getRatingsByUserId(userId);
    }

    @GetMapping("/movie/{movieId}")
    public List<Rating> getRatingsByMovieId(@PathVariable int movieId) {
        return ratingService.getRatingsByMovieId(movieId);
    }

    @PostMapping
    public Rating addRating(@RequestBody Rating rating) {
        return ratingService.addRating(rating);
    }

    @DeleteMapping("/{id}")
    public void deleteRatingById(@PathVariable String id) {
        ratingService.deleteRatingById(id);
    }
}