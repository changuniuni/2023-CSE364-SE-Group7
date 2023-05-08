package milestone2.labs;
 
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "labs")
class Lab {
  @Id
  private String id;
  private String name;
  private String[] area;
  private String topic;
  private String desc;
  private String email;
  private String office;

  Lab(String id, String name, String[] area, String topic, String desc, String email, String office) {
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
    if (!(o instanceof Lab))
      return false;
    Lab lab = (Lab) o;
    return Objects.equals(this.id, lab.id) && Objects.equals(this.name, lab.name)
        && Objects.equals(this.area, lab.area) && Objects.equals(this.topic, lab.topic)
        && Objects.equals(this.desc, lab.desc) && Objects.equals(this.email, lab.email)
        && Objects.equals(this.office, lab.office);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.area, this.topic, this.desc, this.email, this.office);
  }

  @Override
  public String toString() {
    return "Lab{" + "id=" + this.id + ", name='" + this.name + '\'' + ", area='" + this.area + '\'' + 
    ", topic='" + this.topic + '\'' + ", desc='" + this.desc + '\'' + ", email='" + this.email + '\'' + 
    ", office='" + this.office + '\'' + '}';
  }
}
