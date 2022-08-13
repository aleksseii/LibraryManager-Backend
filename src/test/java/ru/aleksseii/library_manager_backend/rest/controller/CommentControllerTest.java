package ru.aleksseii.library_manager_backend.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.aleksseii.library_manager_backend.domain.Comment;
import ru.aleksseii.library_manager_backend.rest.dto.CommentDTO;
import ru.aleksseii.library_manager_backend.service.CommentService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("class CommentController")
@WebMvcTest(value = CommentController.class)
class CommentControllerTest {

    private static final long COMMENT_ID1 = 1L;
    private static final String COMMENT_CONTENT1 = "commentContent1";
    private static final Comment COMMENT1 = new Comment(COMMENT_ID1, COMMENT_CONTENT1, null);

    private static final long COMMENT_ID2 = 2L;
    private static final String COMMENT_CONTENT2 = "commentContent2";
    private static final Comment COMMENT2 = new Comment(COMMENT_ID2, COMMENT_CONTENT2, null);

    private static final long BOOK_ID1 = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("Should get all comments")
    void shouldGetAllComments() throws Exception {

        List<Comment> comments = new ArrayList<>();
        comments.add(COMMENT1);
        comments.add(COMMENT2);
        BDDMockito.given(commentService.getAll()).willReturn(comments);

        List<CommentDTO> expectedComments = comments.stream()
                .map(CommentDTO::toDTO)
                .toList();

        mockMvc.perform(get("/comment"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedComments)));
    }

    @Test
    @DisplayName("Should get comments by book id")
    void shouldGetCommentsByBookId() throws Exception {

        List<Comment> comments = new ArrayList<>();
        comments.add(COMMENT1);
        comments.add(COMMENT2);
        BDDMockito.given(commentService.getByBookId(BOOK_ID1)).willReturn(comments);
        List<CommentDTO> expectedComments = comments.stream()
                .map(CommentDTO::toDTO)
                .toList();

        mockMvc.perform(get("/book/{book_id}/comment", BOOK_ID1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedComments)));
    }

    @Test
    @DisplayName("Should insert comment")
    void shouldInsertComment() throws Exception {

        BDDMockito.given(commentService.insert(BOOK_ID1, COMMENT_CONTENT1)).willReturn(COMMENT1);
        CommentDTO expectedComment = CommentDTO.toDTO(COMMENT1);

        mockMvc.perform(post("/comment")
                .param("book_id", Long.toString(BOOK_ID1))
                .param("content", COMMENT_CONTENT1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedComment)));
    }

    @Test
    @DisplayName("Should update comment")
    void shouldUpdateComment() throws Exception {

        Comment updatedComment = Comment.builder()
                .id(COMMENT_ID1)
                .content(COMMENT_CONTENT2)
                .build();
        BDDMockito.given(commentService.update(COMMENT_ID1, COMMENT_CONTENT2)).willReturn(updatedComment);
        CommentDTO expectedComment = CommentDTO.toDTO(updatedComment);

        mockMvc.perform(put("/comment/{id}", COMMENT_ID1)
                .param("content", COMMENT_CONTENT2))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedComment)));
        Mockito.verify(commentService, Mockito.times(1)).update(COMMENT_ID1, COMMENT_CONTENT2);
    }

    @Test
    @DisplayName("Should delete comment")
    void shouldDeleteComment() throws Exception {

        mockMvc.perform(delete("/comment/{id}", COMMENT_ID1))
                .andExpect(status().isOk());
        Mockito.verify(commentService, Mockito.times(1)).deleteById(COMMENT_ID1);
    }
}
