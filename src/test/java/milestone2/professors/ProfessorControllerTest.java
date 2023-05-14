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

    private Professor professor1;
    private Professor professor2;

    private CourseHistory chistory;

    @BeforeEach
    public void setUp() {
        professor1 = new Professor("1", "KyeongHoon_Min", "A, B, C".split(", "), "asdf", "Hello", "mkh533@mkh.com", "105-105");
        Mockito.when(professorRepository.save(Mockito.any(Professor.class))).thenReturn(professor1);
        Mockito.when(professorRepository.findById("1")).thenReturn(Optional.of(professor1));
        professor2 = new Professor("3", "Changhyeon_Kim", "A, E, F".split(", "), "asdf", "Oh no", "junsu@ljs.com", "110-110");
        Mockito.when(professorRepository.save(Mockito.any(Professor.class))).thenReturn(professor2);
        Mockito.when(professorRepository.findById("3")).thenReturn(Optional.of(professor2));
        Mockito.when(professorRepository.findAll()).thenReturn(Arrays.asList(professor1,professor2));

        chistory = new CourseHistory("2", "abc", "1", "kyeongHoon_Min", 2023, 1);
        Mockito.when(coursehistoryRepository.save(Mockito.any(CourseHistory.class))).thenReturn(chistory);
        Mockito.when(coursehistoryRepository.findById("2")).thenReturn(Optional.of(chistory));
        Mockito.when(coursehistoryRepository.findAll()).thenReturn(Arrays.asList(chistory));
    }
    @Test
    public void testGetAllProfessors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/professors"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllCourseHistories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/coursehistories"))
                .andExpect(status().isOk());
    }

    @Test
    void testHistroyBrowseYear() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/coursehistories/browse/2023"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/coursehistories/browse/2024"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testHistroyBrowseYearSmes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/coursehistories/browse/2023/1"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/coursehistories/browse/2023/2"))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.get("/coursehistories/browse/2024/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testOne() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/coursehistories/2"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/coursehistories/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testProfessorCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/professors/search/Min/courses"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/search/junsu/courses"))
                .andExpect(status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/search/hyeon/courses"))
                .andExpect(status().isNotFound());

    }

    @Test
    void testSearchArea() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/professors/search/area/A"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/search/area/D"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSearchPhone() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/professors/search/phone/1"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/search/phone/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSearchTopic() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/professors/search/topic/as"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/search/topic/qwe"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSearchaName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/professors/search/name/min"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/professors/search/name/junsu"))
                .andExpect(status().isNotFound());
    }
}

