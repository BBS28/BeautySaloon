package ua.kharkiv.epam.shchehlov.web.command;

import ua.kharkiv.epam.shchehlov.dao.impl.ServiceDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Service;
import ua.kharkiv.epam.shchehlov.services.ServiceService;
import ua.kharkiv.epam.shchehlov.services.impl.ServiceServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowServicesCommand extends Command{
    public static final String PAGE_SERVICE_LIST = "/WEB-INF/jsp/serviceList.jsp";
    private static final long serialVersionUID = -8441481565177573283L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       HttpSession session = request.getSession();
        ServiceService serviceService = new ServiceServiceImpl(new ServiceDaoImpl());
        List<Service> serviceList = serviceService.getAll();
        session.setAttribute("serviceList", serviceList);
        System.out.println(serviceList);
        return PAGE_SERVICE_LIST;
    }
}
