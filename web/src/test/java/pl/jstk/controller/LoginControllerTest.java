package pl.jstk.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.jstk.constants.ModelConstants;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LoginControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new HomeController()).build();
    }

    @Test
    public void testLoginPage() throws Exception {
        // given when
        ResultActions resultActions = mockMvc.perform(get("/login"));
        // then
        resultActions.andExpect(status().isOk())
                     .andExpect(view().name("login"));


    }

    @Test
    public void logginInAdmin() throws Exception {
        ResultActions resultActions = mockMvc.perform(formLogin("/login").user("admin").password("admin"))
                .andExpect(authenticated().withUsername("admin"));

               // .andExpect(authenticated());

    }

    @Test
    public void logginInUser() throws Exception {
        ResultActions resultActions = mockMvc.perform(formLogin("/login").user("user").password("user"))
                .andExpect(authenticated().withUsername("user"));


    }

}
