package by.zaitsev.dotdottask.controller.filter;

import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.util.AttributeName;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Optional;

/**
 * NewUserFilter is a filter class used to add a new user to a session.
 *
 * @author Konstantin Zaitsev
 */
@WebFilter(urlPatterns = "/*",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.INCLUDE, DispatcherType.FORWARD})
public class NewUserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        var httpSession = httpRequest.getSession();
        Optional<User> optionalUser = Optional.ofNullable((User) httpSession.getAttribute(AttributeName.USER));
        if (optionalUser.isEmpty()) {
            var user = new User();
            httpSession.setAttribute(AttributeName.USER, user);
        }
        chain.doFilter(request, response);
    }
}
