package milestone2.courses;
 
import java.util.Objects;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public
class CourseCountMap {
  private static CourseCountMap instance = null;
  private HashMap<String,Integer> map;

  private CourseCountMap() {
    map = new HashMap<>();
  }

  public static CourseCountMap getInstance() {
    if (instance == null) {
      instance = new CourseCountMap();
    }
    return instance;
  }

  public void put(String key, int value) {
    map.put(key, value);
  }

  public String getkey(int index) {
    int target = 0;
    String keyReturn = "";
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      keyReturn = entry.getKey();
      if (index == target)
        return keyReturn;
      else
        target++;
    }
    return keyReturn;
  }

  public int getValue(String key) {
    return map.get(key);
  }

  public Set<Map.Entry<String, Integer>> getEntrySet() {
    return map.entrySet();
  }
}