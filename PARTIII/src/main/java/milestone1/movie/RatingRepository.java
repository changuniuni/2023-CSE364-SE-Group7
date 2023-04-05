package milestone1.movie;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel ="ratings", path = "ratings")
public interface RatingRepository extends MongoRepository<Rating, String> {
    List<Rating> findByMovieId(@Param("id") String id);
}

