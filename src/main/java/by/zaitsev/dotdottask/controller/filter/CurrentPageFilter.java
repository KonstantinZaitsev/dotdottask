package by.zaitsev.dotdottask.controller.filter;

import by.zaitsev.dotdottask.util.AttributeName;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * CurrentUserFilter is a filter class used to add a user current page to a session.
 *
 * @author Konstantin Zaitsev
 */
@WebFilter(urlPatterns = "*.jsp",
        dispatcherTypes = {DispatcherType.REQUEST},
        initParams = {@WebInitParam(name = "PAGES_ROOT_DIRECTORY", value = "/jsp"),
                @WebInitParam(name = "INDEX_PAGE", value = "/index.jsp")})
public class CurrentPageFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(CurrentPageFilter.class);
    private String root;
    private String indexPage;

    @Override
    public void init(FilterConfig filterConfig) {
        root = filterConfig.getInitParameter("PAGES_ROOT_DIRECTORY");
        indexPage = filterConfig.getInitParameter("INDEX_PAGE");
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        String requestUri = httpRequest.getRequestURI();
        String currentPage = indexPage;
        int rootFirstIndex = requestUri.indexOf(root);
        if (rootFirstIndex != -1) {
            currentPage = requestUri.substring(rootFirstIndex);
        }
        httpRequest.getSession().setAttribute(AttributeName.CURRENT_PAGE, currentPage);
        logger.log(Level.DEBUG, "doFilter(ServletRequest request, ServletResponse response, FilterChain " +
                "chain) method was completed successfully.");
        chain.doFilter(request, response);
    }
}
