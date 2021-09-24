package ua.kharkiv.epam.shchehlov.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CommandAccessFilter implements Filter {
    private static final Logger log = Logger.getLogger(CommandAccessFilter.class);

    private final Set<String> accessibleCommands;
    private final Set<String> clientCommands;
    private final Set<String> masterCommands;
    private final Set<String> adminCommands;

    public CommandAccessFilter() {
        accessibleCommands = new HashSet<>();
        clientCommands = new HashSet<>();
        masterCommands = new HashSet<>();
        adminCommands = new HashSet<>();


        //accessibleCommands
        accessibleCommands.add("logIn");
        accessibleCommands.add("logOut");
        accessibleCommands.add("registration");
        accessibleCommands.add("showMasterServices");
        accessibleCommands.add("showReviews");
        accessibleCommands.add("showContacts");
        accessibleCommands.add("changeLanguage");

        //client
        clientCommands.add("showReviews");
        clientCommands.add("createMeeting");
        clientCommands.add("clientCabinet");
        clientCommands.add("leaveReview");
        clientCommands.add("showTimeSlots");

        //master
        masterCommands.add("activeDone");
        masterCommands.add("masterSchedule");

        //admin
        adminCommands.add("paidDone");
        adminCommands.add("adminCabinet");
        adminCommands.add("changeTimeSlot");
        adminCommands.add("cancelMeeting");

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("Start initializing filter: " + CommandAccessFilter.class.getSimpleName());

        log.debug("Finished initializing filter: " + CommandAccessFilter.class.getSimpleName());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String command = httpRequest.getParameter("command");

        if (accessibleCommands.contains(command)) {
            log.debug("This command can be accessed by all users: " + command);
            filterChain.doFilter(httpRequest, httpResponse);
        } else {
            log.debug("This command can be accessed only by logged in users: " + command);
            HttpSession session = httpRequest.getSession(false);
            if (session == null || session.getAttribute("role") == null || session.getAttribute("Role") == "Guest") {
                log.debug("Unauthorized access to resource. Client is not logged-in.");
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                String userRole = String.valueOf(session.getAttribute("role"));
                log.debug("Command is specific to user. Check user role.");
                log.debug("Check user role." + userRole);
                if ("client".equals(userRole) && clientCommands.contains(command)) {
                    log.debug("User is client. Command can be executed by client: " + command);
                    filterChain.doFilter(httpRequest, httpResponse);
                } else if ("master".equals(userRole) && masterCommands.contains(command)) {
                    log.debug("User is master. Command can be executed by master: " + command);
                    filterChain.doFilter(httpRequest, httpResponse);
                } else if ("admin".equals(userRole) && adminCommands.contains(command)) {
                    log.debug("User is admin. Command can be executed by admin: " + command);
                    filterChain.doFilter(httpRequest, httpResponse);
                } else {
                    httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        }
    }

    @Override
    public void destroy() {
        log.debug("Start filter destroy: " + CommandAccessFilter.class.getSimpleName());
        // do nothing
        log.debug("Finished filter destroy: " + CommandAccessFilter.class.getSimpleName());
    }

}
