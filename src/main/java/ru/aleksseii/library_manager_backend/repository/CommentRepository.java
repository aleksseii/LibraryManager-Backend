package ru.aleksseii.library_manager_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.aleksseii.library_manager_backend.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying
    @Query("update Comment c set c.content = :content where c.id = :id")
    void updateCommentById(@Param("id") long id,
                           @Param("content") String content);

    List<Comment> findByBookId(long id);
}
