package ua.kharkiv.epam.shchehlov.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private static final Logger log = Logger.getLogger(EncodingFilter.class);

    private static final String FILTERABLE_CONTENT_TYPE = "application/x-www-form-urlencoded";

    private static final String ENCODING_DEFAULT = "UTF-8";

    private static final String ENCODING_INIT_PARAM_NAME = "encoding";

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("EncodingFilter initialization starts");
        encoding = filterConfig.getInitParameter(ENCODING_INIT_PARAM_NAME);
        if (encoding == null)
            encoding = ENCODING_DEFAULT;
        log.debug("EncodingFilter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("EncodingFilter starts");
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        log.trace("Request uri --> " + httpRequest.getRequestURI());
        String contentType = servletRequest.getContentType();
        log.trace("contentType --> " + contentType);
        if (contentType != null && contentType.startsWith(FILTERABLE_CONTENT_TYPE))
            servletRequest.setCharacterEncoding(encoding);
        log.debug("EncodingFilter encoding " + encoding);
        log.debug("EncodingFilter finished");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.debug("Filter destruction starts");
        // no op
        log.debug("Filter destruction finished");
    }
}
