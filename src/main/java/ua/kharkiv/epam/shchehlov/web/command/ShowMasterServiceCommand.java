package ua.kharkiv.epam.shchehlov.web.command;

import ua.kharkiv.epam.shchehlov.dao.impl.MasterServiceDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.MasterService;
import ua.kharkiv.epam.shchehlov.services.MasterServiceService;
import ua.kharkiv.epam.shchehlov.services.impl.MasterServiceServiceImpl;

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
        MasterServiceService ms = new MasterServiceServiceImpl(new MasterServiceDaoImpl());
        List<MasterService> msList = ms.getAll();
        request.setAttribute("msList", msList);


        return PAGE_MASTER_SERVICE_LIST;
    }
}
