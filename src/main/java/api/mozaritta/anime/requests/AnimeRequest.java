package api.mozaritta.anime.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class AnimeRequest {
    private ObjectId id;
    @NotEmpty(message="Title should not be empty")
    private String title;
    @NotEmpty(message="Type should not be empty")
    private String type;
    @NotEmpty(message="Status should not be empty")
    private String status;
    @NotEmpty(message="Thumbnail should not be empty")
    private String thumbnail;
    @NotEmpty(message="Picture should not be empty")
    private String picture;
    @NotEmpty(message="Season should not be empty")
    private String season;
    @NotEmpty(message="Year should not be empty")
    private Integer year;
    private String review;
    @NotEmpty(message="Episodes should not be empty")
    private Integer episodes;
}
