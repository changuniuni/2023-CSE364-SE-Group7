package milestone2.professors;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;


@Component
public class ProfessorDataLoader {

    @Autowired
    static ProfessorRepository professorRepository;
    @Autowired
    private CourseHistoryRepository coursehistoryRepository;

    @PostConstruct
    public void loadData() {
        String professorFile = "data/CSE_LAB.txt";
        String courseHistory = "data/CourseHistory.txt";
        readDataFile(professorFile);
        readCourseHistory(courseHistory);
    }

    public static void readDataFile(String filePath) {
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                Professor professor = new Professor(data[0], data[1], data[2].split(", "), data[3], data[4], data[5], data[6]);
                professorRepository.save(professor);
            }

        } catch (IOException e) {
            System.err.println("Error occurred while reading " + filePath + " file.");
            e.printStackTrace();
        }
    }
    
    private void readCourseHistory(String filePath) {
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                CourseHistory course = new CourseHistory(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), Integer.parseInt(data[5]));
                coursehistoryRepository.save(course);
            }

        } catch (IOException e) {
            System.err.println("Error occurred while reading " + filePath + " file.");
            e.printStackTrace();
        }
    }
}