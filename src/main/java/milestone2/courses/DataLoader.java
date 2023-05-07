package milestone2.courses;

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
    private CourseRepository courseRepository;

    @PostConstruct
    public void loadData() {
        String courseFile = "data/Details.txt";

        readDataFile(courseFile);
    }

    private void readDataFile(String filePath) {
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                print();
                Course course = new Course(data[0], data[1], data[2], data[3], data[4].split("\\|"), data[5], data[6], data[7]);
                courseRepository.save(course);
            }

        } catch (IOException e) {
            System.err.println("Error occurred while reading " + filePath + " file.");
            e.printStackTrace();
        }
    }
}