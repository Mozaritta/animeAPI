package api.mozaritta.anime.dto;

import api.mozaritta.anime.entities.Anime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
public class AllAnimeDTO {
    private List<Anime> animeList;
    private int totalElements;
    private int totalPages;
    private int currentPage;
    private boolean isFirst;
    private boolean isLast;
    private boolean hasNext;
    private boolean hasPrevious;

    public AllAnimeDTO(Page<Anime> animePage) {
        this.setAnimeList(animePage.getContent());
        this.setTotalElements(animePage.getNumberOfElements());
        this.setTotalPages(animePage.getTotalPages());
        this.setCurrentPage(animePage.getNumber()+1);
        this.setFirst(animePage.isFirst());
        this.setLast(animePage.isLast());
        this.setHasNext(animePage.hasNext());
        this.setHasPrevious(animePage.hasPrevious());
    }
}
