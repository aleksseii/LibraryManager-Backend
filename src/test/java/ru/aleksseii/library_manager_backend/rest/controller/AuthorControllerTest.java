package ru.aleksseii.library_manager_backend.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.aleksseii.library_manager_backend.domain.Author;
import ru.aleksseii.library_manager_backend.rest.dto.AuthorDTO;
import ru.aleksseii.library_manager_backend.service.AuthorService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("class AuthorController")
@WebMvcTest(value = AuthorController.class)
class AuthorControllerTest {

    private static final long AUTHOR_ID1 = 1L;
    private static final String AUTHOR_NAME1 = "authorName1";
    private static final Author AUTHOR1 = new Author(AUTHOR_ID1, AUTHOR_NAME1);

    private static final long AUTHOR_ID2 = 2L;
    private static final String AUTHOR_NAME2 = "authorName2";
    private static final Author AUTHOR2 = new Author(AUTHOR_ID2, AUTHOR_NAME2);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("Should get all authors")
    void shouldGetAllAuthors() throws Exception {

        List<Author> authors = new ArrayList<>();
        authors.add(AUTHOR1);
        authors.add(AUTHOR2);
        BDDMockito.given(authorService.getAll()).willReturn(authors);

        List<AuthorDTO> expectedAuthors = authors.stream()
                .map(AuthorDTO::toDTO)
                .toList();

        mockMvc.perform(get("/author"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedAuthors)));
    }

    @Test
    @DisplayName("Should get author by id")
    void shouldGetAuthorById() throws Exception {

        BDDMockito.given(authorService.getById(AUTHOR_ID1)).willReturn(AUTHOR1);
        AuthorDTO expectedAuthor = AuthorDTO.toDTO(AUTHOR1);

        mockMvc.perform(get("/author/{id}", AUTHOR_ID1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedAuthor)));
    }

    @Test
    @DisplayName("Should insert author")
    void shouldInsertAuthor() throws Exception {

        BDDMockito.given(authorService.insert(AUTHOR1)).willReturn(AUTHOR1);

        AuthorDTO expectedAuthor = AuthorDTO.toDTO(AUTHOR1);
        String expectedAuthorAsString = mapper.writeValueAsString(expectedAuthor);

        mockMvc.perform(post("/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedAuthorAsString))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedAuthorAsString));
    }

    @Test
    @DisplayName("Should update author")
    void shouldUpdateAuthor() throws Exception {

        String randomName = "random name";
        Author updatedAuthor = new Author(AUTHOR_ID2, randomName);
        AuthorDTO expectedAuthor = AuthorDTO.toDTO(updatedAuthor);

        BDDMockito.given(authorService.update(AUTHOR_ID2, randomName)).willReturn(updatedAuthor);

        mockMvc.perform(put("/author/{id}", AUTHOR_ID2)
                .param("name", randomName))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedAuthor)));
    }

    @Test
    @DisplayName("Should delete author")
    void shouldDeleteAuthor() throws Exception {

        mockMvc.perform(delete("/author/{id}", AUTHOR_ID2))
                .andExpect(status().isOk());

        Mockito.verify(authorService, Mockito.times(1)).deleteById(AUTHOR_ID2);
    }
}
