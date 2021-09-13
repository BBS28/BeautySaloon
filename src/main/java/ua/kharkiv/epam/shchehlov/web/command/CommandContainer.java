package ua.kharkiv.epam.shchehlov.web.command;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Logger log = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put("showServices", new ShowServicesCommand());
        commands.put("showMasterServices", new ShowMasterServiceCommand());
        commands.put("showTimeSlots", new TimeSlotsCommand());
        commands.put("createMeeting", new CreateMeetingCommand());
        commands.put("logIn", new LoginCommand());
        commands.put("logOut", new LogoutCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("masterSchedule", new MasterScheduleCommand());
        commands.put("doneService", new DoneServiceCommand());
        commands.put("clientCabinet", new ClientCabinetCommand());
    }

    public static Command get(String commandName) {

        if (commandName == null || !commands.containsKey(commandName)) {
            log.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }
}
