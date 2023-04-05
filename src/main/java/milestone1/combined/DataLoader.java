package milestone1.combined;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;

@Component
public class DataLoader {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void loadData() {
        String movieFile = "data/movies.dat";
        String ratingFile = "data/ratings.dat";
        String userFile = "data/users.dat";

        readDataFile(movieFile);
        readDataFile(ratingFile);
        readDataFile(userFile);
    }

    private void readDataFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("::");
                if (filePath.contains("movies")) {
                    Movie movie = new Movie(data[0], data[1], data[2].split("\\|"));
                    movieRepository.save(movie);
                } else if (filePath.contains("ratings")) {
                    Rating rating = new Rating(data[0], data[1], Double.parseDouble(data[2]), Long.parseLong(data[3]));
                    ratingRepository.save(rating);
                } else if (filePath.contains("users")) {
                    User user = new User(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),data[4]);
                    userRepository.save(user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading " + filePath + " file.");
            e.printStackTrace();
        }
    }
}