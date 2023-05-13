/*
package milestone2;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class ProfessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testProfessorCourse() throws Exception {
        List<Professor> professors = Arrays.asList(
            new Professor("Name1"),
            new Professor("Name2"),
            new Professor("Name3")
        );
    
        List<CourseHistory> courseHistories = Arrays.asList(
            new CourseHistory("Course1", "Name1"),
            new CourseHistory("Course2", "Name2"),
            new CourseHistory("Course3", "Name3")
        );
    
        Mockito.when(repository.findAll()).thenReturn(professors);
        Mockito.when(coursehistoryrepository.findAll()).thenReturn(courseHistories);
    
        mockMvc.perform(MockMvcRequestBuilders.get("/professors/search/Name/courses"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entityModels").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entityModels[0].content.courseName").value("Course1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entityModels[1].content.courseName").value("Course2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entityModels[2].content.courseName").value("Course3"));
    }
    
}
*/