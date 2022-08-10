package ru.aleksseii.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.aleksseii.domain.Book;
import ru.aleksseii.rest.dto.BookDTO;
import ru.aleksseii.service.BookService;

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

    @GetMapping(value = "/book", params = {"name"})
    public List<BookDTO> getBooksByName(@RequestParam(name = "name") String bookName) {

        return bookService.getByName(bookName).stream()
                .map(BookDTO::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/book/{id}")
    public BookDTO getBookById(@PathVariable(name = "id") long bookId) {

        Book book = bookService.getById(bookId);
        return BookDTO.toDTO(book);
    }

    @PostMapping("/book")
    public BookDTO insertBook(@RequestParam(name = "book") String bookName,
                              @RequestParam(name = "author") String authorName,
                              @RequestParam(name = "genre") String genreName) {

        Book insertedBook = bookService.insert(bookName, authorName, genreName);
        return BookDTO.toDTO(insertedBook);
    }

    @PutMapping("/book/{id}")
    public BookDTO updateBook(@PathVariable(name = "id") long bookId,
                              @RequestParam(name = "book") String bookName,
                              @RequestParam(name = "author") String authorName,
                              @RequestParam(name = "genre") String genreName) {

        Book updatedBook = bookService.update(bookId, bookName, authorName, genreName);
        return BookDTO.toDTO(updatedBook);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable(name = "id") long bookId) {

        bookService.deleteById(bookId);
    }
}
