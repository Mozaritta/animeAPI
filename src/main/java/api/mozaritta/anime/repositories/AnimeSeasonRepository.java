package api.mozaritta.anime.repositories;

import api.mozaritta.anime.entities.AnimeSeason;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeSeasonRepository extends MongoRepository<AnimeSeason, Integer> {
}
