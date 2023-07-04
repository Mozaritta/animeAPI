package api.mozaritta.anime.controllers;

import api.mozaritta.anime.entities.Anime;
import api.mozaritta.anime.services.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/v2")
public class RandomController {


    public RandomController(
            AnimeService animeService
    ){
        this.animeService = animeService;
    }

    @GetMapping("/random")
    public Integer randomNumber(){
        return new Random().nextInt(50);
    }


    @Autowired
    private final AnimeService animeService;
    @RequestMapping("/put")
    public void addTopTalent(@RequestBody Anime topTalentData) {
        boolean nameNonExistentOrHasInvalidLength =
                Optional.ofNullable(topTalentData)
                        .map(Anime::getTitle)
                        .map(title -> title.length() == 10)
                        .orElse(true);

        if (nameNonExistentOrHasInvalidLength) {
            // throw some exception
        }

        animeService.save(topTalentData);
    }

    // test functions to delete afterwards
    //    @GetMapping("/greet")
    //    public GreetResponse testFunction(){
    //        return new GreetResponse("Hello");
    //    }

    //    record GreetResponse(
//            String greet
//    ){}
}
