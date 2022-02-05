package by.zaitsev.dotdottask.util;

import by.zaitsev.dotdottask.controller.Router;

/**
 * PagePath is a class with paths to jsp pages of the application.
 *
 * @author Konstantin Zaitsev
 * @see Router
 */
public final class PagePath {
    public static final String INDEX_PAGE = "/index.jsp";
    public static final String ERROR_400_PAGE = "/jsp/error/error400.jsp";
    public static final String AUTHORIZATION_PAGE = "/jsp/guest/authorization.jsp";
    public static final String CATALOG_PAGE = "/jsp/user/catalog/jsp";

    private PagePath() {
    }
}
