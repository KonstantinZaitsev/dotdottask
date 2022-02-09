<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../WEB-INF/import/import.jspf" %>

<fmt:message key="catalog.title" var="title"/>
<fmt:message key="catalog.projects.own.no" var="projects_own_no"/>
<fmt:message key="catalog.projects.invited.no" var="projects_invited_no"/>
<fmt:message key="catalog.account" var="account"/>
<fmt:message key="catalog.account.profile" var="account_profile"/>
<fmt:message key="catalog.account.sign-out" var="account_sign_out"/>
<fmt:message key="catalog.language" var="language"/>
<fmt:message key="catalog.image" var="image"/>
<fmt:message key="catalog.edit" var="edit"/>
<fmt:message key="catalog.name.invalid" var="name_invalid"/>
<fmt:message key="catalog.name" var="name"/>
<fmt:message key="catalog.surname.invalid" var="surname_invalid"/>
<fmt:message key="catalog.surname" var="surname"/>
<fmt:message key="catalog.email.invalid" var="email_invalid"/>
<fmt:message key="catalog.email.not-unique" var="email_not_unique"/>
<fmt:message key="catalog.email" var="email"/>
<fmt:message key="catalog.password.invalid" var="password_invalid"/>
<fmt:message key="catalog.password.mismatch" var="password_mismatch"/>
<fmt:message key="catalog.password" var="password"/>
<fmt:message key="catalog.confirm-password" var="confirm_password"/>

<jsp:useBean id="user" type="by.zaitsev.dotdottask.model.entity.User" scope="session"/>

<html>
<head>
    <link href="${abs}/image/favicon.ico" rel="icon">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,
    400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${abs}/css/catalog.css" rel="stylesheet">
    <title>${title}</title>
