package dev.kishore.fakestoreapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {
    private double rate;
    private Long count;
    public RatingDTO toRatingDTO(){
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setRate(rate);
        ratingDTO.setCount(count);
        return ratingDTO;
    }
}
