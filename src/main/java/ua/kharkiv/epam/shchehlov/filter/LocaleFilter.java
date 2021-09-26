package ua.kharkiv.epam.shchehlov.filter;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.constant.Constant;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Objects;

public class LocaleFilter implements Filter {
    private static final Logger log = Logger.getLogger(LocaleFilter.class);
    private static final String[] languages = {"en", "ru"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("LocaleFilter init starts");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("LocaleFilter doFilter starts");
        Locale locale = defineLocale(servletRequest);
        ServletRequest requestWrapper = setLocaleInRequest((HttpServletRequest) servletRequest, locale);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private HttpServletRequestWrapper setLocaleInRequest(HttpServletRequest request, Locale locale) {
        return new HttpServletRequestWrapper(request) {
            @Override
            public Locale getLocale() {
                return locale;
            }

            @Override
            public Enumeration<Locale> getLocales() {
                return Collections.enumeration(Collections.singleton(locale));
            }
        };
    }

    private Locale defineLocale(ServletRequest request) {
        String strLocale = request.getParameter(Constant.LANGUAGE);
        Locale locale = null;
        if (StringUtils.isNotBlank(strLocale)) {
            locale = Locale.forLanguageTag(strLocale);
            log.debug(Constant.LOCALE + Constant.POINTER + locale);
        } else if (Objects.nonNull(((HttpServletRequest) request).getSession(true).getAttribute(Constant.LOCALE))) {
            locale = (Locale) ((HttpServletRequest) request).getSession(true).getAttribute(Constant.LOCALE);
            log.debug(Constant.LOCALE + Constant.POINTER + locale);
        } else {
            Enumeration<Locale> locales = request.getLocales();
            while (locales.hasMoreElements()) {
                Locale requestLocale = locales.nextElement();
                if (ArrayUtils.contains(languages, requestLocale.getLanguage())) {
                    locale = requestLocale;
                    break;
                }
            }
            if (Objects.isNull(locale)) {
                locale = Locale.ENGLISH;
            }
            log.debug(Constant.LOCALE + Constant.POINTER + locale);
        }
        ((HttpServletRequest) request).getSession().setAttribute(Constant.LOCALE, locale);
        log.debug(((HttpServletRequest) request).getSession().getAttribute(Constant.LOCALE));
        return locale;
    }

}
