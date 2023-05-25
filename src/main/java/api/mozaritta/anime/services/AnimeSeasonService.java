package api.mozaritta.anime.services;

import api.mozaritta.anime.entities.AnimeSeason;
import org.springframework.stereotype.Service;

@Service
public class AnimeSeasonService {
    public AnimeSeason createAnimeSeason(String season, Integer year){
        return new AnimeSeason();
    }
}
