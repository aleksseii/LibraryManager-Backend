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
import ru.aleksseii.library_manager_backend.domain.Genre;
import ru.aleksseii.library_manager_backend.rest.dto.GenreDTO;
import ru.aleksseii.library_manager_backend.service.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("class GenreController")
@WebMvcTest(value = GenreController.class)
class GenreControllerTest {

    private static final long GENRE_ID1 = 1L;
    private static final String GENRE_NAME1 = "genreName1";
    private static final Genre GENRE1 = new Genre(GENRE_ID1, GENRE_NAME1);

    private static final long GENRE_ID2 = 2L;
    private static final String GENRE_NAME2 = "genreName2";
    private static final Genre GENRE2 = new Genre(GENRE_ID2, GENRE_NAME2);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService genreService;


    @Test
    @DisplayName("Should get all genres")
    void shouldGetAllGenres() throws Exception {

        List<Genre> genres = new ArrayList<>();
        genres.add(GENRE1);
        genres.add(GENRE2);
        BDDMockito.given(genreService.getAll()).willReturn(genres);

        List<GenreDTO> expectedGenres = genres.stream()
                .map(GenreDTO::toDTO)
                .toList();

        mockMvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedGenres)));
    }

    @Test
    @DisplayName("Should get genre by name")
    void shouldGetGenreByName() throws Exception {

        BDDMockito.given(genreService.getByName(GENRE_NAME1)).willReturn(GENRE1);
        GenreDTO expectedGenre = GenreDTO.toDTO(GENRE1);

        mockMvc.perform(get("/genre")
                .param("name", GENRE_NAME1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedGenre)));
    }

    @Test
    @DisplayName("Should get genre by id")
    void shouldGetGenreById() throws Exception {

        BDDMockito.given(genreService.getById(GENRE_ID1)).willReturn(GENRE1);
        GenreDTO expectedGenre = GenreDTO.toDTO(GENRE1);

        mockMvc.perform(get("/genre/{id}", GENRE_ID1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedGenre)));
    }

    @Test
    @DisplayName("Should insert genre")
    void shouldInsertGenre() throws Exception {

        BDDMockito.given(genreService.insert(GENRE1)).willReturn(GENRE1);
        GenreDTO expectedGenre = GenreDTO.toDTO(GENRE1);
        String expectedGenreAsString = mapper.writeValueAsString(expectedGenre);

        mockMvc.perform(post("/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedGenreAsString))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedGenreAsString));
    }

    @Test
    @DisplayName("Should update genre")
    void shouldUpdateGenre() throws Exception {

        String randomName = "random name";
        Genre domainGenre = new Genre(GENRE_ID2, randomName);
        GenreDTO expectedGenre = GenreDTO.toDTO(domainGenre);

        BDDMockito.given(genreService.update(GENRE_ID2, randomName)).willReturn(domainGenre);

        mockMvc.perform(put("/genre/{id}", GENRE_ID2)
                .param("name", randomName))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedGenre)));
    }

    @Test
    @DisplayName("Should delete genre")
    void shouldDeleteGenre() throws Exception {

        mockMvc.perform(delete("/genre/{id}", GENRE_ID2))
                .andExpect(status().isOk());

        Mockito.verify(genreService, Mockito.times(1)).deleteById(GENRE_ID2);
    }
}