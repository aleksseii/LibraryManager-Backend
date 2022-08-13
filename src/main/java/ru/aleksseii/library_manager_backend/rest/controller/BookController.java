package ru.aleksseii.library_manager_backend.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.aleksseii.library_manager_backend.domain.Book;
import ru.aleksseii.library_manager_backend.service.BookService;
import ru.aleksseii.library_manager_backend.rest.dto.BookDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping(value = "/book")
    public List<BookDTO> getAllBooks() {

        return bookService.getAll().stream()
                .map(BookDTO::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/book", params = { "name" })
    public List<BookDTO> getBooksByName(@RequestParam(name = "name") String bookName) {

        return bookService.getByName(bookName).stream()
                .map(BookDTO::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/book/{id}")
    public BookDTO getBookById(@PathVariable(name = "id") long bookId) {

        Book book = bookService.getById(bookId);
        return BookDTO.toDTO(book);
    }

    @PostMapping(value = "/book", params = { "book", "author", "genre" })
    public BookDTO insertBook(@RequestParam(name = "book") String bookName,
                              @RequestParam(name = "author") String authorName,
                              @RequestParam(name = "genre") String genreName) {

        Book insertedBook = bookService.insert(bookName, authorName, genreName);
        return BookDTO.toDTO(insertedBook);
    }

    @PutMapping(value = "/book/{id}", params = { "book", "author", "genre" })
    public BookDTO updateBook(@PathVariable(name = "id") long bookId,
                              @RequestParam(name = "book") String bookName,
                              @RequestParam(name = "author") String authorName,
                              @RequestParam(name = "genre") String genreName) {

        Book updatedBook = bookService.update(bookId, bookName, authorName, genreName);
        return BookDTO.toDTO(updatedBook);
    }

    @DeleteMapping(value = "/book/{id}")
    public void deleteBook(@PathVariable(name = "id") long bookId) {

        bookService.deleteById(bookId);
    }
}
