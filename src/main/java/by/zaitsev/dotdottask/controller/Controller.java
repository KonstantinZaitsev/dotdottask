package by.zaitsev.dotdottask.controller;

import by.zaitsev.dotdottask.controller.command.CommandFactory;
import by.zaitsev.dotdottask.exception.CommandException;
import by.zaitsev.dotdottask.util.ParameterName;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Controller is the main HttpServlet of the application. Uses the processRequest method to process the doGet and
 * doPost methods.
 *
 * @author Konstantin Zaitsev
 */
@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * @param request  request from doGet/doPost method.
     * @param response response from doGet/doPost method.
     * @throws ServletException if the request for the GET/POST could not be handled.
     * @throws IOException      if an input or output error is detected when the servlet handles the request.
     */
    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {
        String stringCommand = request.getParameter(ParameterName.COMMAND);
        var command = CommandFactory.getInstance().build(stringCommand);
        try {
            Router router = command.execute(request);
            if (router.hasError()) {
                response.sendError(router.getErrorCode(), router.getErrorMessage());
            } else {
                switch (router.getRouterType()) {
                    case FORWARD -> getServletContext()
                            .getRequestDispatcher(router.getPagePath())
                            .forward(request, response);
                    case REDIRECT -> response.sendRedirect(request.getContextPath() + router.getPagePath());
                }
            }
        } catch (CommandException e) {
            logger.log(Level.ERROR, "Internal error has occurred: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
