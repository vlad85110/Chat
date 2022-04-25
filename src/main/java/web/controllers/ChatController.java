package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.dao.MessageDAO;
import web.dao.PersonDAO;
import web.models.Message;
import web.models.Person;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private final MessageDAO messageDAO;

    @Autowired
    public ChatController(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @GetMapping()
    public String chatFrame(Model model, @ModelAttribute("message") Message message) {
        model.addAttribute("messages", messageDAO.showAll());
        return "chat";
    }

    @PostMapping()
    public String sendMessage(@ModelAttribute("message") Message message, HttpServletRequest request) {
        var addr = request.getRemoteAddr();
        System.out.println(this);
        System.out.println(addr);
        messageDAO.sendMessage(message, addr);
        return "redirect:/chat";
    }
}
