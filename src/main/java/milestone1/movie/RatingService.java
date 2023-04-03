package milestone1.movie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public Rating getRatingById(String id) {
        return ratingRepository.findById(id).orElse(null);
    }

    public Rating addRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public void deleteRatingById(String id) {
        ratingRepository.deleteById(id);
    }
}
