package milestone2.professors;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
 
public interface CourseHistoryRepository extends MongoRepository<CourseHistory, String> {

}
