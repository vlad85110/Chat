package web.dao;

import Exceptions.enter.EnterException;
import Exceptions.enter.IncorrectPasswordException;
import Exceptions.enter.NoSuchUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import web.models.Status;
import web.models.users.Person;

import java.util.List;

@Component
public class PersonDAO {
    private static JdbcTemplate template;

    @Autowired
    public PersonDAO(JdbcTemplate template) {
        PersonDAO.template = template;
    }

    public void enter(Person person, String address) throws EnterException {
        if (!isRegistered(person)) {
            try {
                template.queryForObject("SELECT * FROM people WHERE name='" + person.getName() + "'",
                        new BeanPropertyRowMapper<>(Person.class));
            } catch (EmptyResultDataAccessException ex) {
                throw new NoSuchUserException();
            }
            throw new IncorrectPasswordException();
        }

        template.update("INSERT INTO people_online VALUES (?, ?)", person.getName(), address);
        try {
            template.queryForMap("SELECT * FROM authorized_users WHERE name='" + person.getName() +
                    "' AND address='" + address + "'");
        } catch (EmptyResultDataAccessException e) {
            template.update("INSERT INTO authorized_users VALUES (?, ?)", person.getName(), address);
        }
    }

    public void register(Person person) {
        //регистрация и валидация логина с паролем
        template.update("INSERT INTO people VALUES (?, ?)", person.getName(), person.getPassword());
    }

    public void clearOnline() {
        template.update("DELETE FROM people_online");
    }

    public static void deleteUserFromOnline(String address) {
        template.update("DELETE FROM people_online WHERE address='" + address +"'");
    }

    public static boolean isAuthorized(String address) {
        try {
            template.queryForMap("SELECT * FROM authorized_users WHERE address='" + address + "'");
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    public boolean isRegistered(Person person) {
        try {
            template.queryForObject("SELECT * FROM people WHERE name='" + person.getName() + "'",
                    new BeanPropertyRowMapper<>(Person.class));
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    public Person getPersonByAddress(String address) {
        return template.queryForObject("SELECT * FROM authorized_users WHERE address='" + address + "'",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public void exit(String address) {
        template.update("DELETE FROM authorized_users WHERE address='" + address +"'");
        deleteUserFromOnline(address);
    }

    public static List<Person> showOnline() {
        return template.query("SELECT * FROM people_online", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPersonById(int id) {
        return template.queryForObject("SELECT * FROM people WHERE id='" + id + "'",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public static int getIdByName(String name) {
        var person =  template.queryForObject("SELECT * FROM people WHERE name='" + name + "'",
                new BeanPropertyRowMapper<>(Person.class));
        assert person != null;
        System.out.println(person.getName());
        return person.getId();
    }

    public Status getStatus(int id) {
        var person = getPersonById(id);

        try {
            template.queryForMap("SELECT * FROM people_online WHERE name='" + person.getName() + "'");
        } catch (EmptyResultDataAccessException e) {
            return new Status(null, "Offline");
        }
        return new Status("Online", null);
    }
}
