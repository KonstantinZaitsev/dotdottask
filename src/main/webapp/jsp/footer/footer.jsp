<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../WEB-INF/import/import.jspf" %>

<fmt:message key="footer.title" var="title"/>
<fmt:message key="footer.description" var="description"/>

<html>
<head>
    <link href="${abs}/css/footer.css" rel="stylesheet">
    <title>${title}</title>
</head>
<body>
<footer id="footer">
    <div class="container d-md-flex py-4">
        <div class="me-md-auto text-center text-md-start">
            ${description}<strong><span> Epam Java WD</span></strong>
        </div>
    </div>
</footer>
</body>
</html>
