package web.controllers;

import Exceptions.enter.EnterException;
import config.loggingfilter.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.dao.PersonDAO;
import web.error.Error;
import web.models.users.Person;
import web.models.users.check.PeopleOnlineChecker;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class MainController {
    private final PersonDAO personDAO;
    private final Info info;
    private String error;

    @Autowired
    public MainController(PersonDAO personDAO, Info info) {
        this.personDAO = personDAO;
        this.info = info;
    }

    @PreDestroy
    public void destructor() {
        PeopleOnlineChecker.stop();
        personDAO.clearOnline();
    }

    @GetMapping
    public String mainScreen() {
        return "main";
    }

    @GetMapping("/enter")
    public String showAuthorization(@ModelAttribute("person") Person person,
                                    @ModelAttribute("error") Error error, HttpServletRequest request) {
        var address = info.getClientBrowser(request);

        if (PersonDAO.isAuthorized(address)) {
            try {
                var user = personDAO.getPersonByAddress(address);
                personDAO.enter(user, address);
            } catch (EnterException e) {
                e.printStackTrace();
            }
            return "redirect:/chat";
        }

        error.setText(this.error);
        this.error = null;
        return "enter";
    }

    @PostMapping("/enter")
    public String authorize(@ModelAttribute("person") Person person, HttpServletRequest request) {
        var address = info.getClientBrowser(request);

        try {
            personDAO.enter(person, address);
        } catch (EnterException e) {
            error = e.getMessage();
            return "redirect:/enter";
        }
        return "redirect:/chat";
    }

    @GetMapping("/register")
    public String showRegistration(@ModelAttribute("person") Person person) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("person") Person person, HttpServletRequest request) {
        var address = info.getClientBrowser(request);
        personDAO.register(person);
        try {
            personDAO.enter(person, address);
        } catch (EnterException e) {
            e.printStackTrace();
        }
        return "redirect:/chat";
    }

    @GetMapping("/exit")
    public String exit(HttpServletRequest request) {
        personDAO.exit(info.getClientBrowser(request));
        return "redirect:/";
    }
}
