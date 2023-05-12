package com.bezkoder.springjwt.models.Dto.Rating;


import com.bezkoder.springjwt.models.Entity.Rating;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingDto {

    private Integer id;

    private  String stars;


    private  String ratingNumber;



    public static RatingDto fromEntity(Rating rating) {

        return RatingDto.builder()
                .stars(rating.getStars())
                .ratingNumber(rating.getRatingNumber())
                .build();

    }

    public static  Rating toEntity (RatingDto ratingDto){
        Rating rating =new Rating();
        rating.setRatingNumber(ratingDto.getRatingNumber());
        rating.setStars(ratingDto.getStars());

        return rating;



    }
}
