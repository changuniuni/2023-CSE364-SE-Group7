package milestone2.professors;

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
public class ProfessorTest {

    private Professor professor;


    @BeforeEach
    public void setUp() {
        professor = new Professor("1", "KyeongHoon_Min", "A, B, C".split(", "), "asdf", "Hello", "mkh533@mkh.com", "105-105", "052-217-"+"1");

    }

    @Test
    public void testGetId() {
        assertEquals("1", professor.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("KyeongHoon_Min", professor.getName());
    }

    @Test
    public void testGetArea() {
        assertEquals("A, B, C".split(", "), professor.getArea());
    }

    @Test
    public void testGetTopic() {
        assertEquals("asdf", professor.getTopic());
    }

    @Test
    public void testGetDesc() {
        assertEquals("Hello", professor.getDesc());
    }

    @Test
    public void testGetEmail() {
        assertEquals("mkh533@mkh.com", professor.getEmail());
    }

    @Test
    public void testGetOfiice() {
        assertEquals("105-105", professor.getOffice());
    }

    @Test
    public void testGetPhone() {
        assertEquals("052-217-"+"1", professor.getPhone());
    }

    // @Test
    // public void testSetName() {
    //     user.setName("Adam");
    //     assertEquals("Adam", user.getName());
    // }

    // @Test
    // public void testSetNameThrowsException() {
    //     assertThrows(IllegalArgumentException.class, () -> {
    //         professor.setName(null);
    //     });
    // }

    // @Test
    // public void testGetCourseList() {
    //     List<Course> courses = new ArrayList<>();
    //     courses.add(course1);
    //     courses.add(course2);
    //     user.setCourses(courses);
    //     assertEquals(courses, user.getCourseList());
    // }

    // @Test
    // public void testDeleteCourse() {
    //     List<Course> courses = new ArrayList<>();
    //     courses.add(course3);
    //     courses.add(course4);
    //     user.setCourses(courses);
    //     user.deleteCourse(course3.getCourseId());
    //     assertTrue(user.getCourseList().contains(course4));
    // }

    // @Test
    // public void testGetCourseListWhenNull() {
    //     // This test case will test the branch when courseList is null
    //     assertNull(user.getCourseList());
    // }


    // @Test
    // public void testDeleteCourseNotFound() {
    //     // This test case will test the branch when the course to delete is not found
    //     List<Course> courses = new ArrayList<>();
    //     courses.add(course3);
    //     user.setCourses(courses);
    //     user.deleteCourse(course4.getCourseId());
    //     assertTrue(user.getCourseList().contains(course3)); // the course should still be there as it was not found for deletion
    // }

    // @Test
    // public void testDeleteCourse_CourseExists() {
    //     // Set up the course list
    //     List<Course> courses = new ArrayList<>();
    //     courses.add(course3);
    //     courses.add(course4);
    //     user.setCourses(courses);

    //     // Delete the course
    //     user.deleteCourse(course3.getCourseId());

    //     // Verify that the course was deleted
    //     assertFalse(user.getCourseList().contains(course3));
    // }

    // @Test
    // public void testDeleteCourse_CourseDoesNotExist() {
    //     // Set up the course list
    //     List<Course> courses = new ArrayList<>();
    //     courses.add(course3);
    //     user.setCourses(courses);

    //     // Attempt to delete a course that does not exist
    //     user.deleteCourse(course4.getCourseId());

    //     // Verify that the course list remains the same
    //     assertTrue(user.getCourseList().contains(course3));
    //     assertEquals(1, user.getCourseList().size());
    // }

    // @Test
    // public void testSetCoursesToNull() {
    //     // This test case will test the setCourses method with a null argument
    //     user.setCourses(null);
    //     assertNull(user.getCourseList());
    // }

}