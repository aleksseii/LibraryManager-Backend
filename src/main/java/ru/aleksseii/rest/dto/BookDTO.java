package ru.aleksseii.rest.dto;

import lombok.*;
import ru.aleksseii.domain.Book;
import ru.aleksseii.domain.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {

    private long id;

    private String name;

    private AuthorDTO authorDTO;

    private GenreDTO genreDTO;

    private List<CommentDTO> commentDTOList;

    public static BookDTO toDTO(Book book) {

        List<Comment> comments = book.getComments();
        List<CommentDTO> newCommentDTOList = new ArrayList<>();

        if (comments != null) {
            comments.forEach(c -> newCommentDTOList.add(CommentDTO.toDTO(c)));
        }

        return BookDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .authorDTO(AuthorDTO.toDTO(book.getAuthor()))
                .genreDTO(GenreDTO.toDTO(book.getGenre()))
                .commentDTOList(newCommentDTOList)
                .build();
    }

    public static Book toDomainObjectWithoutComments(BookDTO bookDTO) {
        return Book.builder()
                .id(bookDTO.getId())
                .name(bookDTO.getName())
                .author(AuthorDTO.toDomainObject(bookDTO.getAuthorDTO()))
                .genre(GenreDTO.toDomainObject(bookDTO.getGenreDTO()))
                .comments(new ArrayList<>())
                .build();
    }
}
