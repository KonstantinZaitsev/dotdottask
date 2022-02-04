package by.zaitsev.dotdottask.controller;

import java.util.StringJoiner;

/**
 * Router is a class that helps the controller understand how to handle request and response after executing a command.
 *
 * @author Konstantin Zaitsev
 */
public class Router {
    private String pagePath;
    private int errorCode;
    private String errorMessage;
    private RouterType routerType;

    public enum RouterType {
        FORWARD,
        REDIRECT
    }

    public boolean hasError() {
        return errorCode != 0;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public RouterType getRouterType() {
        return routerType;
    }

    public void setRouterType(RouterType routerType) {
        this.routerType = routerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Router that = (Router) o;
        return errorCode == that.errorCode &&
                pagePath != null ? pagePath.equals(that.pagePath) : that.pagePath == null &&
                errorMessage != null ? errorMessage.equals(that.errorMessage) : that.errorMessage == null &&
                routerType == that.routerType;
    }

    @Override
    public int hashCode() {
        int result = pagePath != null ? pagePath.hashCode() : 0;
        result = 31 * result + errorCode;
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        result = 31 * result + (routerType != null ? routerType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Router.class.getSimpleName() + "[", "]")
                .add("pagePath='" + pagePath + "'")
                .add("errorCode=" + errorCode)
                .add("errorMessage='" + errorMessage + "'")
                .add("routerType=" + routerType)
                .toString();
    }
}
