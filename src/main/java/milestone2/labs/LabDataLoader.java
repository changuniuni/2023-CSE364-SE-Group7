package milestone2.labs;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;


@Component
public class LabDataLoader {

    @Autowired
    private LabRepository labRepository;

    @PostConstruct
    public void loadData() {
        String labFile = "data/CSE_LAB.txt";

        readDataFile(labFile);
    }

    private void readDataFile(String filePath) {
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                Lab lab = new Lab(data[0], data[1], data[2].split(", "), data[3], data[4], data[5], data[6]);
                labRepository.save(lab);
            }

        } catch (IOException e) {
            System.err.println("Error occurred while reading " + filePath + " file.");
            e.printStackTrace();
        }
    }
}