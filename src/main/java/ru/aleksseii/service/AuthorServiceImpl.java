package ru.aleksseii.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aleksseii.domain.Author;
import ru.aleksseii.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author insert(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author getById(long id) {
        return authorRepository.getReferenceById(id);
    }

    @Override
    public Author getByName(String name) {
        return authorRepository.findByName(name);
    }

    @Override
    public Author update(long id, String authorName) {

        Author author = Author.builder()
                .id(id)
                .name(authorName)
                .build();

        return authorRepository.save(author);
    }

    @Override
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }
}
