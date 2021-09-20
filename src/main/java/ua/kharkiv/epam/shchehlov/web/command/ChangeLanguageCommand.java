package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeLanguageCommand extends Command {
    private static final long serialVersionUID = 8481493066571573283L;
    private static final Logger log = Logger.getLogger(ChangeLanguageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String language = request.getParameter("language");
        log.debug(String.format("language - %s", language));


        HttpSession session = request.getSession();
        session.setAttribute("language", language);


        return "/controller?command=showMasterServices";
    }
}
