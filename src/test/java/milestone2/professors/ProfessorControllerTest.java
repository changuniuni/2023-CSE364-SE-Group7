package milestone2.professors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.mockito.BDDMockito.given;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfessorRepository professorRepository;

    @MockBean
    private CourseHistoryRepository coursehistoryRepository;

    private Professor professor;

    @BeforeEach
    public void setUp() {
        professor = new Professor("1", "KyeongHoon_Min", "A, B, C".split(", "), "asdf", "Hello", "mkh533@mkh.com", "105-105");
        
        // Mockito.when(professorRepository.findById("1")).thenReturn(Optional.of(professor));
        // Mockito.when(professorRepository.save(Mockito.any(Professor.class))).thenReturn(professor);
        // Mockito.when(professorRepository.findAll()).thenReturn(Arrays.asList(professor));
        
    }
    @Test
    public void testGetAllProfessors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/professors"))
                .andExpect(status().isOk());
    }

    // @Test
    // void testHistroyBrowseYear() {

    // }

    // @Test
    // void testHistroyBrowseYearSmes() {

    // }

    // @Test
    // void testOne() {

    // }

    // @Test
    // void testProfessorCourse() {

    // }

    // @Test
    // void testSearchArea() {

    // }

    // @Test
    // void testSearchPhone() {


    // }

    // @Test
    // void testSearchTopic() {

    // }

    // @Test
    // void testSearchaName() {

    // }
}

