package milestone3.courses;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class CourseCntMapTest {


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
