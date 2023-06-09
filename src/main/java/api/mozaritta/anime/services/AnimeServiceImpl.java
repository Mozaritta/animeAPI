package api.mozaritta.anime.services;

import api.mozaritta.anime.dto.AllAnimeDTO;
import api.mozaritta.anime.dto.AnimeDTO;
import api.mozaritta.anime.entities.Anime;
import api.mozaritta.anime.repositories.AnimeRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class AnimeServiceImpl implements AnimeService{

    @Autowired
    @Bean
    public ModelMapper animeModelMapper() {
        return new ModelMapper();
    }
    @Autowired
    private AnimeRepository animeRepository;

    @Override
    public AllAnimeDTO getAllAnime(Integer page){
//        System.out.println(animeRepository.findAll());
        int pageNumber = page < 1 ? 0 : page-1;
        Pageable pageable = PageRequest.of(pageNumber, 3, Sort.Direction.DESC, "title");
        return new AllAnimeDTO(animeRepository.findAll(pageable));
    }

    @Override
    public Anime getFirst(){
        return animeRepository.findAnimeByTitle("Cinderella Monogatari").get();
    }

    @Override
    public ObjectId save(Anime anime) {
        return animeRepository.save(anime).getId();
    }

    //    public Optional<Anime> getAnimeByImdbId(String imdbId){
//        return animeRepository.findAnimeByImdbId(imdbId);
//    }

    @Override
    public Anime convertToEntity(AnimeDTO animeDTO) throws ParseException {

        Anime anime = animeModelMapper().map(animeDTO, Anime.class);

        if (animeDTO.getId() != null) {
            if(animeRepository.findById(animeDTO.getId()).isPresent()){
                Anime oldAnime = animeRepository.findById(animeDTO.getId()).get();
                anime.setImdbId(oldAnime.getImdbId());
                anime.setTitle(oldAnime.getTitle());
            }
        }
        return anime;
    }

    @Override
    public AnimeDTO convertToDto(Anime anime) {
        return animeModelMapper().map(anime, AnimeDTO.class);
    }
}
