package api.mozaritta.anime.services;

import api.mozaritta.anime.entities.Anime;
import api.mozaritta.anime.entities.AnimeSeason;
import api.mozaritta.anime.repositories.AnimeRepository;
import api.mozaritta.anime.repositories.AnimeSeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnimeSeasonService {

    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private AnimeSeasonRepository animeSeasonRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public AnimeSeason createAnimeSeason(String season, Integer year, String imdbId){
        AnimeSeason animeSeason = new AnimeSeason(season, year);
        animeSeasonRepository.insert(animeSeason);
        return animeSeason;
    }
}
