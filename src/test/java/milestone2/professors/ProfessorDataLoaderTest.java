package milestone2.professors;

import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@SpringBootTest
public class ProfessorDataLoaderTest {

    @Autowired
    private ProfessorDataLoader professorDataLoader;

    private ProfessorRepository professorRepository = mock(ProfessorRepository.class);
    private CourseHistoryRepository courseHistoryRepository = mock(CourseHistoryRepository.class);

    @TempDir
    public File tempDir;

    @Test
    public void testReadDataFile() throws IOException {
        File file = new File(tempDir, "asdf.txt");
        List<String> lines = Arrays.asList(
            "John\tDoe\tProgramming, Database\tjohn.doe@example.com\t01-02-3456\t555-1234\tAssistant Professor",
            "Jane\tDoe\tAI, Machine Learning\tjane.doe@example.com\t01-02-3456\t555-5678\tAssociate Professor"
        );
        java.nio.file.Files.write(file.toPath(), lines);
        
        ProfessorDataLoader.readDataFile("asdf.txt");

        verify(professorRepository, times(2)).save(any(Professor.class));
    }

    @Test
    public void testReadCourseHistory() throws IOException {
        File file = new File(tempDir, "CourseHistory.txt");
        List<String> lines = Arrays.asList(
            "John\tDoe\tCSE101\tSpring 2022\t3\tA",
            "Jane\tDoe\tCSE102\tSpring 2022\t3\tB+"
        );
        java.nio.file.Files.write(file.toPath(), lines);
        
        professorDataLoader.readCourseHistory(file.getAbsolutePath());

        verify(courseHistoryRepository, times(2)).save(any(CourseHistory.class));
    }
}