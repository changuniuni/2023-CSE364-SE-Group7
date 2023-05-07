package milestone2.sign_up.controller;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void deleteAllUsers() {
        mongoTemplate.getCollection("users").deleteMany(new Document());
    }
    
}
