package by.zaitsev.dotdottask.controller.filter;

import by.zaitsev.dotdottask.controller.command.CommandType;
import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.util.AttributeName;
import by.zaitsev.dotdottask.util.ParameterName;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Optional;

/**
 * EncodingFilter is a filter class used to check command or not.
 *
 * @author Konstantin Zaitsev
 */
@WebFilter(urlPatterns = "/*")
public class CommandAccessFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        var servletRequest = (HttpServletRequest) request;
        var servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession();
        String commandString = request.getParameter(ParameterName.COMMAND);
        try {
            CommandType commandType = CommandType.valueOf(commandString.toUpperCase());
            EnumSet<User.UserRole> allowedRoles = commandType.getAllowedRoles();
            Optional<User> optionalUser = Optional.ofNullable((User) session.getAttribute(AttributeName.USER));
            if (optionalUser.isPresent()) {
                var user = optionalUser.get();
                if (allowedRoles.contains(user.getUserRole())) {
                    chain.doFilter(servletRequest, servletResponse);
                } else {
                    servletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            } else {
                if (allowedRoles.contains(User.UserRole.GUEST)) {
                    chain.doFilter(servletRequest, servletResponse);
                } else {
                    servletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            chain.doFilter(servletRequest, servletResponse);
        }
    }
}
