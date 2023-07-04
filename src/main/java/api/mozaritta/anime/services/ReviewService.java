package api.mozaritta.anime.services;

import api.mozaritta.anime.dto.ReviewDTO;
import api.mozaritta.anime.entities.Review;

import java.text.ParseException;
import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews();

    Review createReview(String body, String imdbId);
    Review convertToEntity(ReviewDTO reviewDTO) throws ParseException;

    ReviewDTO convertToDto(Review review);
}
