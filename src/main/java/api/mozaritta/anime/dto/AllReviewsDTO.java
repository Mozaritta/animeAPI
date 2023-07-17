package api.mozaritta.anime.dto;

import api.mozaritta.anime.entities.Review;
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
public class AllReviewsDTO {
    private List<ReviewDTO> reviewList;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private boolean isFirst;
    private boolean isLast;
    private boolean hasNext;
    private boolean hasPrevious;

    public AllReviewsDTO(Page<ReviewDTO> reviewPage) {
        this.setReviewList(reviewPage.getContent());
        this.setTotalElements(reviewPage.getTotalElements());
        this.setTotalPages(reviewPage.getTotalPages());
        this.setCurrentPage(reviewPage.getNumber() + 1);
        this.setFirst(reviewPage.isFirst());
        this.setLast(reviewPage.isLast());
        this.setHasNext(reviewPage.hasNext());
        this.setHasPrevious(reviewPage.hasPrevious());
    }
}
