package ua.kharkiv.epam.shchehlov.constant;

public class Path {
    public Path() {
    }

    public static final String ADMIN_PAGE = "/WEB-INF/jsp/adminPage.jsp";
    public static final String CHANGE_TS_PATH = "/WEB-INF/jsp/changeTS.jsp";
    public static final String CLIENT_PAGE_PATH = "/WEB-INF/jsp/clientPage.jsp";
    public static final String REVIEW_PATH = "/WEB-INF/jsp/review.jsp";
    public static final String LOGIN_PATH = "/WEB-INF/jsp/login.jsp";
    public static final String MASTER_SCHEDULE_PATH = "/WEB-INF/jsp/masterSchedule.jsp";
    public static final String REGISTRATION_PATH = "/WEB-INF/jsp/registration.jsp";
    public static final String CONTACTS_PATH = "/WEB-INF/jsp/contacts.jsp";
    public static final String MASTER_SERVICE_LIST_PATH = "/WEB-INF/jsp/msList.jsp";
    public static final String SHOW_REVIEW_PATH = "/WEB-INF/jsp/showReviews.jsp";
    public static final String TIME_SLOTS_LIST_PATH = "/WEB-INF/jsp/timeSlot.jsp";
    public static final String ERROR_PATH = "/WEB-INF/jsp/error.jsp";

    public static final String COMMAND_ADMIN_CABINET = "controller?command=adminCabinet";
    public static final String COMMAND_SLASH_ADMIN_CABINET = "/controller?command=adminCabinet";
    public static final String COMMAND_CLIENT_CABINET = "controller?command=clientCabinet";
    public static final String COMMAND_MASTER_SERVICES = "/controller?command=showMasterServices";
    public static final String COMMAND_TIME_SLOTS = "controller?command=showTimeSlots";
    public static final String COMMAND_MASTER_SCHEDULE = "controller?command=masterSchedule";
    public static final String COMMAND_LOGIN = "/controller?command=logIn";
}
