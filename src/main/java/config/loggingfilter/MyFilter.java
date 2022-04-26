package config.loggingfilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import web.models.users.check.PeopleOnlineChecker;

import javax.servlet.http.HttpServletRequest;

@Component
public class MyFilter extends AbstractRequestLoggingFilter {
    private final Info info;

    public MyFilter() {
        this.info = new Info();
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {

    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        var URL = request.getRequestURL().toString();

        if (URL.contains("/chat")) {
            PeopleOnlineChecker.registerRequest(request, info);
        }

        System.out.println(request.getMethod());
        System.out.println(info.getClientBrowser(request));
    }
}
