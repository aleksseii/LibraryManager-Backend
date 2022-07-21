package ru.aleksseii.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aleksseii.domain.Comment;
import ru.aleksseii.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment insert(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getByBookId(long bookId) {
        return commentRepository.findByBookId(bookId);
    }

    @Override
    public Comment update(long commentId, String content) {

        commentRepository.updateCommentById(commentId, content);
        return Comment.builder()
                .id(commentId)
                .content(content)
                .build();
    }

    @Override
    public void deleteById(long commentId) {
        commentRepository.deleteById(commentId);
    }
}
