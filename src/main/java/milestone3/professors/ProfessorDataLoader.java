package milestone3.professors;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;


@Component
public class ProfessorDataLoader {

    @Autowired
    private ProfessorRepository professorRepository;
    
    String professorFile = "data/CSE_LAB.txt";

    @PostConstruct
    public void loadData() throws IOException {
        readDataFile(professorFile);
    }

    private void readDataFile(String filePath) throws IOException {
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(professorFile))) {

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                Professor professor = new Professor(data[0], data[1], data[2].split(", "), data[3], data[4], data[5], data[6]);
                professorRepository.save(professor);
            }

        } catch (IOException e) {
            throw e;
        }

        
    }
}