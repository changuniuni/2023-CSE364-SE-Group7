package milestone2.sign_up.controller;

import milestone2.courses.Course;
import milestone2.sign_up.model.User;
import milestone2.sign_up.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userRepository.save(new User(user.getId(), user.getName()));
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable String id) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/users/{id}/courses")
    public List<Course> getCourses(@PathVariable String id) {
        User user = userRepository.findById(id).orElse(null);
        return userService.getCourses(user);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setId(user.getId());
            userRepository.save(existingUser);
        }
        return existingUser;
    }



    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    @DeleteMapping("/users")
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }
}