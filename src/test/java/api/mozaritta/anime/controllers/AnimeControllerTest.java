package api.mozaritta.anime.controllers;

import api.mozaritta.anime.entities.Anime;
import api.mozaritta.anime.repositories.AnimeRepository;
import api.mozaritta.anime.services.ReviewService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AnimeControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    AnimeRepository animeRepository;
    @Autowired
    ReviewService reviewService;

//    @Container
    final static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0").withExposedPorts(27017);

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeEach
    void setUp(){
        String animeName1 = "Boku No Hero Academia";
        animeRepository.deleteAll();
        List<Anime> anime = new ArrayList<>();
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        anime.add(new Anime(animeName1, new String(array, StandardCharsets.UTF_8), "24"));
        anime.add(new Anime("One Punch Man", new String(array, StandardCharsets.UTF_8), "24"));
        anime.add(new Anime("Mashle", new String(array, StandardCharsets.UTF_8), "24"));
        anime.add(new Anime("SpyXFamily", new String(array, StandardCharsets.UTF_8), "24"));
        anime.add(new Anime("One Piece", new String(array, StandardCharsets.UTF_8), "24"));
        anime.add(new Anime("Naruto", new String(array, StandardCharsets.UTF_8), "24"));
        animeRepository.saveAll(anime);

        new Random().nextBytes(array);
        String imdb = animeRepository.findAnimeByTitle(animeName1).get().getImdbId();
        reviewService.createReview("love this anime!", imdb);

    }

    @Test
    void shouldGetAnime() throws Exception{
        mvc.perform(get("/api/v1/anime/all_anime").param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['data'].['totalElements']", CoreMatchers.equalTo(6)))
                .andExpect(jsonPath("$.['data'].['currentPage']", CoreMatchers.equalTo(1)))
                .andExpect(jsonPath("$.['data'].['first']", CoreMatchers.equalTo(true)))
                .andExpect(jsonPath("$.['data'].['last']", CoreMatchers.equalTo(false)))
                .andExpect(jsonPath("$.['data'].['hasPrevious']", CoreMatchers.equalTo(false)))
                .andReturn()
        ;
    }
    @Test
    void shouldGetReview() throws Exception{
        mvc.perform(get("/api/v1/anime/all_reviews").param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['data'].['totalElements']", CoreMatchers.equalTo(1)))
                .andExpect(jsonPath("$.['data'].['currentPage']", CoreMatchers.equalTo(1)))
                .andExpect(jsonPath("$.['data'].['first']", CoreMatchers.equalTo(true)))
                .andExpect(jsonPath("$.['data'].['hasPrevious']", CoreMatchers.equalTo(false)))
                .andReturn()
        ;
    }

}