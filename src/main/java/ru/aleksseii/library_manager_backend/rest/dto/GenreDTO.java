package ru.aleksseii.library_manager_backend.rest.dto;

import lombok.*;
import ru.aleksseii.library_manager_backend.domain.Genre;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreDTO {

    private long id;

    private String name;

    public static GenreDTO toDTO(Genre genre) {
        return GenreDTO.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }

    public static Genre toDomainObject(GenreDTO genreDTO) {
        return Genre.builder()
                .id(genreDTO.getId())
                .name(genreDTO.getName())
                .build();
    }
}
