package api.mozaritta.anime.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ReviewDTO {
    @Id
    private ObjectId id;
    @NotNull
    private String body;
    @NotNull
    private String imdbID;
}
