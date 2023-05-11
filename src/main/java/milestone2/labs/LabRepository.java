package milestone2.labs;

import org.springframework.data.mongodb.repository.MongoRepository;
 
public interface LabRepository extends MongoRepository<Lab, String> {

}
