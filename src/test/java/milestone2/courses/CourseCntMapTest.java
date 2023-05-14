package milestone2;
import milestone2.courses.CourseCountMap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;


@SpringBootTest
public class CourseCntMapTest {

    private CourseCountMap map;

    @Test
    public void testCntMapGetvalExist() {
        try {
            CourseCountMap countMapDummy = CourseCountMap.getInstance();
            countMapDummy.put("9999", 99);
            assertEquals(99, countMapDummy.getValue("9999"));
            countMapDummy.remove("9999");
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testCntMapGetvalNoExist() {
        try {
            CourseCountMap countMapDummy = CourseCountMap.getInstance();
            assertEquals(-99, countMapDummy.getValue("9999"));
        }
        catch (Exception e) {
            fail();
        }
    }
}
