package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
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
    private static final String PAGE_MASTER_SERVICE_LIST = "/WEB-INF/jsp/msList.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        log.debug(session.getAttribute("role"));

        CatalogService ms = new CatalogServiceImpl(new CatalogDaoImpl());
        List<Catalog> msList = ms.getAll();
        String sortParameter;
        long serviceFilter;
        long masterFilter;

        if (request.getParameter("sFilter") != null && !request.getParameter("sFilter").equals("")) {
            serviceFilter = Long.parseLong(request.getParameter("sFilter"));
            msList.removeIf(catalog -> catalog.getService().getId() != serviceFilter);
            log.debug(serviceFilter);
            request.setAttribute("sFilter", serviceFilter);
        }

        if (request.getParameter("mFilter") != null && !request.getParameter("mFilter").equals("")) {
            masterFilter = Long.parseLong(request.getParameter("mFilter"));
            msList.removeIf(catalog -> catalog.getMaster().getId() != masterFilter);
            request.setAttribute("mFilter", masterFilter);
        }

        if (request.getParameter("sort") != null) {
            sortParameter = request.getParameter("sort");
            switch (sortParameter) {
                case "master":
                    msList.sort(Comparator.comparing(o -> o.getMaster().getName()));
                    break;
                case "rate":
                    msList.sort((o1, o2) -> Double.compare(o2.getMaster().getRate(), o1.getMaster().getRate()));
                    break;
            }
        }


        request.setAttribute("msList", msList);
        return PAGE_MASTER_SERVICE_LIST;
    }
}