</head>
<body>
<main id="main">
    <div class="flex-shrink-0 p-3 sidebar">
        <a href="#" class="d-flex align-items-center pb-3 mb-3 link-dark text-decoration-none border-bottom">
            <svg class="bi me-2" width="30" height="24"></svg>
            <span class="fs-5 fw-semibold">../task</span>
        </a>
        <ul class="list-unstyled ps-0">
            <c:choose>
                <c:when test="${sessionScope.own_projects.size() eq 0}">
                    <span>${projects_own_no}</span>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${sessionScope.own_projects}" var="project">
                        <li class="mb-1">
                            <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse"
                                    data-bs-target="#project-${project.id}" aria-expanded="false">
                                    ${project.title}
                            </button>
                            <div class="collapse" id="project-${project.id}">
                                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small nav-tabs flex-column nav"
                                    role="tablist">
                                    <c:forEach items="${project.taskList}" var="task">
                                        <li class="nav-item"><a href="#task-info-${task.id}"
                                                                class="link-dark rounded nav-link" role="tab"
                                                                data-bs-toggle="tab">${task.title}</a></li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </li>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            <li class="border-top my-3"></li>
            <c:choose>
                <c:when test="${sessionScope.invited_projects.size() eq 0}">
                    <span>${projects_invited_no}</span>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${sessionScope.invited_projects}" var="project">
                        <li class="mb-1">
                            <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse"
                                    data-bs-target="#project-${project.id}" aria-expanded="false">
                                    ${project.title}
                            </button>
                            <div class="collapse" id="project-${project.id}">
                                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                    <c:forEach items="${project.taskList}" var="task">
                                        <li><a href="#" class="link-dark rounded">${task.title}</a></li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </li>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            <li class="border-top my-3"></li>
            <li class="mb-1">
                <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse"
                        data-bs-target="#account-collapse" aria-expanded="false">
                    ${account}
                </button>
                <div class="collapse" id="account-collapse">
                    <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                        <li><a href="#" data-bs-toggle="modal" data-bs-target="#modal-profile"
                               class="link-dark rounded">${account_profile}</a></li>
                        <li>
                            <a href="${abs}/controller?command=sign_out_command" class="link-dark rounded">
                                ${account_sign_out}
                            </a>
                        </li>
                    </ul>
                </div>
            <li class="mb-1">
                <button class="btn btn-toggle align-items-center rounded collapsed"
                        data-bs-toggle="collapse"
                        data-bs-target="#language-collapse" aria-expanded="false">
                    ${language}
                </button>
                <div class="collapse" id="language-collapse">
                    <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                        <li>
                            <a href="${abs}/controller?command=change_locale_command&locale=ru_RU">Русский</a>
                        </li>
                        <li>
                            <a href="${abs}/controller?command=change_locale_command&locale=en_US">English</a>
                        </li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
    <div class="modal fade" id="modal-profile" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="card profile-header">
                        <div class="body">
                            <div class="row">
                                <div class="col-lg-4 col-md-4 col-12">
                                    <div class="profile-image float-md-right">
                                        <img src="${user.image}" class="img-fluid" alt="">
                                        <button class="btn btn-toggle align-items-center rounded collapsed"
                                                data-bs-toggle="collapse"
                                                data-bs-target="#edit-image" aria-expanded="false">
                                            <i class="bi-pen"></i>
                                        </button>
                                        <div class="collapse" id="edit-image">
                                            <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                                <li>
                                                    <form action="${abs}/controller" method="post"
                                                          enctype="multipart/form-data">
                                                        <input type="hidden" name="command" value="edit_image_command">
                                                        <label for="image"></label>
                                                        <input type="file" name="image" id="image"
                                                               placeholder="${image}" multiple accept="image/jpeg">
                                                        <button type="submit" class="btn btn-primary btn-round">${edit}
                                                        </button>
                                                    </form>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-8 col-md-8 col-12">
                                    <h4 class="m-t-0 m-b-0">
                                        <strong>${user.name}</strong>
                                        <button class="btn btn-toggle align-items-center rounded collapsed"
                                                data-bs-toggle="collapse"
                                                data-bs-target="#edit-name" aria-expanded="false">
                                            <i class="bi-pen"></i>
                                            <c:if test="${requestScope.update_name_result eq 'invalid'}">
                                                <span style="color: #ff3d4a">${name_invalid}</span>
                                            </c:if>
                                        </button>
                                    </h4>
                                    <div class="collapse" id="edit-name">
                                        <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                            <li>
                                                <form action="${abs}/controller" method="post">
                                                    <input type="hidden" name="command" value="edit_name_command">
                                                    <label for="name"></label>
                                                    <input type="text" name="name" id="name" placeholder="${name}"
                                                           minlength="2" maxlength="30" pattern="[a-zA-Zа-яА-Я]+"
                                                           title="enter on alphabets only" required>
                                                    <button type="submit" class="btn btn-primary btn-round">${edit}
                                                    </button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                    <h4 class="m-t-0 m-b-0">
                                        <strong>${user.surname}</strong>
                                        <button class="btn btn-toggle align-items-center rounded collapsed"
                                                data-bs-toggle="collapse"
                                                data-bs-target="#edit-surname" aria-expanded="false">
                                            <i class="bi-pen"></i>
                                            <c:if test="${requestScope.update_surname_result eq 'invalid'}">
                                                <span style="color: #ff3d4a">${surname_invalid}</span>
                                            </c:if>
                                        </button>
                                    </h4>
                                    <div class="collapse" id="edit-surname">
                                        <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                            <li>
                                                <form action="${abs}/controller" method="post">
                                                    <input type="hidden" name="command" value="edit_surname_command">
                                                    <label for="surname"></label>
                                                    <input type="text" name="name" id="surname" placeholder="${surname}"
                                                           minlength="2" maxlength="30" pattern="[a-zA-Zа-яА-Я]+"
                                                           title="enter on alphabets only" required>
                                                    <button type="submit" class="btn btn-primary btn-round">${edit}
                                                    </button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                    <p>
                                        ${user.email}
                                        <button class="btn btn-toggle align-items-center rounded collapsed"
                                                data-bs-toggle="collapse"
                                                data-bs-target="#edit-email" aria-expanded="false">
                                            <i class="bi-pen"></i>
                                            <c:choose>
                                                <c:when test="${requestScope.update_email_result eq 'invalid'}">
                                                    <span style="color: #ff3d4a">${email_invalid}</span>
                                                </c:when>
                                                <c:when test="${requestScope.update_email_result eq 'not_unique'}">
                                                    <span style="color: #ff3d4a">${email_not_unique}</span>
                                                </c:when>
                                            </c:choose>
                                        </button>
                                    </p>
                                    <div class="collapse" id="edit-email">
                                        <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                            <li>
                                                <form action="${abs}/controller" method="post">
                                                    <input type="hidden" name="command" value="edit_email_command">
                                                    <label for="email"></label>
                                                    <input type="email" name="email" id="email" placeholder="${email}"
                                                           minlength="6" maxlength="50" required>
                                                    <button type="submit" class="btn btn-primary btn-round">${edit}
                                                    </button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                    <button class="btn btn-toggle align-items-center rounded collapsed"
                                            data-bs-toggle="collapse"
                                            data-bs-target="#edit-password" aria-expanded="false">
                                        Edit Password
                                        <c:choose>
                                            <c:when test="${requestScope.update_password_result eq 'invalid'}">
                                                <span style="color: #ff3d4a">${password_invalid}</span>
                                            </c:when>
                                            <c:when test="${requestScope.update_password_result eq 'mismatch'}">
                                                <span style="color: #ff3d4a">${password_mismatch}</span>
                                            </c:when>
                                        </c:choose>
                                    </button>
                                    <div class="collapse" id="edit-password">
                                        <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                            <li>
                                                <form action="${abs}/controller" method="post">
                                                    <input type="hidden" name="command" value="edit_password_command">
                                                    <label for="password"></label>
                                                    <input type="password" name="password" id="password"
                                                           placeholder="${password}" minlength="6"
                                                           maxlength="30" required>
                                                    <br>
                                                    <label for="confirmed_password"></label>
                                                    <input type="password" name="confirmed_password"
                                                           id="confirmed_password" placeholder="${confirm_password}"
                                                           minlength="6" maxlength="30" required>
                                                    <button type="submit" class="btn btn-primary btn-round">${edit}
                                                    </button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="tab-content">
        <c:forEach items="${sessionScope.own_projects}" var="project">
            <c:forEach items="${project.taskList}" var="task">
                <div id="task-info-${task.id}" class="tab-pane fade" role="tabpanel">
                    <div class="text-center">
                        <h1><strong>${task.title}</strong></h1>
                        <div class="border-top my-3"></div>
                        <div class="row">
                            <div class="col-6">
                                <h4><strong>Creation date:</strong></h4>
                                <p>${task.creationDate}</p>
                            </div>
                            <div class="col-6">
                                <h4><strong>Deadline:</strong></h4>
                                <p>${task.deadline}</p>
                            </div>
                            <div class="col-6">
                                <c:choose>
                                    <c:when test="${task.assignedUserId eq user.id}">
                                        <h4><strong>Assigned</strong></h4>
                                    </c:when>
                                    <c:otherwise>
                                        <h4><strong>Not Assigned</strong></h4>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="col-6">
                                <c:choose>
                                    <c:when test="${task.isDone()}">
                                        <h4><strong>Done</strong></h4>
                                    </c:when>
                                    <c:otherwise>
                                        <h4><strong>Not Done</strong></h4>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="border-top my-3"></div>
                    <div>
                        <p>${task.description}</p>
                    </div>
                </div>
            </c:forEach>
        </c:forEach>
    </div>

</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${abs}/js/catalog.js"></script>
</body>
</html>
