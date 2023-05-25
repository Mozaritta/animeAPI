package api.mozaritta.anime.services;

import api.mozaritta.anime.entities.Anime;
import api.mozaritta.anime.repositories.AnimeRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeServiceImpl implements AnimeService{
    @Autowired
    private AnimeRepository animeRepository;

    @Override
    public List<Anime> getAllAnime(){
//        System.out.println(animeRepository.findAll());
        return animeRepository.findAll();
    }

    @Override
    public ObjectId save(Anime anime) {
        return animeRepository.save(anime).getId();
    }

    //    public Optional<Anime> getAnimeByImdbId(String imdbId){
//        return animeRepository.findAnimeByImdbId(imdbId);
//    }
}
