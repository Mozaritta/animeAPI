package api.mozaritta.anime.services;

import api.mozaritta.anime.entities.Anime;
import org.bson.types.ObjectId;

import java.util.List;

public interface AnimeService {

    public List<Anime> getAllAnime();

    public ObjectId save(Anime anime);


}
