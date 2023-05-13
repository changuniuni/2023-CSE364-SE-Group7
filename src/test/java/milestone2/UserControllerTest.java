package milestone2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import milestone2.sign_up.controller.UserController;
import milestone2.sign_up.controller.UserService;
import milestone2.sign_up.model.User;
import milestone2.sign_up.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void createUserTest() throws Exception {
        User user = new User("123", "John");
        when(userRepository.save(any())).thenReturn(user);

        mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":\"123\",\"name\":\"John\"}"))
            .andExpect(status().isOk());

        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void getAllUsersTest() throws Exception {
        List<User> users = Arrays.asList(new User("123", "John"), new User("456", "Jane"));
        when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(get("/users")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getUserByIdTest() throws Exception {
        User user = new User("123", "John");
        when(userRepository.findById("123")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/123")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(userRepository, times(1)).findById("123");
    }

    @Test
    public void updateUserTest() throws Exception {
        User user = new User("123", "John");
        when(userRepository.findById("123")).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        mockMvc.perform(put("/users/123")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":\"123\",\"name\":\"John\"}"))
            .andExpect(status().isOk());

        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/users/123")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(userRepository, times(1)).deleteById("123");
    }

    @Test
    public void deleteAllUsersTest() throws Exception {
        mockMvc.perform(delete("/users")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(userService, times(1)).deleteAllUsers();
    }
}
