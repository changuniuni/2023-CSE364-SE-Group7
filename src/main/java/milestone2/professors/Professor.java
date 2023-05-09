package milestone2.professors;
 
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "labs")
class Professor {
  @Id
  private String id;
  private String name;
  private String[] area;
  private String topic;
  private String desc;
  private String email;
  private String office;

  Professor(String id, String name, String[] area, String topic, String desc, String email, String office) {
    this.id = id;
    this.name = name;
    this.area = area;
    this.topic = topic;
    this.desc = desc;
    this.email = email;
    this.office = office;
  }

  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String[] getArea() {
    return this.area;
  }
  public void setArea(String[] area) {
    this.area = area;
  }

  public String getTopic() {
    return this.topic;
  }
  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getDesc() {
        return desc;
  }
  public void setDesc(String desc) {
        this.desc = desc;
  }

  public String getEmail() {
    return this.email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  public String getOffice() {
    return this.office;
  }
  public void setOffice(String office) {
    this.office = office;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Professor))
      return false;
    Professor professor = (Professor) o;
    return Objects.equals(this.id, professor.id) && Objects.equals(this.name, professor.name)
        && Objects.equals(this.area, professor.area) && Objects.equals(this.topic, professor.topic)
        && Objects.equals(this.desc, professor.desc) && Objects.equals(this.email, professor.email)
        && Objects.equals(this.office, professor.office);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.area, this.topic, this.desc, this.email, this.office);
  }

  @Override
  public String toString() {
    return "Professor{" + "id=" + this.id + ", name='" + this.name + '\'' + ", area='" + this.area + '\'' + 
    ", topic='" + this.topic + '\'' + ", desc='" + this.desc + '\'' + ", email='" + this.email + '\'' + 
    ", office='" + this.office + '\'' + '}';
  }
}
