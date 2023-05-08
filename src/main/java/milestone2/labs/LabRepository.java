package milestone2.labs;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
 
public interface LabRepository extends MongoRepository<Lab, String> {

}
