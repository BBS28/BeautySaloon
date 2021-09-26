package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;
import ua.kharkiv.epam.shchehlov.constant.Path;
import ua.kharkiv.epam.shchehlov.dao.impl.CatalogDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Catalog;
import ua.kharkiv.epam.shchehlov.services.CatalogService;
import ua.kharkiv.epam.shchehlov.services.impl.CatalogServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class ShowMasterServiceCommand extends Command {
    private static final Logger log = Logger.getLogger(ShowMasterServiceCommand.class);
    private static final long serialVersionUID = -8481481565177573283L;
    private static final String START_COMMAND = "Command ShowMasterServiceCommand start";
    private static final String END_COMMAND = "Command ShowMasterServiceCommand finished";
    private static final String S_FILTER = "sFilter";
    private static final String M_FILTER = "mFilter";
    private static final String MS_LIST = "msList";

    /**
     * Execution method for ShowMasterServiceCommand command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug(START_COMMAND);
        HttpSession session = request.getSession();
        log.debug(session.getAttribute(Constant.ROLE));

        CatalogService ms = new CatalogServiceImpl(new CatalogDaoImpl());
        List<Catalog> msList = ms.getAll();
        String sortParameter;
        long serviceFilter;
        long masterFilter;

        if (request.getParameter(S_FILTER) != null && !request.getParameter(S_FILTER).equals("")) {
            serviceFilter = Long.parseLong(request.getParameter(S_FILTER));
            msList.removeIf(catalog -> catalog.getService().getId() != serviceFilter);
            log.debug(serviceFilter);
            request.setAttribute(S_FILTER, serviceFilter);
        }

        if (request.getParameter(M_FILTER) != null && !request.getParameter(M_FILTER).equals("")) {
            masterFilter = Long.parseLong(request.getParameter(M_FILTER));
            msList.removeIf(catalog -> catalog.getMaster().getId() != masterFilter);
            request.setAttribute(M_FILTER, masterFilter);
        }

        if (request.getParameter(Constant.SORT) != null) {
            sortParameter = request.getParameter(Constant.SORT);
            switch (sortParameter) {
                case Constant.MASTER:
                    msList.sort(Comparator.comparing(o -> o.getMaster().getName()));
                    break;
                case Constant.RATE:
                    msList.sort((o1, o2) -> Double.compare(o2.getMaster().getRate(), o1.getMaster().getRate()));
                    break;
            }
        }
        request.setAttribute(MS_LIST, msList);
        log.debug(END_COMMAND);
        return Path.MASTER_SERVICE_LIST_PATH;
    }
}
