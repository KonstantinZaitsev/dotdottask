package by.zaitsev.dotdottask.controller.command;

import by.zaitsev.dotdottask.controller.command.impl.common.ChangeLocaleCommand;
import by.zaitsev.dotdottask.controller.command.impl.common.NonExistentCommand;
import by.zaitsev.dotdottask.controller.command.impl.guest.SignInCommand;
import by.zaitsev.dotdottask.controller.command.impl.guest.SignUpCommand;
import by.zaitsev.dotdottask.controller.command.impl.navigation.GoToCatalogPagePathCommand;
import by.zaitsev.dotdottask.controller.command.impl.navigation.GoToSignInPageCommand;
import by.zaitsev.dotdottask.controller.command.impl.navigation.GoToSignUpPageCommand;
import by.zaitsev.dotdottask.model.entity.User;

import java.util.EnumSet;

/**
 * CommandType is an enum containing the existing command types. Each command is associated with a class instance of
 * the corresponding command and an EnumSet containing the roles to which the command is available.
 *
 * @author Konstantin Zaitsev
 */
public enum CommandType {
    NON_EXISTENT_COMMAND(new NonExistentCommand(), EnumSet.of(User.UserRole.USER, User.UserRole.GUEST)),
    CHANGE_LOCALE_COMMAND(new ChangeLocaleCommand(), EnumSet.of(User.UserRole.USER, User.UserRole.GUEST)),
    SIGN_UP_COMMAND(new SignUpCommand(), EnumSet.of(User.UserRole.GUEST)),
    SIGN_IN_COMMAND(new SignInCommand(), EnumSet.of(User.UserRole.GUEST)),
    GO_TO_CATALOG_PAGE_COMMAND(new GoToCatalogPagePathCommand(), EnumSet.of(User.UserRole.GUEST)),
    GO_TO_SIGN_IN_PAGE_COMMAND(new GoToSignInPageCommand(), EnumSet.of(User.UserRole.GUEST)),
    GO_TO_SIGN_UP_PAGE_COMMAND(new GoToSignUpPageCommand(), EnumSet.of(User.UserRole.GUEST));

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
