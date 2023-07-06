package api.mozaritta.anime.services;

import api.mozaritta.anime.dto.AllReviewsDTO;
import api.mozaritta.anime.dto.ReviewDTO;
import api.mozaritta.anime.entities.Review;

import java.text.ParseException;
import java.util.List;

public interface ReviewService {
    AllReviewsDTO getAllReviews(Integer page);

    Review createReview(String body, String imdbId);
    Review convertToEntity(ReviewDTO reviewDTO) throws ParseException;

    ReviewDTO convertToDto(Review review);
}
