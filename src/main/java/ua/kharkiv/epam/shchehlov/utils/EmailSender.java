package ua.kharkiv.epam.shchehlov.utils;

import org.apache.log4j.Logger;
import ua.kharkiv.epam.shchehlov.dao.impl.MeetingDaoImpl;
import ua.kharkiv.epam.shchehlov.entity.Meeting;
import ua.kharkiv.epam.shchehlov.services.MeetingService;
import ua.kharkiv.epam.shchehlov.services.impl.MeetingServiceImpl;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

public class EmailSender {
    private static final Logger log = Logger.getLogger(EmailSender.class);

    private static boolean sendEmail(String emailTo, String name) {
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

    static void emailProcess(){
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
    }
}


