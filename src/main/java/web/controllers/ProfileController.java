package web.controllers;

import config.loggingfilter.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.dao.PersonDAO;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final PersonDAO personDAO;
    private final Info info;

    @Autowired
    public ProfileController(PersonDAO personDAO, Info info) {
        this.personDAO = personDAO;
        this.info = info;
    }

    @GetMapping
    private String getProfile(@RequestParam("id") int id, Model model, HttpServletRequest request) {
        model.addAttribute("person", personDAO.getPersonById(id));
        model.addAttribute("status", personDAO.getStatus(id));
        return "profile";
    }
}
