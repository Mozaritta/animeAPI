package api.mozaritta.anime.services;

import api.mozaritta.anime.dto.AllAnimeDTO;
import api.mozaritta.anime.dto.AnimeDTO;
import api.mozaritta.anime.entities.Anime;
import org.bson.types.ObjectId;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface AnimeService {


    AllAnimeDTO getAllAnime(Integer page);

    Anime getFirst();

    ObjectId save(Anime anime);

    public Optional<Anime> findById(ObjectId id);
    Anime convertToEntity(AnimeDTO animeDTO);

    AnimeDTO convertToDto(Anime anime);

    void deleteAnimeById(ObjectId id);
}
