package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;


@Controller
public class BooksController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/books")
    public String showBooks(Model model) {
        model.addAttribute("bookList", bookService.findAllBooks());
        return ViewNames.BOOKS;
    }

    @GetMapping(value = "/books/add")
    public String add(Model model) {
        model.addAttribute("newBook", new BookTo());
        return ViewNames.ADD;
    }

    @PostMapping("/greeting")
    public String addBook(@ModelAttribute("newBook") BookTo newBook, Model model) {
        bookService.saveBook(newBook);
        model.addAttribute(ModelConstants.MESSAGE, "Success!");
        model.addAttribute(ModelConstants.INFO, "Book added");
        return ViewNames.WELCOME;
    }


    @GetMapping(value = "/books/{id}")
    public String showBook(@RequestParam("id") Long id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        return ViewNames.BOOK;
    }

    @GetMapping(value = "/books/search")
    public String search(Model model) {
        model.addAttribute("newBook", new BookTo());
        return ViewNames.SEARCH;
    }

    @PostMapping(value = "/searching")
    public String searching(@ModelAttribute("title") String title, @ModelAttribute("authors") String author, Model model) {
        model.addAttribute("bookList", bookService.findBooksByMany(title, author));

        model.addAttribute("info", "There is result of your search:");
        return ViewNames.BOOKS;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping(value = "/delete/{id}")
    public String deleteBook(@RequestParam("id") Long id, Model model) {
        bookService.deleteBook(id);
        model.addAttribute(ModelConstants.MESSAGE, "Success!");
        model.addAttribute(ModelConstants.INFO, "Book deleted");
        return ViewNames.WELCOME;
    }

    @ExceptionHandler({AccessDeniedException.class})
    public String handleException(Model model) {
        model.addAttribute("error", "Access denied, you must be logged");
        return "403";

    }
}
