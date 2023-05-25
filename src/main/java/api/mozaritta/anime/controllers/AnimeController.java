package api.mozaritta.anime.controllers;

import api.mozaritta.anime.entities.Anime;
import api.mozaritta.anime.entities.Review;
import api.mozaritta.anime.services.AnimeService;
import api.mozaritta.anime.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/anime")
public class AnimeController {
    @Autowired
    private AnimeService animeService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Anime>> allAnime(){
        return new ResponseEntity<List<Anime>>(animeService.getAllAnime(), HttpStatus.OK);
    }

//    @GetMapping("/{imdbId}")
//    public ResponseEntity<Optional<Anime>> getAnimeById(@PathVariable String imdbId){
//        return new ResponseEntity<Optional<Anime>>(animeService.getAnimeByImdbId(imdbId), HttpStatus.OK);
//    }

    @PostMapping("/add_review")
    public ResponseEntity<Review> createReview(@RequestBody Map<String , String> payload){
        String bodyReview = payload.get("body");
        String imdbId = payload.get("imdb");
        Review response = reviewService.createReview(bodyReview, imdbId);
        return new ResponseEntity<Review>(response, HttpStatus.CREATED);
    }
}
