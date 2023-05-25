package api.mozaritta.anime.services;

import api.mozaritta.anime.entities.Review;

public interface ReviewService {
    public Review createReview(String body, String imdbId);
}
