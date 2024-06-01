package dev.kishore.fakestoreapi.model;

import dev.kishore.fakestoreapi.dto.RatingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Long id;
    private String title;
    private Long price;
    private String description;
    private String category;
    private String image;
    private RatingDTO rating;
}
