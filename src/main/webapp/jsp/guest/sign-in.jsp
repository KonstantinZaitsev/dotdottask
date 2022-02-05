<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../WEB-INF/import/import.jspf" %>

<fmt:message key="authorization.sign-in.title" var="title"/>
<fmt:message key="authorization.sign-in.header.first" var="header_first"/>
<fmt:message key="authorization.sign-in.header.second" var="header_second"/>
<fmt:message key="authorization.sign-in.form.email" var="form_email"/>
<fmt:message key="authorization.sign-in.form.password" var="form_password"/>
<fmt:message key="authorization.sign-in.forgot-password" var="forgot_password"/>
<fmt:message key="authorization.sign-in.button" var="button"/>
<fmt:message key="authorization.sign-in.or-sign-up" var="or_sign_up"/>
<fmt:message key="authorization.sign-in.back" var="back"/>
<fmt:message key="authorization.sign-in.sign-up" var="sign_up"/>
<fmt:message key="authorization.sign-in.error" var="error"/>

<%--@elvariable id="sign_in_result" type="java.lang.Boolean"--%>

<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,
    400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://unpkg.com/aos@next/dist/aos.css" rel="stylesheet"/>
    <link href="${abs}/css/authorization.css" rel="stylesheet">
    <title>${title}</title>
</head>
<body>
<main id="main">
    <div class="content">
        <div class="container">
            <div class="row">
                <div class="col-md-6" data-aos="fade-up">
                    <img src="${abs}/image/authorization-bg.svg" class="img-fluid" alt="">
                </div>
                <div class="col-md-6 contents">
                    <div class="row justify-content-center">
                        <div class="col-md-8">
                            <div class="mb-4">
                                <h3 data-aos="zoom-out" data-aos-delay="200">${header_first}</h3>
                                <p class="mb-4" data-aos="zoom-out" data-aos-delay="300">${header_second}</p>
                            </div>
                            <form action="${abs}/controller" method="post">
                                <input type="hidden" name="command" value="sign_in_command">
                                <div class="form-group first">
                                    <label for="email"></label>
                                    <input type="email" name="email" placeholder="${form_email}" class="form-control"
                                           id="email" minlength="6" maxlength="50" required data-aos="zoom-out"
                                           data-aos-delay="500">
                                </div>
                                <div class="form-group last mb-4">
                                    <label for="password"></label>
                                    <input type="password" name="password" placeholder="${form_password}"
                                           class="form-control" id="password" minlength="6" maxlength="30" required
                                           data-aos="zoom-out" data-aos-delay="700">
                                </div>
                                <div class="d-flex mb-5 align-items-center" data-aos="zoom-out" data-aos-delay="800">
                                    <span class="ml-auto"><a href="#" class="forgot-pass">${forgot_password}</a></span>
                                </div>
                                <input type="submit" value="${button}" class="btn btn-block btn-primary"
                                       data-aos="zoom-out" data-aos-delay="1000">
                                <div class="error" data-aos="zoom-out" data-aos-delay="1000">
                                    <c:if test="${sign_in_result eq 'false'}">
                                        <small>${error}</small>
                                    </c:if>
                                </div>
                                <span class="d-block text-left my-4 text-muted" data-aos="zoom-out"
                                      data-aos-delay="1000">&mdash; ${or_sign_up} &mdash;</span>
                                <a href="${abs}/controller?command=go_to_index_page_command" data-aos="zoom-out"
                                   data-aos-delay="1200"
                                   class="btn d-inline-flex align-items-center justify-content-center align-self-center">
                                    <span>${back}</span>
                                    <i class="bi-arrow-right"></i>
                                </a>
                                <a href="${abs}/controller?command=go_to_sign_up_page_command" data-aos="zoom-out"
                                   data-aos-delay="1200"
                                   class="btn d-inline-flex align-items-center justify-content-center align-self-center">
                                    <span>${sign_up}</span>
                                    <i class="bi-arrow-right"></i>
                                </a>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://unpkg.com/aos@next/dist/aos.js"></script>
<script src="${abs}/js/authorization.js"></script>
</body>
</html>
