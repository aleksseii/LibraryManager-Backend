package ru.aleksseii.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aleksseii.domain.Book;
import ru.aleksseii.domain.Comment;
import ru.aleksseii.repository.BookRepository;
import ru.aleksseii.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getByBookId(long bookId) {
        return commentRepository.findByBookId(bookId);
    }

    @Override
    public Comment insert(long bookId, String commentContent) {

        Book book = bookRepository.getReferenceById(bookId);

        Comment comment = Comment.builder()
                .content(commentContent)
                .book(book)
                .build();

        return commentRepository.save(comment);
    }

    @Override
    public Comment update(long commentId, String content) {

        Book book = commentRepository.getReferenceById(commentId).getBook();
        Comment newComment = Comment.builder()
                .id(commentId)
                .content(content)
                .book(book)
                .build();
        return commentRepository.save(newComment);
    }

    @Override
    public void deleteById(long commentId) {
        commentRepository.deleteById(commentId);
    }
}
