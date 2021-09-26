package ua.kharkiv.epam.shchehlov.filter;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.web.command.CommandContainer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CommandAccessFilter implements Filter {
    private static final Logger log = Logger.getLogger(CommandAccessFilter.class);
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String NO_COMMAND = "noCommand";
    private static final String START_INIT = "Start initializing filter: ";
    private static final String END_INIT = "Finished initializing filter: ";
    private static final String START_FILTER = "CommandAccessFilter starts";
    private static final String END_FILTER = "CommandAccessFilter finished";
    private static final String START_DESTROY = "Start filter destroy: ";
    private static final String END_DESTROY = "Finished filter destroy: ";

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
        accessibleCommands.add("noCommand");

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
        log.debug(START_INIT + CommandAccessFilter.class.getSimpleName());

        log.debug(END_INIT + CommandAccessFilter.class.getSimpleName());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug(START_FILTER);
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String command = httpRequest.getParameter(Constant.COMMAND);

        if (!accessibleCommands.contains(command) &&
        !clientCommands.contains(command) &&
        !masterCommands.contains(command) &&
        !adminCommands.contains(command)) {
            command = NO_COMMAND;
        }

        if (accessibleCommands.contains(command)) {
            log.debug("This command can be accessed by all users: " + command);
            filterChain.doFilter(httpRequest, httpResponse);
        } else {
            log.debug("This command can be accessed only by logged in users: " + command);
            HttpSession session = httpRequest.getSession(false);
            if (session == null || session.getAttribute(Constant.ROLE) == null || session.getAttribute(Constant.ROLE) == Constant.GUEST) {
                log.debug("Unauthorized access to resource. Client is not logged-in.");
                servletRequest.setAttribute(ERROR_MESSAGE, Constant.ERROR_UNAUTHORISED_USER);
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                String userRole = String.valueOf(session.getAttribute(Constant.ROLE));
                log.debug("Command is specific to user. Check user role.");
                log.debug("Check user role." + userRole);
                if (Constant.CLIENT.equals(userRole) && clientCommands.contains(command)) {
                    log.debug("User is client. Command can be executed by client: " + command);
                    filterChain.doFilter(httpRequest, httpResponse);
                } else if (Constant.MASTER.equals(userRole) && masterCommands.contains(command)) {
                    log.debug("User is master. Command can be executed by master: " + command);
                    filterChain.doFilter(httpRequest, httpResponse);
                } else if (Constant.ADMIN.equals(userRole) && adminCommands.contains(command)) {
                    log.debug("User is admin. Command can be executed by admin: " + command);
                    filterChain.doFilter(httpRequest, httpResponse);
                } else {
                    servletRequest.setAttribute(ERROR_MESSAGE, Constant.ERROR_UNAUTHORISED_USER);
                    httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        }
        log.debug(END_FILTER);
    }

    @Override
    public void destroy() {
        log.debug(START_DESTROY + CommandAccessFilter.class.getSimpleName());
        // do nothing
        log.debug(END_DESTROY + CommandAccessFilter.class.getSimpleName());
    }
}
