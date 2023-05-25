package api.mozaritta.anime.repositories;

import api.mozaritta.anime.entities.Anime;
import api.mozaritta.anime.entities.Review;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId> {

}
