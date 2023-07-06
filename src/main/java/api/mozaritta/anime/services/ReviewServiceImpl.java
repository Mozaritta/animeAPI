package api.mozaritta.anime.services;

import api.mozaritta.anime.dto.AllReviewsDTO;
import api.mozaritta.anime.dto.ReviewDTO;
import api.mozaritta.anime.entities.Anime;
import api.mozaritta.anime.entities.Review;
import api.mozaritta.anime.repositories.AnimeRepository;
import api.mozaritta.anime.repositories.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    @Bean
    public ModelMapper reviewModelMapper() {
        return new ModelMapper();
    }
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public AllReviewsDTO getAllReviews(@RequestBody Integer page){
        int pageNumber = page < 1 ? 0 : page-1;
        Pageable pageable = PageRequest.of(
                pageNumber,
                3,
                Sort.Direction.DESC,
                "title"
        );
        return new AllReviewsDTO(reviewRepository.findAll(pageable));
    }

    public Review createReview(String body, String imdbId){
        Review review = new Review(body);
        review = reviewRepository.insert(review);
        mongoTemplate.update(Anime.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review.getId()))
                .first();
        return review;
    }

    @Override
    public Review convertToEntity(ReviewDTO reviewDTO) throws ParseException {

        Review review = reviewModelMapper().map(reviewDTO, Review.class);

        if (reviewDTO.getImdbID() != null) {
            Review oldReview = reviewRepository.findById(reviewDTO.getId()).get();
            review.setBody(oldReview.getBody());
        }
        return review;
    }

    @Override
    public ReviewDTO convertToDto(Review review) {
        ReviewDTO reviewDTO = reviewModelMapper().map(review, ReviewDTO.class);
        return reviewDTO;
    }
}
