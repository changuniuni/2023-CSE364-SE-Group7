package milestone1.movie;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String userid;
    private String gender;
    private int age;
    private int occupation;
    private String zipCode;

    public User(String id, String gender, int age, int occupation, String zipCode) {
        this.userid = userid;
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
        this.zipCode = zipCode;
    }

    public String getUserId() {
        return userid;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public int getOccupation() {
        return occupation;
    }

    public String getZipCode() {
        return zipCode;
    }
}