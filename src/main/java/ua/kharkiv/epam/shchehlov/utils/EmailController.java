package ua.kharkiv.epam.shchehlov.utils;

import org.apache.log4j.Logger;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EmailController {
    private static final Logger log = Logger.getLogger(EmailController.class);
    private static final long PERIOD = ChronoUnit.DAYS.getDuration().getSeconds();

    public void startScheduler() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(EmailSender::emailProcess,
                getInitialDelayToEndOfTheDay(), PERIOD, TimeUnit.SECONDS);
    }

    private long getInitialDelayToEndOfTheDay() {
        ZonedDateTime tomorrowMidnight = ZonedDateTime.now(ZoneOffset.UTC)
                .plusDays(1)
                .truncatedTo(ChronoUnit.DAYS);
        return ChronoUnit.SECONDS
                .between(ZonedDateTime.now(ZoneOffset.UTC), tomorrowMidnight);
    }
}
