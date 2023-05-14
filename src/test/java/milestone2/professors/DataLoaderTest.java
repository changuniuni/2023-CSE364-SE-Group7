package milestone2.professors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;

@SpringBootTest
public class DataLoaderTest {

    private ProfessorDataLoader professorDataLoader;
    private CourseHistoryDataLoader coursehistoryrDataLoader;
    

    @BeforeEach
    public void setUp() {
        professorDataLoader = new ProfessorDataLoader();
        coursehistoryrDataLoader = new CourseHistoryDataLoader();

    }

    @Test
    void testLoadProfessorsData() {
        Assertions.assertThrows((IOException.class), () -> {
            professorDataLoader.professorFile = "invaild_Path";
            professorDataLoader.loadData();
        });
    }

    @Test
    void testLoadCourseHistoriesData() {
        Assertions.assertThrows((IOException.class), () -> {
            coursehistoryrDataLoader.courseHistory = "invaild_Path";
            coursehistoryrDataLoader.loadData();
        });
    }
}