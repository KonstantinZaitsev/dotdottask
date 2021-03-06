package by.zaitsev.dotdottask.controller.command;

import by.zaitsev.dotdottask.controller.command.impl.common.ChangeLocaleCommand;
import by.zaitsev.dotdottask.controller.command.impl.common.NonExistentCommand;
import by.zaitsev.dotdottask.controller.command.impl.guest.SignInCommand;
import by.zaitsev.dotdottask.controller.command.impl.guest.SignUpCommand;
import by.zaitsev.dotdottask.controller.command.impl.navigation.GoToCatalogPageCommand;
import by.zaitsev.dotdottask.controller.command.impl.navigation.GoToIndexPageCommand;
import by.zaitsev.dotdottask.controller.command.impl.navigation.GoToSignInPageCommand;
import by.zaitsev.dotdottask.controller.command.impl.navigation.GoToSignUpPageCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.SignOutCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.edit.EditEmailCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.edit.EditImageCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.edit.EditNameCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.edit.EditPasswordCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.edit.EditSurnameCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.project.*;
import by.zaitsev.dotdottask.controller.command.impl.user.tag.AddTagCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.tag.DeleteTagCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.tag.EditTagNameCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.task.AddTaskCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.task.DeleteTaskCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.task.EditTaskAssignedUserIdCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.task.EditTaskDeadlineCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.task.EditTaskDescriptionCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.task.EditTaskIsDoneCommand;
import by.zaitsev.dotdottask.controller.command.impl.user.task.EditTaskTitleCommand;
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
    GO_TO_CATALOG_PAGE_COMMAND(new GoToCatalogPageCommand(), EnumSet.of(User.UserRole.USER)),
    GO_TO_SIGN_IN_PAGE_COMMAND(new GoToSignInPageCommand(), EnumSet.of(User.UserRole.GUEST)),
    GO_TO_SIGN_UP_PAGE_COMMAND(new GoToSignUpPageCommand(), EnumSet.of(User.UserRole.GUEST)),
    GO_TO_INDEX_PAGE_COMMAND(new GoToIndexPageCommand(), EnumSet.of(User.UserRole.GUEST)),
    SIGN_OUT_COMMAND(new SignOutCommand(), EnumSet.of(User.UserRole.USER)),
    EDIT_NAME_COMMAND(new EditNameCommand(), EnumSet.of(User.UserRole.USER)),
    EDIT_SURNAME_COMMAND(new EditSurnameCommand(), EnumSet.of(User.UserRole.USER)),
    EDIT_EMAIL_COMMAND(new EditEmailCommand(), EnumSet.of(User.UserRole.USER)),
    EDIT_PASSWORD_COMMAND(new EditPasswordCommand(), EnumSet.of(User.UserRole.USER)),
    EDIT_IMAGE_COMMAND(new EditImageCommand(), EnumSet.of(User.UserRole.USER)),
    EDIT_PROJECT_TITLE_COMMAND(new EditProjectTitleCommand(), EnumSet.of(User.UserRole.USER)),
    EDIT_PROJECT_DESCRIPTION_COMMAND(new EditProjectDescriptionCommand(), EnumSet.of(User.UserRole.USER)),
    EDIT_TAG_NAME_COMMAND(new EditTagNameCommand(), EnumSet.of(User.UserRole.USER)),
    EDIT_TASK_TITLE_COMMAND(new EditTaskTitleCommand(), EnumSet.of(User.UserRole.USER)),
    EDIT_TASK_DESCRIPTION_COMMAND(new EditTaskDescriptionCommand(), EnumSet.of(User.UserRole.USER)),
    EDIT_TASK_DEADLINE_COMMAND(new EditTaskDeadlineCommand(), EnumSet.of(User.UserRole.USER)),
    EDIT_TASK_IS_DONE_COMMAND(new EditTaskIsDoneCommand(), EnumSet.of(User.UserRole.USER)),
    EDIT_TASK_ASSIGNED_USER_ID_COMMAND(new EditTaskAssignedUserIdCommand(), EnumSet.of(User.UserRole.USER)),
    DELETE_PROJECT_COMMAND(new DeleteProjectCommand(), EnumSet.of(User.UserRole.USER)),
    DELETE_TAG_COMMAND(new DeleteTagCommand(), EnumSet.of(User.UserRole.USER)),
    DELETE_TASK_COMMAND(new DeleteTaskCommand(), EnumSet.of(User.UserRole.USER)),
    ADD_PROJECT_COMMAND(new AddProjectCommand(), EnumSet.of(User.UserRole.USER)),
    ADD_TAG_COMMAND(new AddTagCommand(), EnumSet.of(User.UserRole.USER)),
    ADD_TASK_COMMAND(new AddTaskCommand(), EnumSet.of(User.UserRole.USER)),
    DELETE_ASSIGNED_USER_COMMAND(new DeleteAssignedUserCommand(), EnumSet.of(User.UserRole.USER)),
    ADD_ASSIGNED_USER_COMMAND(new AddAssignedUserCommand(), EnumSet.of(User.UserRole.USER));

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
