package milestone2;

import org.springframework.data.mongodb.repository.MongoRepository;
 
public interface TendencyRepository extends MongoRepository<Tendency, String> {

}
