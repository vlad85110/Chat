package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import web.models.Person;

@Component
public class PersonDAO {
    private final JdbcTemplate template;

    @Autowired
    public PersonDAO(JdbcTemplate template) {
        this.template = template;
    }

    public void add(Person person, String address) {
        template.update("INSERT INTO people_online VALUES (?, ?)", person.getName(), address);
    }

    public void clearOnline() {
        template.update("DELETE FROM people_online");
    }
}
