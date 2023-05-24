package api.mozaritta.anime.services;

import api.mozaritta.anime.entities.Anime;
import api.mozaritta.anime.repositories.AnimeRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {
    @Autowired
    private AnimeRepository animeRepository;

    public List<Anime> getAllAnime(){
        return animeRepository.findAll();
    }

    public Optional<Anime> getAnimeById(ObjectId id){
        return animeRepository.findById(id);
    }


}
