package milestone2.sign_up.controller;

import milestone2.courses.Course;
import milestone2.sign_up.model.User;
import milestone2.sign_up.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    // curl -X POST -H "Content-Type: application/json" -d '{"id": "20201111", "name": "Hong gil dong"}' http://localhost:8080/users
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userRepository.save(new User(user.getId(), user.getName()));
    }

    //curl -X GET http://localhost:8080/users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // curl -X GET http://localhost:8080/users/20201111
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable String id) {
        return userRepository.findById(id).orElse(null);
    }

    // curl -X GET http://localhost:8080/users/20201111/courses
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


    // curl -X DELETE http://localhost:8080/users/20201111
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    // curl -X DELETE http://localhost:8080/users     
    @DeleteMapping("/users")
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }
}