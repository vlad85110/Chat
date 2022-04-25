package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.dao.PersonDAO;
import web.models.Person;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class MainController {
    private final PersonDAO personDAO;

    @Autowired
    public MainController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @PreDestroy
    public void destructor() {
        personDAO.clearOnline();
    }

    @GetMapping
    public String mainScreen() {
        return "main";
    }

    @GetMapping("/enter")
    public String joinTheChat(@ModelAttribute("person") Person person) {
        return "enter";
    }

    @PostMapping("/enter")
    public String addNewPeople(@ModelAttribute("person") Person person, HttpServletRequest request) {
        var address = request.getRemoteAddr();
        personDAO.add(person, address);
        return "redirect:/chat";
    }
}
