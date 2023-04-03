package milestone1.movie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class DataLoader {
    private String filePath;

    public DataLoader(String filePath) {
        this.filePath = filePath;
    }

    public Map<Integer, User> loadUsers() {
        Map<Integer, User> users = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath + "/users.dat"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("::");
                int userId = Integer.parseInt(values[0]);
                String gender = values[1];
                int age = Integer.parseInt(values[2]);
                int occupation = Integer.parseInt(values[3]);
                String zipCode = values[4];
                User user = new User(userId, gender, age, occupation, zipCode);
                users.put(userId, user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<String[]> loadMovies() {
         List<String[]> movies = new ArrayList<String[]>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath + "/movies.dat")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("::");
                int movieId = Integer.parseInt(values[0]);
                String title = values[1];
                String[] genres = values[2].split("\\|");
                Movie movie = new Movie(movieId, title, genres);
                movies.put(movieId, movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<Rating> loadRatings() {
        List<Rating> ratings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath + "/ratings.dat"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("::");
                int userId = Integer.parseInt(values[0]);
                int movieId = Integer.parseInt(values[1]);
                double ratingValue = Double.parseDouble(values[2]);
                long timestamp = Long.parseLong(values[3]);
                Rating rating = new Rating(userId, movieId, ratingValue, timestamp);
                ratings.add(rating);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ratings;
    }
}