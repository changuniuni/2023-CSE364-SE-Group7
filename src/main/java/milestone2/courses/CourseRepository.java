package milestone2.courses;

import org.springframework.data.mongodb.repository.MongoRepository;
 
public interface CourseRepository extends MongoRepository<Course, String> {

}
