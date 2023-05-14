package milestone2.professors;

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

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class ProfessorTest {

    private Professor professor;
    private Professor professor2;


    @BeforeEach
    public void setUp() {
        professor = new Professor("1", "KyeongHoon_Min", "A, B, C".split(", "), "asdf", "Hello", "mkh533@mkh.com", "105-105");
        professor2 = new Professor("3", "Changhyeon_Kim", "A, E, F".split(", "), "qwer", "Oh no", "junsu@ljs.com", "110-110");
        
    }

    @Test
    public void testGetId() {
        assertEquals("1", professor.getId());
        assertEquals("3", professor2.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("KyeongHoon_Min", professor.getName());
        assertEquals("Changhyeon_Kim", professor2.getName());
    }

    @Test
    public void testGetArea() {
        assertArrayEquals("A, B, C".split(", "), professor.getArea());
        assertArrayEquals("A, E, F".split(", "), professor2.getArea());
    }

    @Test
    public void testGetTopic() {
        assertEquals("asdf", professor.getTopic());
        assertEquals("qwer", professor2.getTopic());
    }

    @Test
    public void testGetDesc() {
        assertEquals("Hello", professor.getDesc());
        assertEquals("Oh no", professor2.getDesc());
    }

    @Test
    public void testGetEmail() {
        assertEquals("mkh533@mkh.com", professor.getEmail());
        assertEquals("junsu@ljs.com", professor2.getEmail());
    }

    @Test
    public void testGetOfiice() {
        assertEquals("105-105", professor.getOffice());
        assertEquals("110-110", professor2.getOffice());
    }

    @Test
    public void testGetPhone() {
        assertEquals("052-217-"+"1", professor.getPhone());
        assertEquals("052-217-"+"3", professor2.getPhone());
    }

    @Test
    public void testSetName() {
        professor.setName("Junsu");
        assertEquals("Junsu", professor.getName());
        professor2.setName("Min");
        assertEquals("Min", professor2.getName());
    }

    @Test
    public void testSetArea() {
        
        professor.setArea("G, G, D".split(", "));
        assertArrayEquals("G, G, D".split(", "), professor.getArea());
        professor2.setArea("Z, Y, h".split(", "));
        assertArrayEquals("Z, Y, h".split(", "), professor2.getArea());
    }

    @Test
    public void testSetTopic() {
        professor.setTopic("math");
        assertEquals("math", professor.getTopic());
        professor2.setTopic("english");
        assertEquals("english", professor2.getTopic());
    }

    @Test
    public void testSetDesc() {
        professor.setDesc("Bye");
        assertEquals("Bye", professor.getDesc());
        professor2.setDesc("Hello2");
        assertEquals("Hello2", professor2.getDesc());
    }

    @Test
    public void testSetEmail() {
        professor.setEmail("junsu@naver.com");
        assertEquals("junsu@naver.com", professor.getEmail());
        professor2.setEmail("hello@naver.com");
        assertEquals("hello@naver.com", professor2.getEmail());
    }

    @Test
    public void testSetOffice() {
        professor.setOffice("Bulid 202-306");
        assertEquals("Bulid 202-306", professor.getOffice());
        professor2.setOffice("Bulid 212-312");
        assertEquals("Bulid 212-312", professor2.getOffice());
    }

    @Test
    public void testSetPhone() {
        professor.setPhone("052-217-3242");
        assertEquals("052-217-3242", professor.getPhone());
        assertEquals("3242", professor.getId());
        professor2.setPhone("052-217-1234");
        assertEquals("052-217-1234", professor2.getPhone());
        assertEquals("1234", professor2.getId());
    }

    @Test
    public void testSetId() {
        professor.setId("5");
        assertEquals("5", professor.getId());
        assertEquals("052-217-5", professor.getPhone());
        professor.setId("6");
        assertEquals("6", professor.getId());
        assertEquals("052-217-6", professor.getPhone());
    }
}