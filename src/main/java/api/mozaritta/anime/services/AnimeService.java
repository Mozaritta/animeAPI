package api.mozaritta.anime.services;

import api.mozaritta.anime.dto.AnimeDTO;
import api.mozaritta.anime.entities.Anime;
import org.bson.types.ObjectId;

import java.text.ParseException;
import java.util.List;

public interface AnimeService {


    List<Anime> getAllAnime();

    Anime getFirst();

    ObjectId save(Anime anime);

    Anime convertToEntity(AnimeDTO animeDTO) throws ParseException;

    AnimeDTO convertToDto(Anime anime);
}
