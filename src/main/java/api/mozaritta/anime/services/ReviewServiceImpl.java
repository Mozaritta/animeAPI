package api.mozaritta.anime.services;

import api.mozaritta.anime.entities.Anime;
import api.mozaritta.anime.entities.Review;
import api.mozaritta.anime.repositories.AnimeRepository;
import api.mozaritta.anime.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String body, String imdbId){
        Review review = new Review(body);
        review = reviewRepository.insert(review);
        mongoTemplate.update(Anime.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();
        return review;
    }
}
