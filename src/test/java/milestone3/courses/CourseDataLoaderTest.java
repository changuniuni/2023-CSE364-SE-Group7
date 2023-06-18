package milestone3.courses;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CourseDataLoaderTest {

    private CourseDataLoader courseDataLoader;
    

    @BeforeEach
    public void setUp() {
        courseDataLoader = new CourseDataLoader();
    }

    @Test
    void testLoadProfessorsData() {
        Assertions.assertThrows((NullPointerException.class), () -> {
            courseDataLoader.courseFile = "invaild_Path";
            courseDataLoader.loadData();
        });
    }
}