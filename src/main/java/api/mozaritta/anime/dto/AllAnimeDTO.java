package api.mozaritta.anime.dto;

import api.mozaritta.anime.entities.Anime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllAnimeDTO {
    private List<AnimeDTO> animeList;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private boolean isFirst;
    private boolean isLast;
    private boolean hasNext;
    private boolean hasPrevious;

    public AllAnimeDTO(Page<AnimeDTO> animePage) {
        this.setAnimeList(animePage.getContent());
        this.setTotalElements(animePage.getTotalElements());
        this.setTotalPages(animePage.getTotalPages());
        this.setCurrentPage(animePage.getNumber() + 1);
        this.setFirst(animePage.isFirst());
        this.setLast(animePage.isLast());
        this.setHasNext(animePage.hasNext());
        this.setHasPrevious(animePage.hasPrevious());
    }
}
