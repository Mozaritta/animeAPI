package api.mozaritta.anime.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "animeSeason")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimeSeason {
    @Id
    private Integer id;
    private String season;
    private Integer year;

    public AnimeSeason(String season, Integer year){
        this.season = season;
        this.year = year;
    }
}
