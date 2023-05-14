package milestone2.professors;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;


@Component
public class CourseHistoryDataLoader {

    @Autowired
    private CourseHistoryRepository coursehistoryRepository;
    
    String courseHistory = "data/CourseHistory.txt";

    @PostConstruct
    public void loadData() throws IOException {
        readDataFile();
    }

    private void readDataFile() throws IOException {
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(courseHistory))) {

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                CourseHistory course = new CourseHistory(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), Integer.parseInt(data[5]));
                coursehistoryRepository.save(course);
            }

        } catch (IOException e) {
            throw e;
        }
    }
}