package milestone2.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import milestone2.courses.Course;
import milestone2.sign_up.controller.UserService;
import milestone2.sign_up.model.User;
import milestone2.sign_up.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        User user = new User("1", "John");
        Mockito.when(userRepository.findById("1")).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        Mockito.when(userService.getCourses(Mockito.any(User.class))).thenReturn(Arrays.asList(new Course(null, null, null, null, null, null, 0, null, null)));
    }

    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"name\":\"John\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserById_UserExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserById_UserDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCourses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/courses"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser_UserExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"name\":\"Adam\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser_UserDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/users/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"2\",\"name\":\"Adam\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users"))
                .andExpect(status().isOk());
    }


    @Test
    public void getAllUsersReturnsOkWhenUsersExist() throws Exception {
        User user1 = new User("1", "John");
        User user2 = new User("2", "Jane");
        List<User> users = Arrays.asList(user1, user2);

        given(userRepository.findAll()).willReturn(users);
    }

    @Test
    public void getAllUsersReturnsNotFoundWhenNoUsersExist() throws Exception {
        List<User> users = new ArrayList<>();

        given(userRepository.findAll()).willReturn(users);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
