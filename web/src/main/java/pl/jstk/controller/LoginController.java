package pl.jstk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String login (Model model) {

        return ViewNames.LOGIN;
    }

    @GetMapping(value = "/403")
    public String denied (Model model) {
model.addAttribute("error", "Access denied");
        return ViewNames.DENIED;
    }

   /* @PostMapping(value = "/login")
    public String login (Model model) {

        return ViewNames.WELCOME;
    }
*/


}
