package api.mozaritta.anime.dto;

import api.mozaritta.anime.entities.Review;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
public class AllReviewsDTO {
    private List<Review> reviewList;
    private int totalElements;
    private int totalPages;
    private int currentPage;
    private boolean isFirst;
    private boolean isLast;
    private boolean hasNext;
    private boolean hasPrevious;

    public AllReviewsDTO(Page<Review> reviewPage) {
        this.setReviewList(reviewPage.getContent());
        this.setTotalElements(reviewPage.getNumberOfElements());
        this.setTotalPages(reviewPage.getTotalPages());
        this.setCurrentPage(reviewPage.getNumber()+1);
        this.setFirst(reviewPage.isFirst());
        this.setLast(reviewPage.isLast());
        this.setHasNext(reviewPage.hasNext());
        this.setHasPrevious(reviewPage.hasPrevious());
    }
}
