package milestone2;

import milestone2.courses.Course;
import milestone2.sign_up.model.User;

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


@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {
// Give the junit test code for the  User.java file.
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("1", "John");
    }

    @Test
    public void testGetId() {
        try{
            assertEquals("1", user.getId());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testGetName() {
        try{
            assertEquals("John", user.getName());
        } catch (Exception e) {
            fail();
        }
    }  
}
