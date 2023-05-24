package api.mozaritta.anime.controllers;

import api.mozaritta.anime.entities.Anime;
import api.mozaritta.anime.services.AnimeService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/anime")
public class AnimeController {
    @Autowired
    private AnimeService animeService;

    @GetMapping
    public ResponseEntity<List<Anime>> allAnime(){
        return new ResponseEntity<List<Anime>>(animeService.getAllAnime(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Anime>> getAnimeById(@PathVariable ObjectId id){
        return new ResponseEntity<Optional<Anime>>(animeService.getAnimeById(id), HttpStatus.OK);
    }
}
