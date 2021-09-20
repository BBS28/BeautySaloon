package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
import ua.kharkiv.epam.shchehlov.services.CatalogService;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.impl.MeetingServiceImpl;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class SendEmailCommand extends Command {
    private static final long serialVersionUID = 8481491554321543283L;
    private static final Logger log = Logger.getLogger(SendEmailCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        MeetingService meetingService = new MeetingServiceImpl(new MeetingDaoImpl());
        List<Meeting> meetingList = meetingService.getAll();
        meetingList.removeIf(meeting -> !(meeting.getDateTime().getDayOfYear()
                == (LocalDateTime.now().minusDays(1).getDayOfYear())
                && meeting.getDateTime().getYear()
                == LocalDateTime.now().getYear()));



        for (Meeting m : meetingList) {
            String clientName = m.getClient().getName();
            boolean result = sendEmail("bbscheglov@gmail.com", clientName);
            log.debug(result);
        }


        return "/WEB-INF/jsp/login.jsp";
    }


    private static boolean sendEmail(String emailTo, String name) {

//        String to = "bbscheglov@gmail.com";         // sender email
//        String from = "beauty.saloon.epam@gmail.com";       // receiver email
//        String host = "127.0.0.1";            // mail server host

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");

        String emailFrom = "beauty.saloon.epam@gmail.com";
        String password = "2709projecT";


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailFrom, password);
            }
        });


        try {
            MimeMessage message = new MimeMessage(session); // email message

            message.setFrom(new InternetAddress(emailFrom)); // setting header fields

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));

            message.setSubject("Отзыв"); // subject line

            // actual mail body
            message.setText("Уважаемый " + name + "!\n" +
                    "Вчера вы посетили наш салон. " +
                    "Будьте добры, оставьте отзыв о полученной услуге и оцените работу мастера!");

            // Send message
            Transport.send(message);
            log.debug("Email Sent successfully....");
        } catch (MessagingException ex) {
            log.error(ex.getMessage(), ex);
            ex.printStackTrace();
        }

        return true;
    }

}
