package milestone2;
 
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tendency")
class Tendency {
  @Id
  private String id;
  private String name;
  private String role;

  Tendency(String name, String role) {
    this.name = name;
    this.role = role;
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getRole() {
    return this.role;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Tendency))
      return false;
    Tendency tendency = (Tendency) o;
    return Objects.equals(this.id, tendency.id) && Objects.equals(this.name, tendency.name)
        && Objects.equals(this.role, tendency.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.role);
  }

  @Override
  public String toString() {
    return "Tendency{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
  }
}
