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
<fmt:message key="catalog.add.project" var="add_project"/>
<fmt:message key="catalog.error.invalid-project" var="error_invalid_project"/>
<fmt:message key="catalog.edit.password" var="edit_password"/>
<fmt:message key="catalog.error.invalid-title" var="error_invalid_title"/>
<fmt:message key="catalog.error.invalid-description" var="error_invalid_description"/>
<fmt:message key="catalog.add.task" var="add_task"/>
<fmt:message key="catalog.error.invalid-task" var="error_invalid_task"/>
<fmt:message key="catalog.description" var="description"/>
<fmt:message key="catalog.deadline" var="deadline"/>
<fmt:message key="catalog.assigned-user" var="assigned_user_a"/>
<fmt:message key="catalog.add" var="add"/>
<fmt:message key="catalog.add.user" var="add_user"/>
<fmt:message key="catalog.error.invalid-user" var="error_invalid_user"/>
<fmt:message key="catalog.delete" var="delete"/>
<fmt:message key="catalog.creation-date" var="creation_date"/>
<fmt:message key="catalog.error.invalid-deadline" var="error_invalid_deadline"/>
<fmt:message key="catalog.error.invalid-assigned-id" var="error_invalid_assigned_user"/>
<fmt:message key="catalog.done" var="done"/>
<fmt:message key="catalog.not-done" var="not_done"/>
<fmt:message key="catalog.error.invalid-is-done" var="error_invalid_is_done"/>
<fmt:message key="catalog.change" var="change"/>
<fmt:message key="catalog.tags" var="tags"/>
<fmt:message key="catalog.error.invalid-tag-name" var="error_invalid_tag_name"/>
<fmt:message key="catalog.add.tag" var="add_tag"/>
<fmt:message key="catalog.error.invalid-tag" var="error_invalid_tag"/>
<fmt:message key="catalog.assigned" var="assigned"/>
<fmt:message key="catalog.not-assigned" var="not_assigned"/>

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
                            <a href="#" class="link-dark rounded" data-bs-toggle="modal"
                               data-bs-target="#modal-edit-project-${project.id}" aria-expanded="false">
                                <i class="bi-pen"></i>
                            </a>
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
            <li>
                <div>
                    <button class="btn btn-info">
                        <a href="#" style="text-decoration: none" class="link-dark rounded" data-bs-toggle="modal"
                           data-bs-target="#modal-add-project" aria-expanded="false">
                            ${add_project}
                        </a>
                    </button>
                    <c:if test="${requestScope.add_project_result eq 'invalid'}">
                        <span style="color: #ff3d4a">${error_invalid_project}</span>
                    </c:if>
                </div>
            </li>
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
                                                    <input type="text" name="surname" id="surname"
                                                           placeholder="${surname}" minlength="2" maxlength="30"
                                                           pattern="[a-zA-Zа-яА-Я]+" title="enter on alphabets only"
                                                           required>
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
                                        ${edit_password}
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
    <c:forEach items="${sessionScope.own_projects}" var="project">
        <div class="modal fade" id="modal-edit-project-${project.id}" tabindex="-1" aria-expanded="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="card profile-header">
                            <div class="row text-center">
                                <h4 class="m-t-0 m-b-0">
                                    <strong>${project.title}</strong>
                                    <button class="btn btn-toggle align-items-center rounded collapsed"
                                            data-bs-toggle="collapse"
                                            data-bs-target="#edit-project-title-${project.id}"
                                            aria-expanded="false">
                                        <i class="bi-pen"></i>
                                        <c:if test="${requestScope.update_project_title_result eq 'invalid'}">
                                            <span style="color: #ff3d4a">${error_invalid_title}</span>
                                        </c:if>
                                    </button>
                                </h4>
                                <div class="collapse" id="edit-project-title-${project.id}">
                                    <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                        <li>
                                            <form action="${abs}/controller" method="post">
                                                <input type="hidden" name="command"
                                                       value="edit_project_title_command">
                                                <input type="hidden" name="project_id" value="${project.id}">
                                                <label for="title-${project.id}"></label>
                                                <input type="text" name="title" id="title-${project.id}"
                                                       placeholder="${title}" minlength="2" max="100" required>
                                                <button type="submit" class="btn btn-primary btn-round">
                                                        ${edit}
                                                </button>
                                            </form>
                                        </li>
                                    </ul>
                                </div>
                                <p class="m-t-0 m-b-0">
                                        ${project.description}
                                    <button class="btn btn-toggle align-items-center rounded collapsed"
                                            data-bs-toggle="collapse"
                                            data-bs-target="#edit-project-description-${project.id}"
                                            aria-expanded="false">
                                        <i class="bi-pen"></i>
                                        <c:if test="${requestScope.update_project_descriptione_result eq 'invalid'}">
                                            <span style="color: #ff3d4a">${error_invalid_description}</span>
                                        </c:if>
                                    </button>
                                </p>
                                <div class="collapse" id="edit-project-description-${project.id}">
                                    <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                        <li>
                                            <form action="${abs}/controller" method="post">
                                                <input type="hidden" name="command"
                                                       value="edit_project_description_command">
                                                <input type="hidden" name="project_id" value="${project.id}">
                                                <label for="description-${project.id}"></label>
                                                <input type="text" name="description" id="description-${project.id}"
                                                       placeholder="${description}" minlength="2" max="500" required>
                                                <button type="submit" class="btn btn-primary btn-round">
                                                        ${edit}
                                                </button>
                                            </form>
                                        </li>
                                    </ul>
                                </div>
                                <c:forEach items="${project.taskList}" var="task">
                                    <div>
                                        <a style="text-decoration: none" href="#" class="link-dark rounded"
                                           data-bs-toggle="modal" data-bs-target="#modal-edit-task-${task.id}"
                                           aria-expanded="false">
                                                ${task.title} <i class="bi-pen"></i>
                                        </a>
                                    </div>
                                </c:forEach>
                                <div style="margin: 5px">
                                    <button class="btn btn-info">
                                        <a href="#" style="text-decoration: none" class="link-dark rounded"
                                           data-bs-toggle="modal"
                                           data-bs-target="#modal-add-task" aria-expanded="false">
                                                ${add_task}
                                        </a>
                                    </button>
                                    <c:if test="${requestScope.add_task_result eq 'invalid'}">
                                        <span style="color: #ff3d4a">${error_invalid_task}</span>
                                    </c:if>
                                </div>
                                <div class="modal fade" id="modal-add-task" tabindex="-1" aria-expanded="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-body">
                                                <div class="card profile-header">
                                                    <div class="row text-center">
                                                        <div class="m-t-0 m-b-0">
                                                            <form action="${abs}/controller" method="post">
                                                                <input type="hidden" name="command"
                                                                       value="add_task_command">
                                                                <input type="hidden" name="project_id"
                                                                       value="${project.id}">
                                                                <div>
                                                                    <label for="task-title-${project.id}">Title</label>
                                                                    <div>
                                                                        <input type="text" name="title"
                                                                               id="task-title-${project.id}"
                                                                               placeholder="${title}" minlength="2"
                                                                               maxlength="100" required>
                                                                    </div>
                                                                </div>
                                                                <br>
                                                                <div>
                                                                    <label for="task-description-${project.id}">${description}:</label>
                                                                    <div>
                                                                        <input type="text" name="description"
                                                                               id="task-description-${project.id}"
                                                                               placeholder="${description}"
                                                                               minlength="2" maxlength="500" required>
                                                                    </div>
                                                                </div>
                                                                <br>
                                                                <div>
                                                                    <label for="task-deadline-${project.id}">${deadline}:</label>
                                                                    <div>
                                                                        <input type="text" name="deadline"
                                                                               id="task-deadline-${project.id}"
                                                                               placeholder="yyyy-mm-dd hh:mm:ss"
                                                                               minlength="19"
                                                                               maxlength="19"
                                                                               required>
                                                                    </div>
                                                                </div>
                                                                <br>
                                                                <div>
                                                                    <label for="task-assigned-user-${project.id}">${assigned_user_a}</label>
                                                                    <div>
                                                                        <input type="email" name="assigned_user_email"
                                                                               id="task-assigned-user-${project.id}"
                                                                               placeholder="${email}"
                                                                               minlength="6" maxlength="50" required>
                                                                    </div>
                                                                </div>
                                                                <br>
                                                                <div>
                                                                    <button type="submit"
                                                                            class="btn btn-info btn-round">
                                                                            ${add}
                                                                    </button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:forEach items="${project.assignedUserList}" var="assigned_user">
                                    <div>
                                        <p>${assigned_user.email}
                                            <a href="${abs}/controller?command=delete_assigned_user_command&project_id=${project.id}&assigned_user_id=${assigned_user.id}"
                                               class="link-dark rounded">
                                                <i class="bi-x-circle"></i>
                                            </a>
                                        </p>
                                    </div>
                                </c:forEach>
                                <div style="margin: 5px">
                                    <button class="btn btn-info">
                                        <a href="#" style="text-decoration: none" class="link-dark rounded"
                                           data-bs-toggle="modal" data-bs-target="#modal-add-assigned-user"
                                           aria-expanded="false">
                                                ${add_user}
                                        </a>
                                    </button>
                                    <c:if test="${requestScope.add_assigned_user_result eq 'invalid'}">
                                        <span style="color: #ff3d4a">${error_invalid_user}</span>
                                    </c:if>
                                </div>
                                <div class="modal fade" id="modal-add-assigned-user" tabindex="-1" aria-expanded="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-body">
                                                <div class="card profile-header">
                                                    <div class="row text-center">
                                                        <div class="m-t-0 m-b-0">
                                                            <form action="${abs}/controller" method="post">
                                                                <input type="hidden" name="command"
                                                                       value="add_assigned_user_command">
                                                                <input type="hidden" name="project_id"
                                                                       value="${project.id}">
                                                                <div>
                                                                    <label for="user-email-${project.id}">${email}:</label>
                                                                    <div>
                                                                        <input type="email" name="email"
                                                                               id="user-email-${project.id}"
                                                                               placeholder="${email}" minlength="6"
                                                                               maxlength="50" required>
                                                                    </div>
                                                                </div>
                                                                <br>
                                                                <div>
                                                                    <button type="submit"
                                                                            class="btn btn-info btn-round">
                                                                            ${add}
                                                                    </button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div>
                                    <form action="${abs}/controller" method="post">
                                        <input type="hidden" name="command" value="delete_project_command">
                                        <input type="hidden" name="project_id" value="${project.id}">
                                        <button type="submit" class="btn btn-danger btn-round">
                                                ${delete}
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <c:forEach items="${project.taskList}" var="task">
            <div class="modal fade" id="modal-edit-task-${task.id}" tabindex="-1" aria-expanded="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-body">
                            <div class="card profile-header">
                                <div class="row text-center">

                                    <h4 class="m-t-0 m-b-0">
                                        <strong>${task.title}</strong>
                                        <button class="btn btn-toggle align-items-center rounded collapsed"
                                                data-bs-toggle="collapse"
                                                data-bs-target="#edit-task-title-${task.id}"
                                                aria-expanded="false">
                                            <i class="bi-pen"></i>
                                            <c:if test="${requestScope.update_task_title_result eq 'invalid'}">
                                                <span style="color: #ff3d4a">${error_invalid_title}</span>
                                            </c:if>
                                        </button>
                                    </h4>
                                    <div class="collapse" id="edit-task-title-${task.id}">
                                        <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                            <li>
                                                <form action="${abs}/controller" method="post">
                                                    <input type="hidden" name="command"
                                                           value="edit_task_title_command">
                                                    <input type="hidden" name="project_id" value="${project.id}">
                                                    <input type="hidden" name="task_id" value="${task.id}">
                                                    <label for="title-${task.id}"></label>
                                                    <input type="text" name="title" id="title-${task.id}"
                                                           placeholder="${title}" minlength="2" max="100" required>
                                                    <button type="submit" class="btn btn-primary btn-round">
                                                            ${edit}
                                                    </button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                    <p class="m-t-0 m-b-0">
                                            ${task.description}
                                        <button class="btn btn-toggle align-items-center rounded collapsed"
                                                data-bs-toggle="collapse"
                                                data-bs-target="#edit-task-description-${task.id}"
                                                aria-expanded="false">
                                            <i class="bi-pen"></i>
                                            <c:if test="${requestScope.update_task_description_result eq 'invalid'}">
                                                <span style="color: #ff3d4a">${error_invalid_description}</span>
                                            </c:if>
                                        </button>
                                    </p>
                                    <div class="collapse" id="edit-task-description-${task.id}">
                                        <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                            <li>
                                                <form action="${abs}/controller" method="post">
                                                    <input type="hidden" name="command"
                                                           value="edit_task_description_command">
                                                    <input type="hidden" name="project_id" value="${project.id}">
                                                    <input type="hidden" name="task_id" value="${task.id}">
                                                    <label for="description-${task.id}"></label>
                                                    <input type="text" name="description"
                                                           id="description-${task.id}" placeholder="${description}"
                                                           minlength="2" max="500" required>
                                                    <button type="submit" class="btn btn-primary btn-round">
                                                            ${edit}
                                                    </button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                    <h4 class="m-t-0 m-b-0">
                                        <strong>${creation_date}:</strong> ${task.creationDate}
                                    </h4>
                                    <h4 class="m-t-0 m-b-0">
                                        <strong>${deadline}:</strong> ${task.deadline}
                                        <button class="btn btn-toggle align-items-center rounded collapsed"
                                                data-bs-toggle="collapse"
                                                data-bs-target="#edit-task-deadline-${task.id}"
                                                aria-expanded="false">
                                            <i class="bi-pen"></i>
                                            <c:if test="${requestScope.update_task_deadline_result eq 'invalid'}">
                                                <span style="color: #ff3d4a">${error_invalid_deadline}</span>
                                            </c:if>
                                        </button>
                                    </h4>
                                    <div class="collapse" id="edit-task-deadline-${task.id}">
                                        <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                            <li>
                                                <form action="${abs}/controller" method="post">
                                                    <input type="hidden" name="command"
                                                           value="edit_task_deadline_command">
                                                    <input type="hidden" name="project_id" value="${project.id}">
                                                    <input type="hidden" name="task_id" value="${task.id}">
                                                    <label for="deadline-${task.id}"></label>
                                                    <input type="text" name="deadline" id="deadline-${task.id}"
                                                           placeholder="yyyy-mm-dd hh:mm:ss" minlength="19" max="19"
                                                           required>
                                                    <button type="submit" class="btn btn-primary btn-round">
                                                            ${edit}
                                                    </button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                    <c:choose>
                                        <c:when test="${user.id eq task.assignedUserId}">
                                            <h4 class="m-t-0 m-b-0">
                                                <strong>${assigned_user_a}:</strong> ${user.email}
                                                <button class="btn btn-toggle align-items-center rounded collapsed"
                                                        data-bs-toggle="collapse"
                                                        data-bs-target="#edit-task-assigned-id-${task.id}"
                                                        aria-expanded="false">
                                                    <i class="bi-pen"></i>
                                                    <c:if test="${requestScope.update_task_assigned_user_id_result eq 'invalid'}">
                                                        <span style="color: #ff3d4a">${error_invalid_assigned_user}</span>
                                                    </c:if>
                                                </button>
                                            </h4>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach items="${project.assignedUserList}" var="assigned_user">
                                                <c:if test="${assigned_user.id eq task.assignedUserId}">
                                                    <h4 class="m-t-0 m-b-0">
                                                        <strong>${assigned_user_a}:</strong> ${assigned_user.email}
                                                        <button class="btn btn-toggle align-items-center rounded collapsed"
                                                                data-bs-toggle="collapse"
                                                                data-bs-target="#edit-task-assigned-id-${task.id}"
                                                                aria-expanded="false">
                                                            <i class="bi-pen"></i>
                                                            <c:if test="${requestScope.update_task_assigned_user_id_result eq 'invalid'}">
                                                                <span style="color: #ff3d4a">${error_invalid_assigned_user}</span>
                                                            </c:if>
                                                        </button>
                                                    </h4>
                                                </c:if>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="collapse" id="edit-task-assigned-id-${task.id}">
                                        <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                            <li>
                                                <form action="${abs}/controller" method="post">
                                                    <input type="hidden" name="command"
                                                           value="edit_task_assigned_user_id_command">
                                                    <input type="hidden" name="project_id" value="${project.id}">
                                                    <input type="hidden" name="task_id" value="${task.id}">
                                                    <div>
                                                        <label for="edit-assigned-user-id-${task.id}">Email:</label>
                                                        <div>
                                                            <input type="email" id="edit-assigned-user-id-${task.id}"
                                                                   name="assigned_user_email" minlength="6"
                                                                   maxlength="50" placeholder="${email}" required>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <button type="submit" class="btn btn-primary btn-round">
                                                                ${edit}
                                                        </button>
                                                    </div>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                    <h4 class="m-t-0 m-b-0">
                                        <c:choose>
                                            <c:when test="${task.isDone() eq true}">
                                                <strong>${done}</strong>
                                            </c:when>
                                            <c:otherwise>
                                                <strong>${not_done}</strong>
                                            </c:otherwise>
                                        </c:choose>
                                        <button class="btn btn-toggle align-items-center rounded collapsed"
                                                data-bs-toggle="collapse"
                                                data-bs-target="#edit-task-is-done-${task.id}"
                                                aria-expanded="false">
                                            <i class="bi-pen"></i>
                                            <c:if test="${requestScope.update_task_is_done_result eq 'invalid'}">
                                                <span style="color: #ff3d4a">${error_invalid_is_done}</span>
                                            </c:if>
                                        </button>
                                    </h4>
                                    <div class="collapse" id="edit-task-is-done-${task.id}">
                                        <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                            <li>
                                                <form action="${abs}/controller" method="post">
                                                    <input type="hidden" name="command"
                                                           value="edit_task_is_done_command">
                                                    <input type="hidden" name="project_id" value="${project.id}">
                                                    <input type="hidden" name="task_id" value="${task.id}">
                                                    <c:choose>
                                                        <c:when test="${task.isDone() eq true}">
                                                            <input type="hidden" name="is_done" value="false">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="hidden" name="is_done" value="true">
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <button type="submit" class="btn btn-primary btn-round">
                                                            ${change}
                                                    </button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                    <h4>${tags}:</h4>
                                    <div class="row align-items-start">
                                        <c:forEach items="${task.tagList}" var="tag">
                                            <div class="col-md-6">
                                                <div class="alert alert-info text-center"
                                                     style="width: 110%; margin: 5px"
                                                     role="alert">
                                                        ${tag.name}
                                                    <button class="btn btn-toggle align-items-center rounded collapsed"
                                                            data-bs-toggle="collapse"
                                                            data-bs-target="#edit-tag-name-${tag.id}"
                                                            aria-expanded="false">
                                                        <i class="bi-pen"></i>
                                                        <c:if test="${requestScope.update_tag_name_result eq 'invalid'}">
                                                            <span style="color: #ff3d4a">${error_invalid_tag_name}</span>
                                                        </c:if>
                                                    </button>
                                                    <form action="${abs}/controller" method="post">
                                                        <input type="hidden" name="command"
                                                               value="delete_tag_command">
                                                        <input type="hidden" name="project_id"
                                                               value="${project.id}">
                                                        <input type="hidden" name="task_id" value="${task.id}">
                                                        <input type="hidden" name="tag_id" value="${tag.id}">
                                                        <button type="submit"
                                                                class="btn btn-toggle align-items-center rounded">
                                                            <i class="bi-x-circle"></i>
                                                        </button>
                                                    </form>
                                                </div>
                                                <div class="collapse" id="edit-tag-name-${tag.id}">
                                                    <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                                        <li>
                                                            <form action="${abs}/controller" method="post">
                                                                <input type="hidden" name="command"
                                                                       value="edit_tag_name_command">
                                                                <input type="hidden" name="project_id"
                                                                       value="${project.id}">
                                                                <input type="hidden" name="task_id"
                                                                       value="${task.id}">
                                                                <input type="hidden" name="tag_id"
                                                                       value="${tag.id}">
                                                                <label for="name-${tag.id}"></label>
                                                                <input type="text" name="name" id="name-${tag.id}"
                                                                       placeholder="${name}" minlength="2"
                                                                       maxlength="30"
                                                                       required>
                                                                <button type="submit"
                                                                        class="btn btn-primary btn-round">
                                                                        ${edit}
                                                                </button>
                                                            </form>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </c:forEach>
                                        <div style="margin: 5px">
                                            <button class="btn btn-info">
                                                <a href="#" style="text-decoration: none" class="link-dark rounded"
                                                   data-bs-toggle="modal" data-bs-target="#modal-add-tag"
                                                   aria-expanded="false">
                                                        ${add_tag}
                                                </a>
                                            </button>
                                            <c:if test="${requestScope.add_tag_result eq 'invalid'}">
                                                <span style="color: #ff3d4a">${error_invalid_tag}</span>
                                            </c:if>
                                        </div>
                                        <div class="modal fade" id="modal-add-tag" tabindex="-1" aria-expanded="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-body">
                                                        <div class="card profile-header">
                                                            <div class="row text-center">
                                                                <div class="m-t-0 m-b-0">
                                                                    <form action="${abs}/controller" method="post">
                                                                        <input type="hidden" name="command"
                                                                               value="add_tag_command">
                                                                        <input type="hidden" name="project_id"
                                                                               value="${project.id}">
                                                                        <input type="hidden" name="task_id"
                                                                               value="${task.id}">
                                                                        <div>
                                                                            <label for="tag-name-${task.id}">Name</label>
                                                                            <div>
                                                                                <input type="text" name="name"
                                                                                       id="tag-name-${task.id}"
                                                                                       placeholder="${name}"
                                                                                       minlength="2"
                                                                                       maxlength="30" required>
                                                                            </div>
                                                                        </div>
                                                                        <br>
                                                                        <div>
                                                                            <button type="submit"
                                                                                    class="btn btn-info btn-round">
                                                                                    ${add}
                                                                            </button>
                                                                        </div>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div>
                                        <form action="${abs}/controller" method="post">
                                            <input type="hidden" name="command"
                                                   value="delete_task_command">
                                            <input type="hidden" name="project_id"
                                                   value="${project.id}">
                                            <input type="hidden" name="task_id" value="${task.id}">
                                            <button type="submit" class="btn btn-danger">
                                                    ${delete}
                                            </button>
                                        </form>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:forEach>

    <div class="modal fade" id="modal-add-project" tabindex="-1" aria-expanded="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="card profile-header">
                        <div class="row text-center">
                            <div class="m-t-0 m-b-0">
                                <form action="${abs}/controller" method="post">
                                    <input type="hidden" name="command" value="add_project_command">
                                    <input type="hidden" name="owner_id" value="${user.id}">
                                    <div>
                                        <label for="project-title">Title:</label>
                                        <div>
                                            <input type="text" name="title" id="project-title" placeholder="${title}"
                                                   minlength="2" maxlength="100" required>
                                        </div>
                                    </div>
                                    <br>
                                    <div>
                                        <label for="project-description">Description:</label>
                                        <div>
                                            <input type="text" name="description" id="project-description"
                                                   placeholder="${description}"
                                                   minlength="2"
                                                   maxlength="500" required>
                                        </div>
                                    </div>
                                    <br>
                                    <div>
                                        <button type="submit" class="btn btn-info btn-round">
                                            ${add}
                                        </button>
                                    </div>
                                </form>
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
                        <h1 style="font-style: italic"><strong>${project.title}</strong></h1>
                        <p>${project.description}</p>
                        <div class="border-top my-3"></div>
                        <h1><strong>${task.title}</strong></h1>
                        <div class="border-top my-3"></div>
                        <div class="row">
                            <div class="col-6">
                                <h4><strong>${creation_date}:</strong></h4>
                                <p>${task.creationDate}</p>
                            </div>
                            <div class="col-6">
                                <h4><strong>${deadline}:</strong></h4>
                                <p>${task.deadline}</p>
                            </div>
                            <div class="col-6">
                                <c:choose>
                                    <c:when test="${task.assignedUserId eq user.id}">
                                        <h4><strong>${assigned}</strong></h4>
                                    </c:when>
                                    <c:otherwise>
                                        <h4><strong>${not_assigned}</strong></h4>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="col-6">
                                <c:choose>
                                    <c:when test="${task.isDone()}">
                                        <h4><strong>${done}</strong></h4>
                                    </c:when>
                                    <c:otherwise>
                                        <h4><strong>${not_done}</strong></h4>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="border-top my-3"></div>
                    <div class="row align-items-start">
                        <c:forEach items="${task.tagList}" var="tag">
                            <div class="alert alert-info text-center" style="width: 40%; margin: 20px" role="alert">
                                    ${tag.name}
                            </div>
                        </c:forEach>
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
