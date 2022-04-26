package web.controllers;

import config.loggingfilter.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dao.PersonDAO;
import web.dao.MessageDAO;
import web.models.messages.Message;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private final MessageDAO messageDAO;
    private final Info info;

    @Autowired
    public ChatController(MessageDAO messageDAO, Info info) {
        this.messageDAO = messageDAO;
        this.info = info;
    }

    @GetMapping()
    public String chatFrame(Model model, @ModelAttribute("message") Message message, HttpServletRequest request) {
        var address = info.getClientBrowser(request);
        if (!PersonDAO.isAuthorized(address)) {
            return "redirect:/enter";
        }

        model.addAttribute("messages", messageDAO.showAll());
        model.addAttribute("online", PersonDAO.showOnline());
        return "chat";
    }

    @PatchMapping()
    public String sendMessage(@ModelAttribute("message") Message message, HttpServletRequest request) {
        var address = info.getClientBrowser(request);
        messageDAO.sendMessage(message, address);
        return "redirect:/chat";
    }
}
