package milestone2.courses;
 
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courses")
public
class Course {
  @Id
  private String id;
  private String code;
  private String title;
  private String mandatory;
  private String[] prereq;
  private String acad_year;
  private int open_smes;
  private String type;
  private String desc;

  public Course(String id, String code, String title, String mandatory, String[] prereq, String acad_year, int open_smes, String type, String desc) {
    this.id = id;
    this.code = code;
    this.title = title;
    this.mandatory = mandatory;
    this.prereq = prereq;
    this.acad_year = acad_year;
    this.open_smes = open_smes;
    this.type = type;
    this.desc = desc;
  }

  public String getCourseId() {
    return this.id;
  }
  public void setCourseId(String id) {
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

  public int getOpenSmes() {
    return this.open_smes;
  }
  public void setOpenSmes(int open_smes) {
    this.open_smes = open_smes;
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
}