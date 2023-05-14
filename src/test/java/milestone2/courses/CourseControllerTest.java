package milestone2;
import milestone2.courses.Course;
import milestone2.courses.CourseController;
import milestone2.courses.CourseRepository;
import milestone2.courses.CourseModelAssembler;
import milestone2.courses.CourseCountMap;
import milestone2.sign_up.controller.UserService;
import milestone2.sign_up.model.User;
import milestone2.sign_up.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;
import static org.mockito.BDDMockito.given;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;


@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private CourseModelAssembler courseModelAssembler;

    @MockBean
    private CourseCountMap courseCntMap;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    private HashMap<String, Integer> courseCountMap;

    @BeforeEach
    public void setUp() {
        Course course1 = new Course("9107", "ITP107", "Introduction to AI Programming I", "Required", new String[]{"0"}, "Freshman", 1, "Basic", "Introducing AI term via Python.");
        Course course2 = new Course("9364", "CSE364", "Software Engineering", "Elective", new String[]{"9241"}, "Junior", 1, "Software", "Techniques on developing high quality software.");
        Course course3 = new Course("9106", "ITP106", "Computer 6", "Elective", new String[]{"0"}, "Freshman", 1, "Basic", "Description 6.");
        Course course4 = new Course("9105", "ITP105", "Computer 5", "Elective", new String[]{"0"}, "Freshman", 1, "Basic", "Description 5.");
        Course course5 = new Course("9104", "ITP104", "Computer 4", "Elective", new String[]{"0"}, "Freshman", 1, "Basic", "Description 4.");
        Course course6 = new Course("9103", "ITP103", "Computer 3", "Elective", new String[]{"0"}, "Freshman", 1, "Basic", "Description 3.");
        Course course7 = new Course("9102", "ITP102", "Computer 2", "Elective", new String[]{"0"}, "Freshman", 1, "Basic", "Description 2.");
        EntityModel<Course> course1Model = EntityModel.of(course1);
        EntityModel<Course> course2Model = EntityModel.of(course2);
        EntityModel<Course> course3Model = EntityModel.of(course3);
        EntityModel<Course> course4Model = EntityModel.of(course4);
        EntityModel<Course> course5Model = EntityModel.of(course5);
        EntityModel<Course> course6Model = EntityModel.of(course6);
        EntityModel<Course> course7Model = EntityModel.of(course7);
        Mockito.when(courseRepository.findAll()).thenReturn(Arrays.asList(course1,course2,course3,course4,course5,course6,course7));
        Mockito.when(courseModelAssembler.toModel(course1)).thenReturn(course1Model);
        Mockito.when(courseModelAssembler.toModel(course2)).thenReturn(course2Model);
        Mockito.when(courseModelAssembler.toModel(course3)).thenReturn(course3Model);
        Mockito.when(courseModelAssembler.toModel(course4)).thenReturn(course4Model);
        Mockito.when(courseModelAssembler.toModel(course5)).thenReturn(course5Model);
        Mockito.when(courseModelAssembler.toModel(course6)).thenReturn(course6Model);
        Mockito.when(courseModelAssembler.toModel(course7)).thenReturn(course7Model);
        Mockito.when(courseRepository.findById("9107")).thenReturn(Optional.of(course1));
        Mockito.when(courseRepository.findById("9364")).thenReturn(Optional.of(course2));
        Mockito.when(courseRepository.findById("9106")).thenReturn(Optional.of(course3));
        Mockito.when(courseRepository.findById("9105")).thenReturn(Optional.of(course4));
        Mockito.when(courseRepository.findById("9104")).thenReturn(Optional.of(course5));
        Mockito.when(courseRepository.findById("9103")).thenReturn(Optional.of(course6));
        Mockito.when(courseRepository.findById("9102")).thenReturn(Optional.of(course7));

        CourseCountMap courseCntMap = CourseCountMap.getInstance();
        courseCntMap.put("9107", 110);
        courseCntMap.put("9106", 120);
        courseCntMap.put("9105", 130);
        courseCntMap.put("9104", 145);
        courseCntMap.put("9103", 150);
        courseCntMap.put("9102", 100);
        courseCntMap.put("9364", 140);
        /*
        User user = new User("1", "John");
        Mockito.when(userRepository.findById("1")).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        Mockito.when(userService.getCourses(Mockito.any(User.class))).thenReturn(Arrays.asList(new Course(null, null, null, null, null, null, 0, null, null)));
        */
    }

    @Test
    public void testCourses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses"))
            .andExpect(status().isOk());
    }

    @Test
    public void testCoursesId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/9107"))
            .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/9241"))
            .andExpect(status().isNotFound());
    }

    /*
    @Test
    public void testCoursePostUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/courses/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"name\":\"John\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCourseDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1/courses/9999"))
                .andExpect(status().isNotFound());
    }
    */
    @Test
    public void testCoursesGradeYear() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/grade/freshman"))
            .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/grade/Senior"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testCoursesGradeYearSmes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/grade/Freshman/1"))
            .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/grade/Freshman/0"))
            .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/grade/Freshman/2"))
            .andExpect(status().isNotFound());
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/grade/junior/2"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testCoursesArea() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/area/BaSiC"))
            .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/area/HCI"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testCoursesNext() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/next/9241"))
            .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/next/9107"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testCoursesTend() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/tendency"))
            .andExpect(status().isOk());
    }
    
    @Test
    public void testCoursesTendYear() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/tendency/Freshman"))
            .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/tendency/"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testCoursesRecArea() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/recommend/s of tWa re"))
            .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/recommend/BASIC"))
            .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/recommend/"))
            .andExpect(status().isNotFound());
    }

    @AfterEach
    public void testDone() {
        courseCntMap.remove("9107");
        courseCntMap.remove("9106");
        courseCntMap.remove("9105");
        courseCntMap.remove("9104");
        courseCntMap.remove("9103");
        courseCntMap.remove("9102");
        courseCntMap.remove("9364");
    }
}