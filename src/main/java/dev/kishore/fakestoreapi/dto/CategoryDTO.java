package dev.kishore.fakestoreapi.dto;

import dev.kishore.fakestoreapi.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.filters.RateLimitFilter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String title;
    private Long price;
    private String description;
    private String category;
    private String image;
    private RatingDTO rating;

    public Category toCategory() {
        Category category = new Category();
        category.setId(this.id);
        category.setTitle(this.title);
        category.setPrice(this.price);
        category.setDescription(this.description);
        category.setCategory(this.category);
        category.setImage(this.image);
        category.setRating(this.rating);
        return category;
    }
}
