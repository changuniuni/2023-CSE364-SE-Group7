package milestone3.sign_up.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import milestone3.courses.Course;
import milestone3.sign_up.model.User;

@Service
public class UserService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void deleteAllUsers() {
        mongoTemplate.getCollection("users").deleteMany(new Document());
    }

    public List<Course> getCourses(User user) {
        return user.getCourseList();
    }
    
}
