package ua.kharkiv.epam.shchehlov.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class ExceptionFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(ExceptionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        try {
//            filterChain.doFilter(servletRequest, servletResponse);
//        } catch (Exception ex) {
//            LOGGER.error("error", ex);
//            throw ex;
//        }
    }

    @Override
    public void destroy() {

    }
}
