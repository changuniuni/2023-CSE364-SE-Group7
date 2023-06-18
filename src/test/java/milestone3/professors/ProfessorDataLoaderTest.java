package milestone3.professors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProfessorDataLoaderTest {

    private ProfessorDataLoader professorDataLoader;
    private CourseHistoryDataLoader coursehistoryrDataLoader;
    

    @BeforeEach
    public void setUp() {
        professorDataLoader = new ProfessorDataLoader();
        coursehistoryrDataLoader = new CourseHistoryDataLoader();

    }

    @Test
    void testLoadProfessorsData() {
        Assertions.assertThrows((NullPointerException.class), () -> {
            professorDataLoader.professorFile = "invaild_Path";
            professorDataLoader.loadData();
        });
    }

    @Test
    void testLoadCourseHistoriesData() {
        Assertions.assertThrows((NullPointerException.class), () -> {
            coursehistoryrDataLoader.courseHistory = "invaild_Path";
            coursehistoryrDataLoader.loadData();
        });
    }
}