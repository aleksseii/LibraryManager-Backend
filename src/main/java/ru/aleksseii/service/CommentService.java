package ru.aleksseii.service;

import ru.aleksseii.domain.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAll();

    List<Comment> getByBookId(long bookId);

    Comment insert(long bookId, String commentContent);

    Comment update(long commentId, String content);

    void deleteById(long commentId);
}
