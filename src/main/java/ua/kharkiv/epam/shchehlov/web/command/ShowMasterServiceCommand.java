package ua.kharkiv.epam.shchehlov.web.command;

import ua.kharkiv.epam.shchehlov.dao.impl.CatalogDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Catalog;
import ua.kharkiv.epam.shchehlov.services.CatalogService;
import ua.kharkiv.epam.shchehlov.services.impl.CatalogServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowMasterServiceCommand extends Command {
    public static final String PAGE_MASTER_SERVICE_LIST = "/WEB-INF/jsp/msList.jsp";
    private static final long serialVersionUID = -8481481565177573283L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CatalogService ms = new CatalogServiceImpl(new CatalogDaoImpl());
        List<Catalog> msList = ms.getAll();
        request.setAttribute("msList", msList);


        return PAGE_MASTER_SERVICE_LIST;
    }
}
