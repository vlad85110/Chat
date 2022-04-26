package web.models.users;

public class Person {
    private static int idGetter = 0;

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String name;
    private String password;

    public Person() {
        id = idGetter++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
