package by.zaitsev.dotdottask.controller.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.zaitsev.dotdottask.controller.command.impl.common.NonExistentCommand;

/**
 * The CommandFactory is a class used to create instances of the Command classes by name.
 *
 * @author Konstantin Zaitsev
 */
public class CommandFactory {
    private static final Logger logger = LogManager.getLogger(CommandFactory.class);
    private static CommandFactory instance;

    private CommandFactory() {
    }

    /**
     * @return a single instance of the CommandFactory class.
     */
    public static CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }

    /**
     * @param stringCommand command name to build.
     * @return an instance of the command class if such a command exists, or {@link NonExistentCommand} if not.
     */
    public Command build(String stringCommand) {
        var command = CommandType.NON_EXISTENT_COMMAND.getCurrentCommand();
        if (stringCommand != null) {
            try {
                var commandType = CommandType.valueOf(stringCommand.toUpperCase());
                command = commandType.getCurrentCommand();
            } catch (IllegalArgumentException e) {
                logger.log(Level.ERROR, "Command {} isn't exist: {}", stringCommand, e.getMessage());
            }
        } else {
            logger.log(Level.ERROR, "Unable to build command, stringCommand == null");
        }
        return command;
    }
}
