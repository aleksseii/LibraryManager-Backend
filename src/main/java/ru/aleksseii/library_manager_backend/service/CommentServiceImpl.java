package ru.aleksseii.library_manager_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aleksseii.library_manager_backend.domain.Book;
import ru.aleksseii.library_manager_backend.domain.Comment;
import ru.aleksseii.library_manager_backend.repository.BookRepository;
import ru.aleksseii.library_manager_backend.repository.CommentRepository;

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
