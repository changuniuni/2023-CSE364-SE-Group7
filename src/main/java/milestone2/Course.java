package milestone2;
 
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courses")
class Course {
  @Id
  private String id;
  private String code;
  private String title;
  private String mandatory;
  private String[] prereq;
  private String acad_year;
  private String type;
  private String desc;

  Course(String id, String code, String title, String mandatory, String[] prereq, String acad_year, String type, String desc) {
    this.id = id;
    this.code = code;
    this.title = title;
    this.mandatory = mandatory;
    this.prereq = prereq;
    this.acad_year = acad_year;
    this.type = type;
    this.desc = desc;
  }

  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return this.code;
  }
  public void setCode(String code) {
    this.code = code;
  }

  public String getTitle() {
    return this.title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  public String getMandatory() {
    return this.mandatory;
  }
  public void setMandatory(String mandatory) {
    this.mandatory = mandatory;
  }

  public String[] getPrereq() {
        return prereq;
  }
  public void setPrereq(String[] prereq) {
        this.prereq = prereq;
  }

  public String getAcadYear() {
    return this.acad_year;
  }
  public void setAcadYear(String acad_year) {
    this.acad_year = acad_year;
  }

  public String getType() {
    return this.type;
  }
  public void setType(String type) {
    this.type = type;
  }

  public String getDesc() {
    return this.desc;
  }
  public void setDesc(String desc) {
    this.desc = desc;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Course))
      return false;
    Course course = (Course) o;
    return Objects.equals(this.id, course.id) && Objects.equals(this.code, course.code)
        && Objects.equals(this.title, course.title) && Objects.equals(this.mandatory, course.mandatory)
        && Objects.equals(this.prereq, course.prereq) && Objects.equals(this.acad_year, course.acad_year)
        && Objects.equals(this.type, course.type) && Objects.equals(this.desc, course.desc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.code, this.title, this.mandatory, this.prereq, this.acad_year, this.type, this.desc);
  }

  @Override
  public String toString() {
    return "Course{" + "id=" + this.id + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + 
    ", mandatory='" + this.mandatory + '\'' + ", prereq='" + this.prereq + '\'' + ", acad_year='" + this.acad_year + '\'' + 
    ", type='" + this.type + '\'' + ", desc='" + this.desc + '\'' + '}';
  }
}
