package milestone2.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoCollection;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import milestone2.courses.Course;
import milestone2.sign_up.controller.UserService;
import milestone2.sign_up.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private MongoCollection<Document> mongoCollection;

    @InjectMocks
    private UserService userService;

    @Test
    public void deleteAllUsersTest() {
        when(mongoTemplate.getCollection("users")).thenReturn(mongoCollection);

        userService.deleteAllUsers();

        verify(mongoCollection, times(1)).deleteMany(new Document());
    }

    @Test
    public void getCoursesTest() {
        Course course1 = new Course(null, null, null, null, null, null, 0, null, null); // Assuming you have a Course class
        Course course2 = new Course(null, null, null, null, null, null, 0, null, null); 
        List<Course> courses = Arrays.asList(course1, course2);
        
        User user = new User(); // Assuming you have a User class
        user.setCourses(courses); // Assuming you have a setCourseList method

        List<Course> resultCourses = userService.getCourses(user);

        assertEquals(courses, resultCourses);
    }
}
