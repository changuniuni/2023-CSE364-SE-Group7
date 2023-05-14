/*
package milestone2;

import milestone2.courses.Course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@SpringBootTest
public class CourseTest {

    private Course course;

    @BeforeEach
    public void setUp() {
        course = new Course("4111", "UNI111", "Introduction to CSE", "Required", new String[]{"0"}, "Freshman", 2, "Basic", "Lecture for introducing CSE to Unistars.");
    }

    @Test
    public void testCourseGet() {
        try {
            assertEquals("4111", course.getCourseId());
            assertEquals("UNI111", course.getCode());
            assertEquals("Introduction to CSE", course.getTitle());
            assertEquals("Required", course.getMandatory());
            assertArrayEquals(new String[]{"0"}, course.getPrereq());
            assertEquals("Freshman", course.getAcadYear());
            assertEquals(2, course.getOpenSmes());
            assertEquals("Basic", course.getType());
            assertEquals("Lecture for introducing CSE to Unistars.", course.getDesc());
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testCourseSet() {
        try {
            course.setCourseId("2107");
            assertNotEquals("4111", course.getCourseId());
            course.setCode("ITP107");
            assertNotEquals("UNI111", course.getCode());
            course.setTitle("Introduction to AI Programming I");
            assertNotEquals("Introduction to CSE", course.getTitle());
            course.setMandatory("Elective");
            assertNotEquals("Required", course.getMandatory());
            course.setPrereq(new String[]{"4111","2117"});
            assertNotEquals(new String[]{"0"}, course.getPrereq());
            course.setAcadYear("Unistar");
            assertNotEquals("Freshman", course.getAcadYear());
            course.setOpenSmes(1);
            assertNotEquals(2, course.getOpenSmes());
            course.setType("Programming");
            assertNotEquals("Basic", course.getType());
            course.setDesc("Python introduction.");
            assertNotEquals("Lecture for introducing CSE to Unistars.", course.getDesc());
        }
        catch (Exception e) {
            fail();
        }
    }
}

*/