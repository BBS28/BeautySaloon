package ua.kharkiv.epam.shchehlov.web;

import ua.kharkiv.epam.shchehlov.dao.impl.ServiceDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Service;
import ua.kharkiv.epam.shchehlov.services.ServiceService;
import ua.kharkiv.epam.shchehlov.services.impl.ServiceServiceImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(urlPatterns = { "/serviceList" })
public class ServiceList  extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static final String PAGE_SERVICE_LIST = "/WEB-INF/jsp/serviceList.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        HttpSession session = request.getSession();
        ServiceService serviceService = new ServiceServiceImpl(new ServiceDaoImpl());
        List<Service> serviceList = serviceService.getAll();

        request.setAttribute("serviceList2", serviceList);
//        System.out.println("2==>" + request.);
        // Сохранить информацию в request attribute перед тем как forward к views.
//        request.setAttribute("errorString", errorString);
//        request.setAttribute("productList", list);
        // Forward к /WEB-INF/views/productListView.jsp
        RequestDispatcher dispatcher = request.getServletContext()
//                .getRequestDispatcher("/WEB-INF/views/productListView.jsp");
                .getRequestDispatcher(PAGE_SERVICE_LIST);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
