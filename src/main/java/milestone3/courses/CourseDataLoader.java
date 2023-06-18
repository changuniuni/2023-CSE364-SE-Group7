package milestone3.courses;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;


@Component
public class CourseDataLoader {

    @Autowired
    private CourseRepository courseRepository;

    
    String courseFile = "data/CourseDetail.txt";

    @PostConstruct
    public void loadData() throws IOException {
        readCourseFile(courseFile);
    }

    private void readCourseFile(String filePath) throws IOException {
        String line = "";
        CourseCountMap courseCntMap = CourseCountMap.getInstance();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                Course course = new Course(data[0], data[1], data[2], data[3], data[4].split("\\|"), data[5], Integer.parseInt(data[6]), data[7], data[8]);
                courseCntMap.put(data[0], Integer.parseInt(data[9]));
                courseRepository.save(course);
            }

        } catch (IOException e) {
            throw e;
        }
    }

}