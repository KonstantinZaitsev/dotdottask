package by.zaitsev.dotdottask.controller.command.impl.user.edit;

import by.zaitsev.dotdottask.controller.Router;
import by.zaitsev.dotdottask.controller.command.Command;
import by.zaitsev.dotdottask.exception.CommandException;
import by.zaitsev.dotdottask.exception.ServiceException;
import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.model.service.impl.UserServiceImpl;
import by.zaitsev.dotdottask.util.AttributeName;
import by.zaitsev.dotdottask.util.ImageEncoder;
import by.zaitsev.dotdottask.util.PagePath;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class EditImageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditImageCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        var user = (User) session.getAttribute(AttributeName.USER);
        try {
            Part imagePart = request.getPart(AttributeName.IMAGE);
            try {
                byte[] imageBytes = imagePart.getInputStream().readAllBytes();
                UserServiceImpl.getInstance().updateUserImageById(user.getId(), imageBytes);
                user.setImage(ImageEncoder.getInstance().encodeImage(imageBytes));
                session.setAttribute(AttributeName.USER, user);
                logger.log(Level.DEBUG, "execute(HttpServletRequest request) method was completed " +
                        "successfully. Forwarded to catalog page");
            } catch (ServiceException e) {
                logger.log(Level.ERROR, "User cannot be updated: {}", e.getMessage());
                throw new CommandException("User cannot be updated: ", e);
            }
        } catch (IOException | ServletException e) {
            logger.log(Level.ERROR, "Unable to get image from request: {}", e.getMessage());
            throw new CommandException("Unable to get image from request: ", e);
        }
        var router = new Router();
        router.setPagePath(PagePath.CATALOG_PAGE);
        return router;
    }
}
