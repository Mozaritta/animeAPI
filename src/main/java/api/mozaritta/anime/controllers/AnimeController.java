package api.mozaritta.anime.controllers;

import api.mozaritta.anime.configurations.Bucket4JConfiguration;
import api.mozaritta.anime.dto.AnimeDTO;
import api.mozaritta.anime.dto.ReviewDTO;
import api.mozaritta.anime.entities.Anime;
import api.mozaritta.anime.entities.AnimeSeason;
import api.mozaritta.anime.entities.Review;
import api.mozaritta.anime.responses.ResponseHandler;
import api.mozaritta.anime.services.AnimeService;
import api.mozaritta.anime.services.ReviewService;

import io.github.bucket4j.Bucket;
import org.apache.commons.lang.RandomStringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/anime")
public class AnimeController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public AnimeController(
            Bucket4JConfiguration bucket4JConfiguration, AnimeService animeService,
            ReviewService reviewService
    ){
        this.bucket4JConfiguration = bucket4JConfiguration;
        this.animeService = animeService;
        this.reviewService = reviewService;
        this.bucket = bucket4JConfiguration.bucketConfig();

    }
    @Autowired
    private Bucket4JConfiguration bucket4JConfiguration;
    private Bucket bucket;

    @Autowired
    private final AnimeService animeService;
    @Autowired
    private final ReviewService reviewService;

    private final String message = "Too Many Requests, please try in a moment";

    @GetMapping("all_anime")
    public ResponseEntity<ResponseHandler> allAnime(Integer page){
        if(this.bucket.tryConsume(1)){
//            LOG.info(animeService.getAllAnime(page).toString());
//            LOG.info("Getting all anime.");
            return new ResponseEntity<>(
                    new ResponseHandler(
                            200,
                            animeService.getAllAnime(page),
                            "Anime retrieved"),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(new ResponseHandler(400, new ArrayList<>(), this.message), HttpStatus.TOO_MANY_REQUESTS);
    }

    @GetMapping("all_reviews")
    public ResponseEntity<ResponseHandler> allReviews(Integer page){
        if(this.bucket.tryConsume(1)){
            LOG.info("Getting all reviews.");
            return new ResponseEntity<>(
                    new ResponseHandler(
                            200,
                            reviewService.getAllReviews(page),
                            "Reviews retrieved"
                    ),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(new ResponseHandler(400, new ArrayList<>(), this.message), HttpStatus.TOO_MANY_REQUESTS);
    }

    @GetMapping("get_first_anime")
    public ResponseEntity<ResponseHandler> getRandom() {
        if(this.bucket.tryConsume(1)){
//            Anime anime = new ObjectMapper().readValue(json, Anime.class);
            ArrayList<Object> list = new ArrayList<>();
            list.add(animeService.getFirst());
            ResponseHandler r = new ResponseHandler(200, list, "");
            return new ResponseEntity<>(r, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseHandler(400, new ArrayList<>(),"Too Many Requests, please try in a moment"), HttpStatus.TOO_MANY_REQUESTS);

    }

//    @GetMapping("/{imdbId}")
//    public ResponseEntity<Optional<Anime>> getAnimeById(@PathVariable String imdbId){
//        return new ResponseEntity<Optional<Anime>>(animeService.getAnimeByImdbId(imdbId), HttpStatus.OK);
//    }

    @PostMapping("test")
    public ResponseEntity<ResponseHandler> test(@RequestBody Anime anime){
        if(this.bucket.tryConsume(1)) {
            ArrayList<Object> list = new ArrayList<>();
            list.add(anime);
            return new ResponseEntity<>(new ResponseHandler(200, list, ""), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseHandler(400, new ArrayList<>(), "test"), HttpStatus.TOO_MANY_REQUESTS);
    }

    @PostMapping("/add_review")
    public ResponseEntity<ResponseHandler> createReview(@RequestBody ReviewDTO reviewDTO) {
        // ToDo: delete this part once the responses are well structured - Map<String , String> payload
        if(this.bucket.tryConsume(1)) {
            boolean nameNonExistentOrHasInvalidLength =
                    Optional.ofNullable(reviewDTO)
                            .map(ReviewDTO::getBody)
                            .map(title -> title.length() == 2)
                            .orElse(true);

            if (nameNonExistentOrHasInvalidLength) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Review response = reviewService.createReview(reviewDTO.getBody(), reviewDTO.getImdbID());
            ArrayList<Object> list = new ArrayList<>();
            list.add(response);
            return new ResponseEntity<>(new ResponseHandler(200, list, "Review added"), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ResponseHandler(400, new ArrayList<>(), "test"), HttpStatus.TOO_MANY_REQUESTS);
    }

    @PostMapping("/add_anime")
    public ResponseEntity<ResponseHandler> createAnime(@RequestBody AnimeDTO request){
        String title      = request.getTitle();
        String type       = request.getType();
        String imdbId     = RandomStringUtils.randomAlphanumeric(24);
        Integer year      = request.getYear();
        String season     = request.getSeason();
        String status     = request.getStatus();
        String review     = request.getReview();
        String thumbnail  = request.getThumbnail();
        Integer episodes  = request.getEpisodes();

        Anime anime = new Anime();
        anime.setTitle(title);
        anime.setImdbId(imdbId);
        anime.setEpisodes(episodes);
        anime.setStatus(status);
        anime.setType(type);
        anime.setThumbnail(thumbnail);
        anime.setAnimeSeason(new AnimeSeason(season, year));
        Review newReview = new Review(review);
        reviewService.save(newReview);
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(newReview);
        anime.setReviewsIds(reviewList);
        animeService.save(anime);
        ArrayList<Object> list = new ArrayList<>();
        list.add(anime);
        return new ResponseEntity<>(new ResponseHandler(200, list, "Anime added"),HttpStatus.CREATED);
    }
}
