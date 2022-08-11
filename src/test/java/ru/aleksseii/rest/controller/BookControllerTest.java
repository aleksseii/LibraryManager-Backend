package ru.aleksseii.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.aleksseii.domain.Author;
import ru.aleksseii.domain.Book;
import ru.aleksseii.domain.Comment;
import ru.aleksseii.domain.Genre;
import ru.aleksseii.rest.dto.BookDTO;
import ru.aleksseii.service.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = BookController.class)
class BookControllerTest {

    private static final long AUTHOR_ID1 = 1L;
    private static final String AUTHOR_NAME1 = "authorName1";
    private static final Author AUTHOR1 = new Author(AUTHOR_ID1, AUTHOR_NAME1);

    private static final long AUTHOR_ID2 = 1L;
    private static final String AUTHOR_NAME2 = "authorName1";
    private static final Author AUTHOR2 = new Author(AUTHOR_ID2, AUTHOR_NAME2);

    private static final long GENRE_ID1 = 1L;
    private static final String GENRE_NAME1 = "genreName1";
    private static final Genre GENRE1 = new Genre(GENRE_ID1, GENRE_NAME1);

    private static final long COMMENT_ID1 = 1L;
    private static final String COMMENT_CONTENT1 = "commentContent1";
    private static final Comment COMMENT1 = new Comment(COMMENT_ID1, COMMENT_CONTENT1, null);
    private static final List<Comment> COMMENTS = new ArrayList<>();

    private static final long BOOK_ID1 = 1L;
    private static final String BOOK_NAME1 = "bookName1";
    private static final Book BOOK1 = Book.builder()
            .id(BOOK_ID1)
            .name(BOOK_NAME1)
            .author(AUTHOR1)
            .genre(GENRE1)
            .comments(COMMENTS)
            .build();

    private static final long BOOK_ID2 = 2L;
    private static final String BOOK_NAME2 = "bookName2";
    private static final Book BOOK2 = Book.builder()
            .id(BOOK_ID2)
            .name(BOOK_NAME2)
            .author(AUTHOR2)
            .genre(GENRE1)
            .comments(COMMENTS)
            .build();

    private static final long BOOK_ID3 = 3L;

    private static final Book BOOK3 = Book.builder()
            .id(BOOK_ID3)
            .name(BOOK_NAME2)
            .author(AUTHOR1)
            .genre(GENRE1)
            .build();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("Should get all books")
    void shouldGetAllBooks() throws Exception {

        List<Book> books = new ArrayList<>();
        books.add(BOOK1);
        books.add(BOOK2);
        books.add(BOOK3);

        BDDMockito.given(bookService.getAll()).willReturn(books);

        List<BookDTO> expectedBooks = books.stream()
                .map(BookDTO::toDTO)
                .toList();

        mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedBooks)));
    }

    @Test
    @DisplayName("Should get books by name")
    void shouldGetBooksByName() throws Exception {

        List<Book> booksSameName = new ArrayList<>();
        booksSameName.add(BOOK2);
        booksSameName.add(BOOK3);

        BDDMockito.given(bookService.getByName(BOOK_NAME2)).willReturn(booksSameName);

        List<BookDTO> expectedBooks = booksSameName.stream()
                .map(BookDTO::toDTO)
                .toList();

        mockMvc.perform(get("/book")
                .param("name", BOOK_NAME2))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedBooks)));
    }

    @Test
    @DisplayName("Should get book by id")
    void shouldGetBookById() throws Exception {

        BDDMockito.given(bookService.getById(BOOK_ID1)).willReturn(BOOK1);
        BookDTO expectedBook = BookDTO.toDTO(BOOK1);

        mockMvc.perform(get("/book/{id}", BOOK_ID1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedBook)));
    }

    @Test
    @DisplayName("Should insert book")
    void shouldInsertBook() throws Exception {

        BDDMockito.given(bookService.insert(BOOK_NAME1, AUTHOR_NAME1, GENRE_NAME1)).willReturn(BOOK1);
        BookDTO expectedBook = BookDTO.toDTO(BOOK1);

        mockMvc.perform(post("/book")
                .param("book", BOOK_NAME1)
                .param("author", AUTHOR_NAME1)
                .param("genre", GENRE_NAME1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedBook)));
    }

    @Test
    @DisplayName("Should update book")
    void shouldUpdateBook() throws Exception {

        BDDMockito.given(bookService.update(BOOK_ID1, BOOK_NAME1, AUTHOR_NAME1, GENRE_NAME1)).willReturn(BOOK1);
        BookDTO expectedBook = BookDTO.toDTO(BOOK1);

        mockMvc.perform(put("/book/{id}", BOOK_ID1)
                .param("book", BOOK_NAME1)
                .param("author", AUTHOR_NAME1)
                .param("genre", GENRE_NAME1))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedBook)));
    }

    @Test
    @DisplayName("Should delete book")
    void shouldDeleteBook() throws Exception {

        mockMvc.perform(delete("/book/{id}", BOOK_ID3))
                .andExpect(status().isOk());

        Mockito.verify(bookService, Mockito.times(1)).deleteById(BOOK_ID3);
    }
}