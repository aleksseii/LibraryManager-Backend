package ru.aleksseii.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.aleksseii.domain.Comment;
import ru.aleksseii.rest.dto.CommentDTO;
import ru.aleksseii.service.CommentService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private static final List<Comment> COMMENTS = new ArrayList<>();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CommentService commentService;

    // TODO: find out why any test from this class crashing (probably I should rewrite this whole class)
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
}
