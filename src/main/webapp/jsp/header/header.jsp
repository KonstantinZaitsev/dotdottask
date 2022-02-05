<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../WEB-INF/import/import.jspf" %>

<fmt:message key="header.title" var="title"/>
<fmt:message key="header.home" var="home"/>
<fmt:message key="header.about" var="about"/>
<fmt:message key="header.language" var="language"/>
<fmt:message key="header.button.get-started" var="button_get_started"/>
<fmt:message key="header.button.sign-in" var="button_sign_in"/>

<jsp:useBean id="user" scope="session" type="by.zaitsev.dotdottask.model.entity.User"/>

<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,
    400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${abs}/css/header.css" rel="stylesheet">
    <title>${title}</title>
</head>
<body>
<header id="header" class="header fixed-top">
    <div class="container-fluid container-xl d-flex align-items-center justify-content-between">
        <a href="#" class="logo d-flex align-items-center">
            <img src="${abs}/image/logo.png" alt="">
            <span>../task</span>
        </a>
        <nav id="navbar" class="navbar">
            <ul>
                <c:choose>
                    <c:when test="${user.userRole eq 'GUEST'}">
                        <li><a class="nav-link scrollto" href="#hero">${home}</a></li>
                        <li><a class="nav-link scrollto" href="#about">${about}</a></li>
                    </c:when>
                    <c:when test="${user.userRole eq 'USER'}">

                    </c:when>
                </c:choose>
                <li class="dropdown"><a href="#"><span>${language}</span><i class="bi-chevron-down"></i></a>
                    <ul>
                        <li><a href="${abs}/controller?command=change_locale_command&locale=ru_RU">Русский</a></li>
                        <li><a href="${abs}/controller?command=change_locale_command&locale=en_US">English</a></li>
                    </ul>
                </li>
            </ul>
            <c:if test="${user.userRole eq 'GUEST'}">
                <a href="${abs}/controller?command=go_to_sign_up_page_command"
                   class="getstarted">${button_get_started}</a>
                <a href="${abs}/controller?command=go_to_sign_in_page_command" class="getstarted">${button_sign_in}</a>
            </c:if>
        </nav>
    </div>
</header>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${abs}/js/index.js"></script>
</body>
</html>
