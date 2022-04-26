package web.models.users.check;

import web.dao.PersonDAO;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DeleteTask extends TimerTask {
    private final String address;
    private final Timer timer;
    private final Map<String, Timer> timers;

    public DeleteTask(String address, Timer timer, Map<String, Timer> timers) {
        this.address = address;
        this.timer = timer;
        this.timers = timers;
    }

    @Override
    public void run() {
        PersonDAO.deleteUserFromOnline(address);
        timers.remove(address);
        System.out.println(1);
    }
}
