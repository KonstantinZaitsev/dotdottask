<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ include file="../../WEB-INF/import/import.jspf" %>

<fmt:message key="error404.title" var="title"/>
<fmt:message key="error404.message" var="message"/>
<fmt:message key="error404.redirect.first" var="redirect_first"/>
<fmt:message key="error404.redirect.home" var="redirect_home"/>
<fmt:message key="error404.redirect.second" var="redirect_second"/>

<jsp:useBean id="user" type="by.zaitsev.dotdottask.model.entity.User" scope="session"/>

<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,
    400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
    <link href="${abs}/css/error.css" rel="stylesheet">
    <title>${title}</title>
</head>
<body>
<div class="mainbox">
    <div class="err">4</div>
    <i class="far fa-question-circle fa-spin"></i>
    <div class="err2">4</div>
    <div class="msg">
        ${message}
        <p>${redirect_first}
            <c:choose>
                <c:when test="${user.userRole eq 'GUEST'}">
                    <a href="${abs}/controller?command=go_to_index_page_command">${redirect_home}</a>
                </c:when>
                <c:when test="${user.userRole eq 'USER'}">
                    <a href="${abs}/controller?command=go_to_catalog_page_command">${redirect_home}</a>
                </c:when>
            </c:choose>
            ${redirect_second}
        </p>
    </div>
</div>
<script src="https://kit.fontawesome.com/4b9ba14b0f.js"></script>
</body>
</html>
