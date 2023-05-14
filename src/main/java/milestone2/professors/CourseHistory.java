package milestone2.professors;
 
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "coursehistories")
class CourseHistory {
  @Id
  private String id;
  private String courseid;
  private String professorid;
  private String professorname;
  private int open_year;
  private int open_smes;

  CourseHistory(String id, String courseid, String professorid, String professorname, int open_year, int open_smes) {
    this.id = id;
    this.courseid = courseid;
    this.professorid = professorid;
    this.professorname = professorname;
    this.open_year = open_year;
    this.open_smes = open_smes;
  }

  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public String getCourseId() {
    return this.courseid;
  }
  public void setCourseId(String courseid) {
    this.courseid = courseid;
  }

  public String getProfessorId() {
    return this.professorid;
  }
  public void setProfessorId(String professorid) {
    this.professorid = professorid;
  }

  public String getProfessorName() {
    return this.professorname;
  }
  public void setProfessorName(String professorname) {
    this.professorname = professorname;
  }

  public int getOpenYear() {
    return open_year;
  }
  public void setOpenYear(int open_year) {
    this.open_year = open_year;
  }
  public int getOpenSmes() {
    return this.open_smes;
  }
  public void setOpenSmes(int open_smes) {
    this.open_smes = open_smes;
  }
}
