package milestone2.courses;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;

@SpringBootTest
public class CourseDataLoaderTest {

    private CourseDataLoader courseDataLoader;
    

    @BeforeEach
    public void setUp() {
        courseDataLoader = new CourseDataLoader();
    }

    @Test
    void testLoadProfessorsData() {
        Assertions.assertThrows((IOException.class), () -> {
            courseDataLoader.courseFile = "invaild_Path";
            courseDataLoader.loadData();
        });
    }
}