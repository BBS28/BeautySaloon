package ua.kharkiv.epam.shchehlov.constant;

public class Constant {
    private Constant() {
    }

    public static final String POINTER = " ==> ";
    public static final String ENTITY_ID = "id";

    public static final String ADMIN = "admin";
    public static final String MASTER = "master";
    public static final String CLIENT = "client";
    public static final String GUEST = "guest";
    public static final String MEETING = "meeting";
    public static final String ACCOUNT = "account";
    public static final String MS = "ms";

    public static final String SERVICE_NAME = "name";
    public static final String SERVICE_DURATION = "duration";
    public static final String SERVICE_PRICE = "price";

    public static final String REVIEW_TEXT = "text";
    public static final String REVIEW_RATE = "rate";

    public static final String ACCOUNT_LOGIN = "login";
    public static final String ACCOUNT_PASSWORD = "password";
    public static final String ACCOUNT_REPEAT_PASSWORD = "repeatPassword";
    public static final String ACCOUNT_NAME = "name";
    public static final String ACCOUNT_SURNAME = "surname";
    public static final String ACCOUNT_EMAIL = "email";
    public static final String ACCOUNT_ROLE = "role";

    public static final String MASTER_ID = "a_id";
    public static final String MASTER_LOGIN = "a_login";
    public static final String MASTER_PASSWORD = "a_password";
    public static final String MASTER_NAME = "a_name";
    public static final String MASTER_SURNAME = "a_surname";
    public static final String MASTER_EMAIL = "a_email";
    public static final String MASTER_RATE = "avg_rate";

    public static final String MASTER_SERVICE_ACCOUNT_ID = "account_id";
    public static final String MASTER_SERVICE_SERVICE_ID = "service_id";

    public static final String MEETING_CONDITION = "condition";
    public static final String MEETING_ACCOUNT_ID = "account_id";
    public static final String MEETING_REVIEW_ID = "review_id";
    public static final String MEETING_MASTER_SERVICE_ID = "master_service_id";
    public static final String MEETING_MEET_TIME = "meet_time";
    public static final String DATE_TIME_PATTERN ="uuuu-MM-dd HH:mm:ss";

    public static final String ERROR_CANNOT_OBTAIN_CATEGORIES = "Cannot obtain categories";
    public static final String ERROR_UNAUTHORISED_USER = "Unauthorised user";
    public static final String ERROR_CANNOT_OBTAIN_DATASOURCE = "Cannot obtain DataSource";
    public static final String ERROR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain Connection";
    public static final String ERROR_CANNOT_CLOSE_CONNECTION = "Cannot close Connection";
    public static final String ERROR_CANNOT_CLOSE_STATEMENT = "Cannot close Statement";
    public static final String ERROR_CANNOT_CLOSE_RESULT_SET = "Cannot close resultSet";
    public static final String ERROR_CANNOT_ROLLBACK_TRANSACTION = "Cannot rollback transaction";


    public static final int END_WORKING_DAY_HOUR = 18;
    public static final int HOURS_PER_DAY = 24;
    public static final int HOURS_BEFORE_WORKING_DAY_STARTS = 8;
    public static final int START_WORKING_DAY_HOUR = 9;
    public static final int MAX_DAYS_FOR_REGISTER = 14;
    public static final int MAX_DAYS_AGO = -60;

    public static final String POST_METHOD = "POST";
    public static final String POST = "post";
    public static final String REQUEST_TYPE_POST = "requestTypePost";
    public static final String WARN = "warn";
    public static final String DAY = "day";
    public static final String ROLE = "role";

    public static final String SLOT = "slot";
    public static final String SCHEDULE = "schedule";
    public static final String LANGUAGE = "language";
    public static final String SCROLL = "scroll";
    public static final String SORT = "sort";
    public static final String DATE = "date";
    public static final String FORWARD = "forward";
    public static final String BACK = "back";
    public static final String RATE = "rate";
    public static final String LOCALE = "locale";
    public static final String COMMAND = "command";
}
