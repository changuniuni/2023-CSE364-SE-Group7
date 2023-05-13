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
public class CourseTest {
// Give the junit test code for the  User.java file.
    private Course course;

    @BeforeEach
    public void setUp() {
        course = new Course("4111", "UNI111", "Introduction to CSE", "Required", new String[]{"0"}, "Freshman", 2, "Basic", "Lecture for introducing CSE to Unistars.");
    }

    @Test
    public void testCourseGetSet() {
        try{
            course = new Course("4111", "UNI111", "Introduction to CSE", "Required", new String[]{"0"}, "Freshman", 2, "Basic", "Lecture for introducing CSE to Unistars.");
            course.setCourseId("2107");
            assertNotEquals("4111", course.getCourseId());

            course.setCode("ITP107");
            assertNotEquals("UNI111", course.getCode());

            course.setTitle("Introduction to AI Programming I");
            assertNotEquals("Introduction to CSE", course.getTitle());
            
            course.setMandatory("Elective");
            assertNotEquals("Required", course.getMandatory());

            course.setPrereq(new String[]{"4111","2117"});
            String[] prereqExpected = {"0"};
            for (String targetResult : course.getPrereq()) {
                for (String targetExpected : prereqExpected) {
                    assertNotEquals(targetResult, targetExpected);
                }
            }

            course.setAcadYear("Unistar");
            assertNotEquals("Freshman", course.getAcadYear());

            course.setOpenSmes(1);
            assertNotEquals(2, course.getOpenSmes());

            course.setType("Programming");
            assertNotEquals("Basic", course.getType());

            course.setDesc("Python introduction.");
            assertNotEquals("Lecture for introducing CSE to Unistars.", course.getDesc());
        } catch (Exception e) {
            fail();
        }
    }
}
