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
    }

    public static Command get(String commandName) {

        if (commandName == null || !commands.containsKey(commandName)) {
            log.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }
}
