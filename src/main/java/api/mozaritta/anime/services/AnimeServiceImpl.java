package api.mozaritta.anime.services;

import api.mozaritta.anime.dto.AllAnimeDTO;
import api.mozaritta.anime.dto.AnimeDTO;
import api.mozaritta.anime.entities.Anime;
import api.mozaritta.anime.entities.AnimeSeason;
import api.mozaritta.anime.entities.Review;
import api.mozaritta.anime.repositories.AnimeRepository;
import api.mozaritta.anime.repositories.ReviewRepository;
import api.mozaritta.anime.requests.AnimeRequest;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimeServiceImpl implements AnimeService{

    @Autowired
    @Bean
    public ModelMapper animeModelMapper() {
        return new ModelMapper();
    }
    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public AllAnimeDTO getAllAnime(Integer page){
//        System.out.println(animeRepository.findAll());
        int pageNumber = page < 1 ? 1 : page-1;
        Pageable pageable = PageRequest.of(pageNumber, 3, Sort.Direction.DESC, "title");
        return new AllAnimeDTO(animeRepository.findAll(pageable).map(this::convertToDto));
    }

    @Override
    @Transactional(readOnly = true)
    public Anime getFirst(){
        return animeRepository.findAnimeByTitle("Cinderella Monogatari").get();
    }

    @Override
    @Transactional(readOnly = true)
    public ObjectId save(Anime anime) {
        return animeRepository.save(anime).getId();
    }

    //    public Optional<Anime> getAnimeByImdbId(String imdbId){
//        return animeRepository.findAnimeByImdbId(imdbId);
//    }
    @Transactional(readOnly = true)
    public Optional<Anime> findById(ObjectId id) {
        return animeRepository.findById(id);
                //new RuntimeException("Anime not found"));
    }
    @Override
    @Transactional(readOnly = true)
    public Anime convertToEntity(AnimeDTO animeDTO){

        Anime anime = animeModelMapper().map(animeDTO, Anime.class);

        if (animeDTO.getId() != null) {
            if(this.findById(animeDTO.getId()).isPresent()){
                Anime oldAnime = this.findById(animeDTO.getId()).get();
                anime.setImdbId(oldAnime.getImdbId());
                anime.setTitle(oldAnime.getTitle());
            }
        }
        return anime;
    }

    @Override
    @Transactional(readOnly = true)
    public AnimeDTO convertToDto(Anime anime) {
        return animeModelMapper().map(anime, AnimeDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteAnimeById(ObjectId id){
        animeRepository.deleteById(id);
    }

    public AnimeDTO createAnime(AnimeRequest animeRequest){
        Anime anime = animeModelMapper().map(animeRequest, Anime.class);
        this.animeRepository.save(this.addReviewToAnime(animeRequest, anime));
        return convertToDto(anime);
    }

    public Anime convertRequestToDTO(AnimeRequest request){
        Anime anime = findById(request.getId()).get();
        return animeModelMapper().map(request, Anime.class);
    }

    public Anime addReviewToAnime(AnimeRequest request, Anime anime){

        if(request.getReview() != null){
            Review newReview = new Review(request.getReview());
            this.reviewRepository.save(newReview);
            List<Review> reviewList = new ArrayList<>();
            reviewList.add(newReview);
            anime.setReviewsIds(reviewList);
            this.animeRepository.save(anime);
        }

        return anime;
    }

}
