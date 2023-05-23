package com.bezkoder.springjwt.models.Dto.Author;


import com.bezkoder.springjwt.models.Entity.Author;
import com.bezkoder.springjwt.models.Entity.Rating;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {

    private Integer id;


    private  String name;


    @Column(length = 1000)
    private  String bio;



    public static AuthorDto fromEntity(Author author) {

        return AuthorDto.builder()
                .id(author.getId())
                .bio(author.getBio())
                .name(author.getName())
                .build();

    }

    public static  Author toEntity (AuthorDto authorDto){
        Author author =new Author();
        author.setBio(authorDto.getBio());
        author.setId(authorDto.getId());
        author.setName(authorDto.getName());


        return author;



    }
}
