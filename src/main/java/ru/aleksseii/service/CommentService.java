package ru.aleksseii.service;

import ru.aleksseii.domain.Comment;

import java.util.List;

public interface CommentService {

    Comment insert(Comment comment);

    List<Comment> getAll();

    List<Comment> getByBookId(long bookId);

    Comment update(long commentId, String content);

    void deleteById(long commentId);
}
