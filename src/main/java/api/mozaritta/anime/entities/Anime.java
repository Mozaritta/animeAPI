package api.mozaritta.anime.entities;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "anime")
public class Anime {

    @Id
    private ObjectId id;
    @NotNull
    private String imdbId;
    @NotNull
    private String title;
    private String type;
    private Integer episodes;
    private String status;
    private String thumbnail;
    private String picture;
    private AnimeSeason animeSeason;
    private List<String> sources;
    private List<String> tags;
    @DocumentReference
    private List<Review> reviewsIds;

    public Anime(String title, String imdbId, Integer episodes){
        this.title = title;
        this.imdbId = imdbId;
        this.episodes = episodes;
    }
    
}
