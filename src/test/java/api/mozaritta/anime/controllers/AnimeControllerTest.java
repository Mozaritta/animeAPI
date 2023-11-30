package api.mozaritta.anime.controllers;

import api.mozaritta.anime.entities.Anime;
import api.mozaritta.anime.repositories.AnimeRepository;
import api.mozaritta.anime.services.ReviewService;
import org.apache.commons.lang.RandomStringUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger LOGGER = LoggerFactory.getLogger(AnimeControllerTest.class);
    @BeforeEach
    void setUp(){
        String animeName1 = "Boku No Hero Academia";
        animeRepository.deleteAll();
        List<Anime> anime = new ArrayList<>();
        //Test logging with colors
        LOGGER.debug("\u001B[34m" + "Debug - {}" + "\u001B[34m", animeName1);
        LOGGER.info("Test logging with colors" + "\u001B[34m");
        LOGGER.info("\u001B[34m" + "Info  - {}" + "\u001B[33m", animeName1);
        LOGGER.warn("\u001B[33m" + "Warn  - {}" + "\u001B[31m", animeName1);
        LOGGER.error("\u001B[31m" + "Error - {}" + "\u001B[38m", animeName1);

        Integer episode = 24;
        anime.add(new Anime(animeName1, RandomStringUtils.randomAlphanumeric(20), episode));
        anime.add(new Anime("One Punch Man", RandomStringUtils.randomAlphanumeric(20), episode));
        anime.add(new Anime("Mashle", RandomStringUtils.randomAlphanumeric(20), episode));
        anime.add(new Anime("SpyXFamily", RandomStringUtils.randomAlphanumeric(20), episode));
        anime.add(new Anime("One Piece", RandomStringUtils.randomAlphanumeric(20), episode));
        anime.add(new Anime("Naruto", RandomStringUtils.randomAlphanumeric(20), episode));
        animeRepository.saveAll(anime);
        LOGGER.info("\u001B[34m" + "Info  - {}" + "\u001B[33m", anime.get(2));
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