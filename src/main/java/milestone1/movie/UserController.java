package milestone1.movie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public User getUserById(String id) {
        return userService.getUserById(id);
    }
    @PostMapping
    public User addUser(User user) {
        return userService.addUser(user);
    }
    @DeleteMapping("/{id}")
    public void deleteUserById(String id) {
        userService.deleteUserById(id);
    }
}