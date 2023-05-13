package milestone2;

import milestone2.courses.Course;
import milestone2.sign_up.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    private User user;
    private Course course1;
    private Course course2;
    private Course course3;
    private Course course4;


    @BeforeEach
    public void setUp() {
        user = new User("1", "John");
        course1 = new Course(null, null, null, null, null, null, 0, null, null); // initialize this with appropriate values for Course
        course2 = new Course(null, null, null, null, null, null, 0, null, null); // initialize this with appropriate values for Course
        course3 = new Course("2222", "CSE1111", "Title1111", "mandatory", null, "2020", 0, "type", "desc");
        course4 = new Course("2222", "CSE2222", "Title2222", "mandatory", null, "2020", 0, "type", "desc");
        // String id, String code, String title, String mandatory, String[] prereq, String acad_year, int open_smes, String type, String desc
    }

    @Test
    public void testGetId() {
        assertEquals("1", user.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("John", user.getName());
    }

    @Test
    public void testSetName() {
        user.setName("Adam");
        assertEquals("Adam", user.getName());
    }

    @Test
    public void testSetNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            user.setName(null);
        });
    }

    @Test
    public void testGetCourseList() {
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        user.setCourses(courses);
        assertEquals(courses, user.getCourseList());
    }

    @Test
    public void testDeleteCourse() {
        List<Course> courses = new ArrayList<>();
        courses.add(course3);
        courses.add(course4);
        user.setCourses(courses);
        user.deleteCourse(course3.getCourseId());
        assertTrue(user.getCourseList().contains(course4));
    }

    @Test
    public void testGetCourseListWhenNull() {
        // This test case will test the branch when courseList is null
        assertNull(user.getCourseList());
    }

    @Test
    public void testDeleteCourseNotFound() {
        // This test case will test the branch when the course to delete is not found
        List<Course> courses = new ArrayList<>();
        courses.add(course3);
        user.setCourses(courses);
        user.deleteCourse(course4.getCourseId());
        assertFalse(user.getCourseList().contains(course3));
    }

    @Test
    public void testSetCoursesToNull() {
        // This test case will test the setCourses method with a null argument
        user.setCourses(null);
        assertNull(user.getCourseList());
    }
}