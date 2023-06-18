package milestone3.professors;

import org.springframework.data.mongodb.repository.MongoRepository;
 
public interface ProfessorRepository extends MongoRepository<Professor, String> {

}
