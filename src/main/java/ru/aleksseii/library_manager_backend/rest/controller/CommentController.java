package ru.aleksseii.library_manager_backend.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.aleksseii.library_manager_backend.domain.Comment;
import ru.aleksseii.library_manager_backend.service.CommentService;
import ru.aleksseii.library_manager_backend.rest.dto.CommentDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

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

        Comment insertedComment = commentService.insert(bookId, commentContent);
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
