package milestone3.professors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CourseHistoryTest {

    private CourseHistory chistory1;
    private CourseHistory chistory2;

    @BeforeEach
    public void setUp() {
        chistory1 = new CourseHistory("1", "abc", "12", "kyeongHoon_Min", 2023, 1);
        chistory2 = new CourseHistory("2", "qwe", "22", "changHyeon_Kim", 2022, 2);
    }

    @Test
    void testGetCourseId() {
        assertEquals("abc", chistory1.getCourseId());
        assertEquals("qwe", chistory2.getCourseId());
    }

    @Test
    void testGetId() {
        assertEquals("1", chistory1.getId());
        assertEquals("2", chistory2.getId());
    }

    @Test
    void testGetOpenSmes() {
        assertEquals(1, chistory1.getOpenSmes());
        assertEquals(2, chistory2.getOpenSmes());
    }

    @Test
    void testGetOpenYear() {
        assertEquals(2023, chistory1.getOpenYear());
        assertEquals(2022, chistory2.getOpenYear());
    }

    @Test
    void testGetProfessorId() {
        assertEquals("12", chistory1.getProfessorId());
        assertEquals("22", chistory2.getProfessorId());
    }

    @Test
    void testGetProfessorName() {
        assertEquals("kyeongHoon_Min", chistory1.getProfessorName());
        assertEquals("changHyeon_Kim", chistory2.getProfessorName());
    }

    @Test
    void testSetCourseId() {
        chistory1.setCourseId("CSE");
        assertEquals("CSE", chistory1.getCourseId());
        chistory2.setCourseId("MTH");
        assertEquals("MTH", chistory2.getCourseId());
    }

    @Test
    void testSetId() {
        chistory1.setId("5");
        assertEquals("5", chistory1.getId());
        chistory2.setId("6");
        assertEquals("6", chistory2.getId());
    }

    @Test
    void testSetOpenSmes() {
        chistory1.setOpenSmes(2);
        assertEquals(2, chistory1.getOpenSmes());
        chistory2.setOpenSmes(1);
        assertEquals(1, chistory2.getOpenSmes());
    }

    @Test
    void testSetOpenYear() {
        chistory1.setOpenYear(2022);
        assertEquals(2022, chistory1.getOpenYear());
        chistory2.setOpenYear(2023);
        assertEquals(2023, chistory2.getOpenYear());
    }

    @Test
    void testSetProfessorId() {
        chistory1.setProfessorId("31");
        assertEquals("31", chistory1.getProfessorId());
        chistory2.setProfessorId("32");
        assertEquals("32", chistory2.getProfessorId());
    }

    @Test
    void testSetProfessorName() {
        chistory1.setProfessorName("James");
        assertEquals("James", chistory1.getProfessorName());
        chistory2.setProfessorName("Lowa");
        assertEquals("Lowa", chistory2.getProfessorName());
    }
}
