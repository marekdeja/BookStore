package pl.jstk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.jstk.constants.ViewNames;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String login (Model model) {

        return ViewNames.LOGIN;
    }
}
