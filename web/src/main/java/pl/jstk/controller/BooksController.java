package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;


@Controller
public class BooksController {

    @Autowired
    BookService bookService;

    @GetMapping(value = "/books")
    public String showBooks(Model model) {
        model.addAttribute("bookList", bookService.findAllBooks() );
        return ViewNames.BOOKS;
    }

    @GetMapping(value = "/books/add")
    public String add(Model model) {
        model.addAttribute("newBook", new BookTo());
        return ViewNames.ADD;
    }


    @GetMapping(value = "/books/{id}")
    public String showBook(@RequestParam("id") Long id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        return ViewNames.BOOK;
    }

    @GetMapping(value = "/books/search")
    public String search (Model model){
        model.addAttribute("newBook", new BookTo());
        return ViewNames.SEARCH;
    }


}
