package milestone1.movie;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel ="movies", path = "movies")
public interface MovieRepository extends MongoRepository<Movie, String> {
    
}