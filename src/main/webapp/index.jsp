<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="WEB-INF/import/import.jspf" %>

<fmt:message key="index.hero.message.first" var="hero_message_first"/>
<fmt:message key="index.hero.message.second" var="hero_message_second"/>
<fmt:message key="index.hero.button.get-started" var="hero_button_get_started"/>
<fmt:message key="index.about.message.first" var="about_message_first"/>
<fmt:message key="index.about.message.second" var="about_message_second"/>
<fmt:message key="index.about.message.third" var="about_message_third"/>
<fmt:message key="index.about.button.read-more" var="about_button_read_more"/>
<fmt:message key="index.values.header.message.first" var="values_header_message_first"/>
<fmt:message key="index.values.header.message.second" var="values_header_message_second"/>
<fmt:message key="index.values.box.first.message.first" var="values_box_first_message_first"/>
<fmt:message key="index.values.box.first.message.second" var="values_box_first_message_second"/>
<fmt:message key="index.values.box.second.message.first" var="values_box_second_message_first"/>
<fmt:message key="index.values.box.second.message.second" var="values_box_second_message_second"/>
<fmt:message key="index.values.box.third.message.first" var="values_box_third_message_first"/>
<fmt:message key="index.values.box.third.message.second" var="values_box_third_message_second"/>

<html>
<head>
    <link href="image/favicon.ico" rel="icon">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,
    400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://unpkg.com/aos@next/dist/aos.css" rel="stylesheet"/>
    <link href="css/index.css" rel="stylesheet">
    <title>../task</title>
</head>
<body>
<header>
    <c:import url="jsp/header/header.jsp"/>
</header>

<section id="hero" class="hero d-flex align-items-center">
    <div class="container">
        <div class="row">
            <div class="col-lg-6 d-flex flex-column justify-content-center">
                <h1 data-aos="fade-up">${hero_message_first}</h1>
                <h2 data-aos="fade-up" data-aos-delay="400">${hero_message_second}</h2>
                <div data-aos="fade-up" data-aos-delay="600">
                    <div class="text-center text-lg-start">
                        <a href="#"
                           class="btn-get-started d-inline-flex align-items-center justify-content-center align-self-center">
                            <span>${hero_button_get_started}</span>
                            <i class="bi-arrow-right"></i>
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-lg-6 hero-img" data-aos="zoom-out" data-aos-delay="200">
                <img src="image/hero-img.png" class="img-fluid" alt="">
            </div>
        </div>
    </div>
</section>

<main id="main">
    <section id="about" class="about">
        <div class="container" data-aos="fade-up">
            <div class="row gx-0">
                <div class="col-lg-6 d-flex flex-column justify-content-center" data-aos="fade-up"
                     data-aos-delay="200">
                    <div class="content">
                        <h3>${about_message_first}</h3>
                        <h2>${about_message_second}</h2>
                        <p>${about_message_third}</p>
                        <div class="text-center text-lg-start">
                            <a href="#values"
                               class="btn-read-more d-inline-flex align-items-center justify-content-center align-self-center">
                                <span>${about_button_read_more}</span>
                                <i class="bi-arrow-right"></i>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 d-flex align-items-center" data-aos="zoom-out" data-aos-delay="200">
                    <img src="image/about.jpg" class="img-fluid" alt="">
                </div>
            </div>
        </div>
    </section>
    <section id="values" class="values">
        <div class="container" data-aos="fade-up">
            <header class="section-header">
                <h2>${values_header_message_first}</h2>
                <p>${values_header_message_second}</p>
            </header>
            <div class="row">
                <div class="col-lg-4" data-aos="fade-up" data-aos-delay="200">
                    <div class="box">
                        <img src="image/values/values-1.png" class="img-fluid" alt="">
                        <h3>${values_box_first_message_first}</h3>
                        <p>${values_box_first_message_second}</p>
                    </div>
                </div>
                <div class="col-lg-4 mt-4 mt-lg-0" data-aos="fade-up" data-aos-delay="400">
                    <div class="box">
                        <img src="image/values/values-2.png" class="img-fluid" alt="">
                        <h3>${values_box_second_message_first}</h3>
                        <p>${values_box_second_message_second}</p>
                    </div>
                </div>
                <div class="col-lg-4 mt-4 mt-lg-0" data-aos="fade-up" data-aos-delay="600">
                    <div class="box">
                        <img src="image/values/values-3.png" class="img-fluid" alt="">
                        <h3>${values_box_third_message_first}</h3>
                        <p>${values_box_third_message_second}</p>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <footer>
        <c:import url="jsp/footer/footer.jsp"/>
    </footer>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://unpkg.com/aos@next/dist/aos.js"></script>
<script src="js/index.js"></script>
</body>
</html>
