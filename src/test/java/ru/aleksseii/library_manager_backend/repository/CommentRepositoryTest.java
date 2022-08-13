package ru.aleksseii.library_manager_backend.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.aleksseii.library_manager_backend.domain.Book;
import ru.aleksseii.library_manager_backend.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@DisplayName("class CommentRepository")
@DataJpaTest
class CommentRepositoryTest {

    private static final long EXISTING_COMMENT_COUNT = 6L;

    private static final long EXISTING_ID1 = 1L;
    private static final long EXISTING_ID2 = 2L;

    private static final String EXISTING_CONTENT1 = "very very very good book 1";
    private static final String EXISTING_CONTENT2 = "very very very bad book 1";

    private static final String EXISTING_BOOK_NAME1 = "First book name";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Should get all comments")
    void shouldGetAllComments() {

        Assertions.assertThat(commentRepository.findAll().size()).isEqualTo(EXISTING_COMMENT_COUNT);
    }

    @Test
    @DisplayName("Should get comment by id")
    void shouldGetCommentById() {

        Comment expectedComment = Comment.builder()
                .id(EXISTING_ID1)
                .content(EXISTING_CONTENT1)
                .build();
        Comment actualComment = commentRepository.getReferenceById(EXISTING_ID1);

        Assertions.assertThat(expectedComment.getId()).isEqualTo(actualComment.getId());
        Assertions.assertThat(expectedComment.getContent()).isEqualTo(actualComment.getContent());
        Assertions.assertThat(actualComment.getBook().getName()).isEqualTo(EXISTING_BOOK_NAME1);
    }

    @Test
    @DisplayName("Should get comments by book id")
    void shouldGetCommentsByBookId() {

        List<Comment> actualComments = commentRepository.findByBookId(EXISTING_ID1);

        Assertions.assertThat(actualComments.size()).isEqualTo(2L);

        Comment firstActual = actualComments.get(0);
        Assertions.assertThat(firstActual.getId()).isEqualTo(EXISTING_ID1);
        Assertions.assertThat(firstActual.getContent()).isEqualTo(EXISTING_CONTENT1);

        Comment secondActual = actualComments.get(1);
        Assertions.assertThat(secondActual.getId()).isEqualTo(EXISTING_ID2);
        Assertions.assertThat(secondActual.getContent()).isEqualTo(EXISTING_CONTENT2);
    }

    @Test
    @DisplayName("Should insert comment")
    void shouldInsertComment() {

        Book randomBook = bookRepository.getReferenceById(1L);

        Comment expectedComment = Comment.builder()
                .content("qwerty")
                .book(randomBook)
                .build();
        commentRepository.save(expectedComment);

        Comment actualComment = commentRepository.getReferenceById(EXISTING_COMMENT_COUNT + 1);

        Assertions.assertThat(expectedComment).isEqualTo(actualComment);
    }

    @Test
    @DisplayName("Should update comment")
    void shouldUpdateComment() {

        Comment expectedComment = Comment.builder()
                .id(EXISTING_ID2)
                .content("brand new updated content")
                .build();
        commentRepository.updateCommentById(expectedComment.getId(), expectedComment.getContent());
        Comment actualComment = commentRepository.getReferenceById(EXISTING_ID2);

        Assertions.assertThat(expectedComment.getId()).isEqualTo(actualComment.getId());
        Assertions.assertThat(expectedComment.getContent()).isEqualTo(actualComment.getContent());
    }

    @Test
    @DisplayName("Should delete comment by id")
    void shouldDeleteCommentById() {

        int beforeSize = commentRepository.findAll().size();
        commentRepository.deleteById(EXISTING_ID2);
        entityManager.flush();

        int afterSize = commentRepository.findAll().size();
        Assertions.assertThat(beforeSize).isEqualTo(afterSize + 1);
    }
}
