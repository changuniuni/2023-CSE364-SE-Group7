package milestone1.movie;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }
    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable String id) {
        return movieService.getMovieById(id);
    }

    @GetMapping("/search")
    public List<Movie> getMoviesByTitle(@RequestParam(value = "title") String title) {
        return movieService.getMoviesByTitle(title);
    }

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }
    @DeleteMapping("/{id}")
    public void deleteMovieById(@PathVariable String id) {
        movieService.deleteMovieById(id);
    }
}