<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../WEB-INF/import/import.jspf" %>

<fmt:message key="authorization.sign-up.title" var="title"/>
<fmt:message key="authorization.sign-up.header.first" var="header_first"/>
<fmt:message key="authorization.sign-up.header.second" var="header_second"/>
<fmt:message key="authorization.sign-up.form.name" var="form_name"/>
<fmt:message key="authorization.sign-up.form.surname" var="form_surname"/>
<fmt:message key="authorization.sign-up.form.email" var="form_email"/>
<fmt:message key="authorization.sign-up.form.password" var="form_password"/>
<fmt:message key="authorization.sign-up.form.confirm-password" var="form_confirm_password"/>
<fmt:message key="authorization.sign-up.button" var="button"/>
<fmt:message key="authorization.sign-up.or-sign-in" var="or_sign_in"/>
<fmt:message key="authorization.sign-up.back" var="back"/>
<fmt:message key="authorization.sign-up.sign-in" var="sign_in"/>
<fmt:message key="authorization.sign-up.error.name.invalid" var="error_name_invalid"/>
<fmt:message key="authorization.sign-up.error.surname.invalid" var="error_surname_invalid"/>
<fmt:message key="authorization.sign-up.error.email.invalid" var="error_email_invalid"/>
<fmt:message key="authorization.sign-up.error.email.not-unique" var="error_email_not_unique"/>
<fmt:message key="authorization.sign-up.error.password.invalid" var="error_password_invalid"/>
<fmt:message key="authorization.sign-up.error.confirmed-password.invalid" var="error_confirmed_password_invalid"/>

<%--@elvariable id="valid_name" type="java.lang.String"--%>
<%--@elvariable id="valid_surname" type="java.lang.String"--%>
<%--@elvariable id="valid_email" type="java.lang.String"--%>
<%--@elvariable id="invalid_name" type="java.lang.String"--%>
<%--@elvariable id="invalid_surname" type="java.lang.String"--%>
<%--@elvariable id="invalid_email" type="java.lang.String"--%>
<%--@elvariable id="invalid_password" type="java.lang.String"--%>
<%--@elvariable id="invalid_confirmed_password" type="java.lang.String"--%>

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
                                <input type="hidden" name="command" value="sign_up_command">
                                <div class="form-group first">
                                    <label for="name"></label>
                                    <input type="text" name="name" placeholder="${form_name}" class="form-control"
                                           id="name" value="${valid_name}" minlength="2" maxlength="30"
                                           pattern="[a-zA-Zа-яА-Я]+" title="enter on alphabets only" required
                                           data-aos="zoom-out" data-aos-delay="500">
                                    <div class="error" data-aos="zoom-out" data-aos-delay="500">
                                        <c:if test="${invalid_name eq 'invalid'}">
                                            <small>${error_name_invalid}</small>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="form-group middle">
                                    <label for="surname"></label>
                                    <input type="text" name="surname" placeholder="${form_surname}" class="form-control"
                                           id="surname" value="${valid_surname}" minlength="2" maxlength="30"
                                           pattern="[a-zA-Zа-яА-Я]+" title="enter on alphabets only" required
                                           data-aos="zoom-out" data-aos-delay="700">
                                    <div class="error" data-aos="zoom-out" data-aos-delay="700">
                                        <c:if test="${invalid_surname eq 'invalid'}">
                                            <small>${error_surname_invalid}</small>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="form-group middle">
                                    <label for="email"></label>
                                    <input type="email" name="email" placeholder="${form_email}" class="form-control"
                                           id="email" value="${valid_email}" minlength="6" maxlength="50" required
                                           data-aos="zoom-out" data-aos-delay="900">
                                    <div class="error" data-aos="zoom-out" data-aos-delay="900">
                                        <c:choose>
                                            <c:when test="${invalid_email eq 'invalid'}">
                                                <small>${error_email_invalid}</small>
                                            </c:when>
                                            <c:when test="${invalid_email eq 'not_unique'}">
                                                <small>${error_email_not_unique}</small>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="form-group middle">
                                    <label for="password"></label>
                                    <input type="password" name="password" placeholder="${form_password}"
                                           class="form-control" id="password" minlength="6" maxlength="30" required
                                           data-aos="zoom-out" data-aos-delay="1100">
                                    <div class="error" data-aos="zoom-out" data-aos-delay="1100">
                                        <c:if test="${invalid_password eq 'invalid'}">
                                            <small>${error_password_invalid}</small>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="form-group last">
                                    <label for="confirmed_password"></label>
                                    <input type="password" name="confirmed_password"
                                           placeholder="${form_confirm_password}" class="form-control"
                                           id="confirmed_password" minlength="6" maxlength="50" required
                                           data-aos="zoom-out" data-aos-delay="1300">
                                    <div class="error" data-aos="zoom-out" data-aos-delay="1300">
                                        <c:if test="${invalid_confirmed_password eq 'invalid'}">
                                            <small>${error_confirmed_password_invalid}</small>
                                        </c:if>
                                    </div>
                                </div>
                                <br/>
                                <input type="submit" value="${button}" class="btn btn-block btn-primary"
                                       data-aos="zoom-out" data-aos-delay="1400">
                                <span class="d-block text-left my-4 text-muted" data-aos="zoom-out"
                                      data-aos-delay="1400">&mdash; ${or_sign_in} &mdash;</span>
                                <a href="${abs}/controller?command=go_to_index_page_command" data-aos="zoom-out"
                                   data-aos-delay="1600"
                                   class="btn d-inline-flex align-items-center justify-content-center align-self-center">
                                    <span>${back}</span>
                                    <i class="bi-arrow-right"></i>
                                </a>
                                <a href="${abs}/controller?command=go_to_sign_in_page_command" data-aos="zoom-out"
                                   data-aos-delay="1600"
                                   class="btn d-inline-flex align-items-center justify-content-center align-self-center">
                                    <span>${sign_in}</span>
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
