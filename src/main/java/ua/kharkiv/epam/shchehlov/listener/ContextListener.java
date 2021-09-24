package ua.kharkiv.epam.shchehlov.listener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ua.kharkiv.epam.shchehlov.utils.EmailController;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    private static final Logger log = Logger.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log("Servlet context initialization start");
        ServletContext servletContext = servletContextEvent.getServletContext();
        initLog4J(servletContext);
        initCommandContainer();
        EmailController emailController = new EmailController();
        emailController.startScheduler();
        log("Servlet context initialization finished");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log("Servlet context destruction starts");
        // do nothing
        log("Servlet context destruction finished");
    }

    /**
     * Initializes log4j framework.
     *
     * @param servletContext
     */
    private void initLog4J(ServletContext servletContext) {
        log("Log4J initialization started");
        try {
            PropertyConfigurator.configure(
                    servletContext.getRealPath("WEB-INF/classes/log4j.properties"));
            log.debug("Log4j has been initialized");
        } catch (Exception ex) {
            log("Cannot configure Log4j");
            ex.printStackTrace();
        }
        log("Log4J initialization finished");
    }

    /**
     * Initializes CommandContainer.
     *
     */
    private void initCommandContainer() {
        // initialize commands container
        // just load class to JVM
        try {
            Class.forName("ua.kharkiv.epam.shchehlov.web.command.CommandContainer");
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Cannot initialize Command Container");
        }
    }

    private void log(String msg) {
        System.out.println("[ContextListener] " + msg);
    }
}

