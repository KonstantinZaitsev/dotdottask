package by.zaitsev.dotdottask.controller.filter;

import by.zaitsev.dotdottask.model.entity.User;
import by.zaitsev.dotdottask.util.AttributeName;
import by.zaitsev.dotdottask.util.PagePath;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

/**
 * JspSecurityFilter is a filter class used to protect jsp pages.
 *
 * @author Konstantin Zaitsev
 */
@WebFilter(urlPatterns = "*.jsp")
public class JspSecurityFilter implements Filter {
    private static final String START_URI = "/index.jsp";
    private Set<String> guestPages;
    private Set<String> userPages;

    @Override
    public void init(FilterConfig filterConfig) {
        guestPages = Set.of(PagePath.INDEX_PAGE, PagePath.SIGN_IN_PAGE, PagePath.SIGN_UP_PAGE, PagePath.ERROR_400_PAGE);
        userPages = Set.of(PagePath.CATALOG_PAGE, PagePath.ERROR_400_PAGE);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        var servletRequest = (HttpServletRequest) request;
        var servletResponse = (HttpServletResponse) response;
        String requestUri = servletRequest.getServletPath();
        HttpSession session = servletRequest.getSession();
        boolean isGuestPage = guestPages.stream().anyMatch(requestUri::contains);
        boolean isUserPage = userPages.stream().anyMatch(requestUri::contains);
        Optional<User> optionalUser = Optional.ofNullable((User) session.getAttribute(AttributeName.USER));
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            var userRole = user.getUserRole();
            if ((userRole == User.UserRole.GUEST && isGuestPage) || (userRole == User.UserRole.USER && isUserPage)) {
                chain.doFilter(servletRequest, servletResponse);
            } else {
                servletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            if (isGuestPage || requestUri.equals(START_URI)) {
                chain.doFilter(servletRequest, servletResponse);
            } else {
                servletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }
}
