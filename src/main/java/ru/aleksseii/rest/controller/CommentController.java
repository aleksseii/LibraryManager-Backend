package ru.aleksseii.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.aleksseii.domain.Book;
import ru.aleksseii.domain.Comment;
import ru.aleksseii.rest.dto.CommentDTO;
import ru.aleksseii.service.BookService;
import ru.aleksseii.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final BookService bookService;

    @GetMapping(value = "/comment")
    public List<CommentDTO> getAllComments() {

        return commentService.getAll().stream()
                .map(CommentDTO::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/book/{book_id}/comment")
    public List<CommentDTO> getCommentsByBookId(@PathVariable(name = "book_id") long bookId) {

        return commentService.getByBookId(bookId).stream()
                .map(CommentDTO::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/comment", params = { "book_id", "content" })
    public CommentDTO insertComment(@RequestParam(name = "book_id") long bookId,
                                    @RequestParam(name = "content") String commentContent) {

        Book book = bookService.getById(bookId);
        Comment insertedComment = Comment.builder()
                .content(commentContent)
                .book(book)
                .build();

        commentService.insert(insertedComment);
        return CommentDTO.toDTO(insertedComment);
    }

    @PutMapping(value = "/comment/{id}", params = { "content" })
    public CommentDTO updateComment(@PathVariable(name = "id") long commentId,
                                    @RequestParam(name = "content") String newContent) {

        Comment updatedComment = commentService.update(commentId, newContent);
        return CommentDTO.toDTO(updatedComment);
    }

    @DeleteMapping(value = "/comment/{id}")
    public void deleteComment(@PathVariable(name = "id") long commentId) {

        commentService.deleteById(commentId);
    }
}
