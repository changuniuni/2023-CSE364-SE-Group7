package milestone2.professors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "professors")
class Professor {
  @Id
  private String id;
  private String name;
  private String[] area;
  private String topic;
  private String desc;
  private String email;
  private String office;
  private String phone;

  Professor(String id, String name, String[] area, String topic, String desc, String email, String office) {
    this.id = id;
    this.name = name;
    this.area = area;
    this.topic = topic;
    this.desc = desc;
    this.email = email;
    this.office = office;
    this.phone = "052-217-"+id;
  }

  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
    this.phone = "052-217-"+id;
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

  public String getPhone() {
    return this.phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
    this.id = phone.replaceAll("052-217-","");
  }

}
