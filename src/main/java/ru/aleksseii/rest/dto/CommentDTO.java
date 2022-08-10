package ru.aleksseii.rest.dto;

import lombok.*;
import ru.aleksseii.domain.Book;
import ru.aleksseii.domain.Comment;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private long id;

    private String content;

    public static CommentDTO toDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .build();
    }

    public static Comment toDomainObject(CommentDTO commentDTO, Book toBook) {
        return Comment.builder()
                .id(commentDTO.getId())
                .content(commentDTO.getContent())
                .book(toBook)
                .build();
    }
}
