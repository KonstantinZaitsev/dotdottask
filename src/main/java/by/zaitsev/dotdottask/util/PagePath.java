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

    private PagePath() {
    }
}
