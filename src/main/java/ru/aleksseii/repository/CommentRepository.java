package ru.aleksseii.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aleksseii.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
