package api.mozaritta.anime.dto;

import api.mozaritta.anime.entities.AnimeSeason;
import api.mozaritta.anime.entities.Review;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnimeDTO {
    @Id
    private ObjectId id;
    @NotNull
    private String title;
    private String type;
    private String status;
    private String thumbnail;
    private String picture;
    private AnimeSeason animeSeason;

}
