package web.models.users.check;

import config.loggingfilter.Info;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class PeopleOnlineChecker {
    private static final Map<String, Timer> timers = new HashMap<>();
    private static final int timeout = 5;

    public static void registerRequest(HttpServletRequest request, Info info) {
        var address = info.getClientBrowser(request);
        timers.computeIfPresent(address, (s, timer) -> {timer.cancel(); timer.purge(); return null;});

        Timer timer = new Timer();
        var task = new DeleteTask(address, timer, timers);

        timer.schedule(task, timeout * 1000 * 60);
        timers.put(address, timer);
    }

    public static void stop() {
        for (var i : timers.entrySet()) {
            i.getValue().cancel();
            i.getValue().purge();
        }
    }
}
