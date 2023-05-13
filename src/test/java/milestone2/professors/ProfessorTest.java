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


    @BeforeEach
    public void setUp() {
        professor = new Professor("1", "KyeongHoon_Min", "A, B, C".split(", "), "asdf", "Hello", "mkh533@mkh.com", "105-105");

    }

    @Test
    public void testGetId() {
        assertEquals("1", professor.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("KyeongHoon_Min", professor.getName());
    }

    @Test
    public void testGetArea() {
        assertArrayEquals("A, B, C".split(", "), professor.getArea());
    }

    @Test
    public void testGetTopic() {
        assertEquals("asdf", professor.getTopic());
    }

    @Test
    public void testGetDesc() {
        assertEquals("Hello", professor.getDesc());
    }

    @Test
    public void testGetEmail() {
        assertEquals("mkh533@mkh.com", professor.getEmail());
    }

    @Test
    public void testGetOfiice() {
        assertEquals("105-105", professor.getOffice());
    }

    @Test
    public void testGetPhone() {
        assertEquals("052-217-"+"1", professor.getPhone());
    }

    @Test
    public void testSetName() {
        professor.setName("Junsu");
        assertEquals("Junsu", professor.getName());
    }

    @Test
    public void testSetArea() {
        professor.setArea("G, G, D".split(", "));
        assertArrayEquals("G, G, D".split(", "), professor.getArea());
    }

    @Test
    public void testSetTopic() {
        professor.setTopic("math");
        assertEquals("math", professor.getTopic());
    }

    @Test
    public void testSetDesc() {
        professor.setDesc("Bye");
        assertEquals("Bye", professor.getDesc());
    }

    @Test
    public void testSetEmail() {
        professor.setEmail("junsu@naver.com");
        assertEquals("junsu@naver.com", professor.getEmail());
    }

    @Test
    public void testSetOffice() {
        professor.setOffice("Bulid 202-306");
        assertEquals("Bulid 202-306", professor.getOffice());
    }

    @Test
    public void testSetPhone() {
        professor.setPhone("052-217-3242");
        assertEquals("052-217-3242", professor.getPhone());
        assertEquals("3242", professor.getId());
    }

    @Test
    public void testSetId() {
        professor.setId("5");
        assertEquals("5", professor.getId());
        assertEquals("052-217-5", professor.getPhone());
    }
}