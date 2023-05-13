package milestone2;

import milestone2.courses.Course;
import milestone2.sign_up.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
// Give the junit test code for the  User.java file.
    private User user;
    private Course course;

    @BeforeEach
    public void setUp() {
        user = new User("1", "John");
        course = new Course(null, null, null, null, null, null, 0, null, null);
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
        user.setName("Jane");
        assertEquals("Jane", user.getName());
    }

    @Test
    public void testGetCourseList() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(course);
        user.setCourses(courseList);
        assertEquals(courseList, user.getCourseList());
    }

    @Test
    public void testSetCourseList() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(course);
        user.setCourses(courseList);
        assertEquals(courseList, user.getCourseList());
    }

  
}
