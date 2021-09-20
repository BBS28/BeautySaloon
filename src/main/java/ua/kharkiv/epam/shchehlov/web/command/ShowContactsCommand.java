package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowContactsCommand extends Command {
    private static final Logger log = Logger.getLogger(ShowContactsCommand.class);
    private static final long serialVersionUID = 848549821177573283L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return "/WEB-INF/jsp/contacts.jsp";
    }
}
