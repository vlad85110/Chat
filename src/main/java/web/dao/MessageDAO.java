package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import database.rowmappers.MessageMapper;
import web.models.messages.Message;
import java.util.List;

@Component
public class MessageDAO {
    private final JdbcTemplate template;
    private String author;

    @Autowired
    public MessageDAO(JdbcTemplate template) {
        this.template = template;
    }

    public List<Message> showAll() {
        return template.query("SELECT * FROM messages ORDER BY date",new BeanPropertyRowMapper<>(Message.class));
    }

    public void sendMessage(Message message, String address) {
        var name = template.query("SELECT * FROM people_online WHERE address='" + address + "'",
                new MessageMapper()).stream().findAny().orElse(null);
        var id = PersonDAO.getIdByName(name);
        template.update("INSERT INTO messages VALUES (?, ?, ?, ?)", message.getText(),
               id, message.getTime(), name);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
