package milestone2.professors;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;


@Component
public class ProfessorDataLoader {

    @Autowired
    private ProfessorRepository professorRepository;

    @PostConstruct
    public void loadData() {
        String professorFile = "data/CSE_LAB.txt";

        readDataFile(professorFile);
    }

    private void readDataFile(String filePath) {
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                Professor professor = new Professor(data[0], data[1], data[2].split(", "), data[3], data[4], data[5], data[6], "052-217-"+data[0]);
                professorRepository.save(professor);
            }

        } catch (IOException e) {
            System.err.println("Error occurred while reading " + filePath + " file.");
            e.printStackTrace();
        }
    }
}