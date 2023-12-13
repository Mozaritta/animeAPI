package api.mozaritta.anime.controllers;

import api.mozaritta.anime.configurations.Bucket4JConfiguration;
import api.mozaritta.anime.dto.AnimeDTO;
import api.mozaritta.anime.dto.ReviewDTO;
import api.mozaritta.anime.entities.Anime;
import api.mozaritta.anime.entities.Review;
import api.mozaritta.anime.requests.AnimeRequest;
import api.mozaritta.anime.responses.ResponseHandler;
import api.mozaritta.anime.services.AnimeService;
import api.mozaritta.anime.services.ReviewService;

import io.github.bucket4j.Bucket;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/api/v1/anime")
public class AnimeController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    /* final private Bucket4JConfiguration bucket4JConfiguration; */
    final private Bucket bucket;

    public AnimeController(
            Bucket4JConfiguration bucket4JConfiguration, AnimeService animeService,
            ReviewService reviewService
    ){
//        this.bucket4JConfiguration = bucket4JConfiguration;
        this.animeService = animeService;
        this.reviewService = reviewService;
        this.bucket = bucket4JConfiguration.bucketConfig();

    }

    @Autowired
    private final AnimeService animeService;
    @Autowired
    private final ReviewService reviewService;

    private final String message = "Too Many Requests, please try in a moment";

    @GetMapping("allAnime")
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

    @GetMapping("allReviews")
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

    @GetMapping("getFirstAnime")
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

    @PostMapping("/addReview")
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
            return new ResponseEntity<>(new ResponseHandler(201, list, "Review added"), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ResponseHandler(400, new ArrayList<>(), "test"), HttpStatus.TOO_MANY_REQUESTS);
    }

    @PostMapping("/getAnimeById")
    public ResponseEntity<ResponseHandler> getAnimeById(@RequestBody ObjectId request){
        Optional<Anime> anime = this.animeService.findById(request);
        if(anime.isEmpty()) {
            return new ResponseEntity<>(new ResponseHandler(404, false, "No anime found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseHandler(302, anime, "Anime found"),HttpStatus.FOUND);
    }

    @PostMapping("/addAnime")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseHandler> createAnime(@RequestBody @Valid AnimeRequest request){
        AnimeDTO animeDTO = this.animeService.createAnime(request);
        return new ResponseEntity<>(new ResponseHandler(201, animeDTO, "Anime added"),HttpStatus.CREATED);
    }
    @PostMapping("/updateAnime")
    public ResponseEntity<ResponseHandler> updateAnime(@RequestBody AnimeRequest request){
        if(request.getId() == null) {
            return new ResponseEntity<>(new ResponseHandler(404, false, "No id provided"), HttpStatus.NOT_FOUND);
        }
        Optional<Anime> optionalAnime = this.animeService.findById(request.getId());
        boolean exist = optionalAnime.isPresent();
        if(!exist){
            return new ResponseEntity<>(new ResponseHandler(404, animeService.getFirst(), "Anime not found"), HttpStatus.NOT_FOUND);
        }

        Anime anime = this.animeService.convertRequestToDTO(request);
        LOG.error(request.getTitle(), anime.getTitle());
        animeService.save(this.animeService.addReviewToAnime(request, anime));
        return new ResponseEntity<>(new ResponseHandler(302, anime.getTitle(), "Anime updated"), HttpStatus.FOUND);
    }

    @DeleteMapping("/deleteAnime")
    public ResponseEntity<ResponseHandler> deleteAnime(@RequestBody ObjectId id){

        boolean exist = this.animeService.findById(id).isPresent();
        if(!exist){
            return new ResponseEntity<>(new ResponseHandler(404, false, "No anime found or false id"), HttpStatus.NOT_FOUND);
        }
        this.animeService.deleteAnimeById(id);
        return new ResponseEntity<>(new ResponseHandler(200, true, "Anime deleted"), HttpStatus.OK);
    }

}
