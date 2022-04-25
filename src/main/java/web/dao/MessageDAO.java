package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import web.dao.rowmappers.MessageMapper;
import web.models.Message;
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
        return template.query("SELECT * FROM messages",new BeanPropertyRowMapper<>(Message.class));
    }

    public void sendMessage(Message message, String address) {
        var name = template.query("SELECT * FROM people_online WHERE address='" + address + "'",
                new MessageMapper()).stream().findAny().orElse(null);
        System.out.println(name);
        template.update("INSERT INTO messages VALUES (?, ?, ?)", message.getText(),
                message.getTime(), name);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
