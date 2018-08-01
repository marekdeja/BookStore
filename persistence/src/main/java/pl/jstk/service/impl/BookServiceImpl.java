package pl.jstk.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jstk.entity.BookEntity;
import pl.jstk.mapper.BookMapper;
import pl.jstk.repository.BookRepository;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookTo> findAllBooks() {
        return BookMapper.map2To(bookRepository.findAll());
    }

    @Override
    public List<BookTo> findBooksByTitle(String title) {
        return BookMapper.map2To(bookRepository.findBookByTitle(title));
    }

    @Override
    public List<BookTo> findBooksByAuthor(String author) {
        return BookMapper.map2To(bookRepository.findBookByAuthor(author));
    }

    @Override
    @Transactional
    public BookTo saveBook(BookTo book) {
        BookEntity entity = BookMapper.map(book);
        entity = bookRepository.save(entity);
        return BookMapper.map(entity);

    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);

    }

    @Override
    @Transactional
    public BookTo findBookById(Long id) {
        List<BookTo> allBooks = this.findAllBooks();
        for (int i = 0; i < allBooks.size(); i++) {
            BookTo oneBook = allBooks.get(i);
            if (oneBook.getId() == id) {
                return oneBook;
            }
        }
        return new BookTo();
    }

    @Override
    @Transactional
    public List<BookTo> findBooksByMany(String title, String author) {
        List<BookTo> books = this.findAllBooks();
        List<BookTo> result = new ArrayList<>();

        if (author.equals("")) {
            result = books.stream()
                    .filter(book -> title.equals(book.getTitle()))
                    .collect(Collectors.toList());
        }
        if (title.equals("")) {
            result = books.stream()
                    .filter(book -> author.equals(book.getAuthors()))
                    .collect(Collectors.toList());
        }

        if (!(title.equals("") && author.equals(""))){
            result = books.stream()
                    .filter(book -> title.equals(book.getTitle()) && author.equals(book.getAuthors()))
                    .collect(Collectors.toList());
        }
        return result;
    }
}
