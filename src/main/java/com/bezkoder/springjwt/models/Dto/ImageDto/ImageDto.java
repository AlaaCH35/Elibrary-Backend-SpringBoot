package com.bezkoder.springjwt.models.Dto.ImageDto;

import com.bezkoder.springjwt.models.ImageData;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Builder
public class ImageDto {
    private Integer id;
    private String name;
    private String type;

    private byte[] imageData;


    public static ImageDto fromEntity(ImageData imageData) {

        return ImageDto.builder()
                .id(imageData.getId())
                .name(imageData.getName())
                .type(imageData.getType())
                .imageData(imageData.getImageData())
                .build();
    }



    public static  ImageData toEntity (ImageDto imageDto){
ImageData imageData1 =new ImageData();
           imageData1.setId(imageDto.getId());
           imageData1.setName(imageDto.getName());
           imageData1.setType(imageDto.getType());
           imageData1.setImageData(imageDto.getImageData());


        return imageData1;



    }







}