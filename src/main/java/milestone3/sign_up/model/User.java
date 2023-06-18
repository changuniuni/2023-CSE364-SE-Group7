package milestone3.sign_up.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import milestone3.courses.Course;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    @NotBlank
    @NotNull
    private String name;
    private List<Course> courseList;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.courseList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
    }

    public List<Course> getCourseList() {
        if (this.courseList == null) {
            System.out.print("Course list is null");
        }
        return this.courseList;
    }

    public void deleteCourse(String courseId)
    {
        for (int i = 0; i < courseList.size(); i++)
        {
            if (courseList.get(i).getCourseId().equals(courseId))
            {
                courseList.remove(i);
                return;
            }
        }
    }
    public void setCourses(List<Course> courseList)
    {
        this.courseList = courseList;
    }
}