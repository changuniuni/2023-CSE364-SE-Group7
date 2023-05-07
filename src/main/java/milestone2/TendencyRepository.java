package milestone2;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
 
public interface TendencyRepository extends MongoRepository<Tendency, String> {

}
