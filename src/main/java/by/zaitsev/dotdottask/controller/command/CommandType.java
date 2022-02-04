package by.zaitsev.dotdottask.controller.command;

import by.zaitsev.dotdottask.controller.command.impl.common.NonExistentCommand;
import by.zaitsev.dotdottask.model.entity.User;

import java.util.EnumSet;

/**
 * CommandType is an enum containing the existing command types. Each command is associated with a class instance of
 * the corresponding command and an EnumSet containing the roles to which the command is available.
 *
 * @author Konstantin Zaitsev
 */
public enum CommandType {
    NON_EXISTENT_COMMAND(new NonExistentCommand(), EnumSet.of(User.UserRole.USER, User.UserRole.GUEST));

    private final Command command;
    private final EnumSet<User.UserRole> allowedRoles;

    CommandType(Command command, EnumSet<User.UserRole> allowedRoles) {
        this.command = command;
        this.allowedRoles = allowedRoles;
    }

    public Command getCurrentCommand() {
        return command;
    }

    public EnumSet<User.UserRole> getAllowedRoles() {
        return allowedRoles;
    }
}