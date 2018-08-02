package pl.jstk.controller;


import org.assertj.core.util.Lists;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.jstk.constants.ModelConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.jstk.service.BookService;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class BooksControlerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BooksController booksController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(booksController).build();
    }

    @Test
    public void testBooksPage() throws Exception {
        // given
        when(bookService.findAllBooks()).thenReturn(Lists.newArrayList());
        // when
        ResultActions resultActions = mockMvc.perform(get("/books"));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("booksList"));
    }

    @Test
    public void testAddBooksPage() throws Exception {
        // given when
        ResultActions resultActions = mockMvc.perform(get("/books/add"));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("addBook")).andExpect(model()
                .attributeExists("newBook"));
    }

    @Test
    public void testGreetingPage() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(post("/greeting"));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andExpect(model().attribute(ModelConstants.MESSAGE, "Success!"));
    }

    @Test
    public void testBookWithId() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/books/{id}"));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("book"))
                .andExpect(model().attribute(ModelConstants.MESSAGE, "Success!"))
                .andExpect(model()
                        .attributeExists("newBook"));
    }

    @Test
    public void testSearchPage() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/books/search"));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("searchPage"))
                .andExpect(model()
                        .attributeExists("newBook"));
    }

    @Test
    public void testSearchingPage() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(post("/searching"));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("booksList"))
                .andExpect(model()
                        .attributeExists("bookList", "info"));
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void deleteWithAnonymous() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(post("/searching"));
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("booksList"))
                .andExpect(model()
                        .attributeExists("bookList", "info"));
    }
}