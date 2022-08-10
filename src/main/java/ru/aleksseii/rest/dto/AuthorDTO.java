package ru.aleksseii.rest.dto;

import lombok.*;
import ru.aleksseii.domain.Author;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDTO {

    private long id;

    private String name;

    public static AuthorDTO toDTO(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }

    public static Author toDomainObject(AuthorDTO authorDTO) {
        return Author.builder()
                .id(authorDTO.getId())
                .name(authorDTO.getName())
                .build();
    }
}
