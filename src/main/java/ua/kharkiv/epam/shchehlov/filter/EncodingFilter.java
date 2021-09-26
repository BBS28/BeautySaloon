package ua.kharkiv.epam.shchehlov.filter;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private static final Logger log = Logger.getLogger(EncodingFilter.class);
    private static final String START_INIT = "EncodingFilter initialization starts";
    private static final String END_INIT = "EncodingFilter initialization finished";
    private static final String START_FILTER = "EncodingFilter starts";
    private static final String END_FILTER = "EncodingFilter finished";
    private static final String START_DESTROY = "Filter destruction starts";
    private static final String END_DESTROY = "Filter destruction finished";
    private static final String REQUEST_URY = "Request uri";
    private static final String CONTENT_TYPE = "contentType";
    private static final String ENCODING = "EncodingFilter encoding ";
    private static final String FILTERABLE_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String ENCODING_DEFAULT = "UTF-8";
    private static final String ENCODING_INIT_PARAM_NAME = "encoding";
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug(START_INIT);
        encoding = filterConfig.getInitParameter(ENCODING_INIT_PARAM_NAME);
        if (encoding == null)
            encoding = ENCODING_DEFAULT;
        log.debug(END_INIT);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug(START_FILTER);
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        log.trace(REQUEST_URY + Constant.POINTER + httpRequest.getRequestURI());
        String contentType = servletRequest.getContentType();
        log.trace(CONTENT_TYPE + Constant.POINTER + contentType);
        if (contentType != null && contentType.startsWith(FILTERABLE_CONTENT_TYPE))
            servletRequest.setCharacterEncoding(encoding);
        log.debug(ENCODING + Constant.POINTER + encoding);
        log.debug(END_FILTER);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.debug(START_DESTROY);
        // no op
        log.debug(END_DESTROY);
    }
}
