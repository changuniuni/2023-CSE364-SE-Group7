package milestone3.professors;

import org.springframework.data.mongodb.repository.MongoRepository;
 
public interface CourseHistoryRepository extends MongoRepository<CourseHistory, String> {

}
