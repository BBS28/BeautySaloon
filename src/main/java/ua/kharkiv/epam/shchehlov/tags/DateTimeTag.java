package ua.kharkiv.epam.shchehlov.tags;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;

public class DateTimeTag extends SimpleTagSupport {
    private static final Logger log = Logger.getLogger(DateTimeTag.class);
    private static final String DATE_DELIMITER = "-";
    private static final String TIME_DELIMITER = ":";
    private static final String DATE_TIME_DELIMITER = " ";

    private StringWriter sw = new StringWriter();

    @Override
    public void doTag() throws JspException, IOException {
        getJspBody().invoke(sw);
        log.debug(String.format("tag sw - %s", sw));
        LocalDateTime dateTime = LocalDateTime.parse(sw.toString());
        String result = String.format("%02d-%02d-%d %02d:%02d",
                dateTime.getDayOfMonth(),
                dateTime.getMonthValue(),
                dateTime.getYear(),
                dateTime.getHour(),
                dateTime.getMinute());
        getJspContext().getOut().println(result);
    }
}
