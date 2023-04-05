package milestone1.movie;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.stream.Collectors;



@RestController
@RequestMapping
public class RatingController {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/ratings/{ratingsAVG}")
    public List<Movie> getMovieByRatingsAVG(@PathVariable Double ratingsAVG) {
        List<Movie> MByRAVG = new ArrayList<>();
        if (ratingsAVG < 1 || ratingsAVG > 5) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid ratingsAVG value");
        }
        else {
            List<Rating> ratings = ratingRepository.findAll();
            Map<String, Double> movieRatingSumMap = new HashMap<>();
            Map<String, Integer> movieRatingCountMap = new HashMap<>();
            for(Rating rating : ratings) {
                String movieId = rating.getMovieId();
                double score = rating.getRating();
                if(!movieRatingSumMap.containsKey(movieId)) {
                    movieRatingSumMap.put(movieId, score);
                    movieRatingCountMap.put(movieId, 1);
                } else {
                    movieRatingSumMap.put(movieId, movieRatingSumMap.get(movieId) + score);
                    movieRatingCountMap.put(movieId, movieRatingCountMap.get(movieId) + 1);
                }
            }
            for(Map.Entry<String, Double> entry : movieRatingSumMap.entrySet()) {
                String movieId = entry.getKey();
                double movieRatingAVG = entry.getValue() / movieRatingCountMap.get(movieId);
                if(movieRatingAVG >= ratingsAVG) {
                    Movie movie = movieRepository.findById(movieId).orElse(null);
                    if(movie != null) {
                        MByRAVG.add(movie);
                    }
                }
            }
        }/* 
        List<EntityModel<Movie>> RESTMVs = MByRAVG.stream()
            .map(movie -> EntityModel.of(movie,
                linkTo(methodOn(RatingController.class).findById(movie.getId())).withSelfRel(),
                linkTo(methodOn(RatingController.class).findgetMovieByRatingsAVG()).withRel("ratings")))
            .collect(Collectors.toList());
        return CollectionModel.of(RESTMVs, linkTo(methodOn(RatingController.class).getMovieByRatingsAVG()).withSelfRel());
        */
        return MByRAVG;
    }
        
}