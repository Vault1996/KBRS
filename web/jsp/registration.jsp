<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 19.08.2016
  Time: 12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="lang" var="rb"/>

<html>
    <head>
        <title> <fmt:message key="title.registration" bundle="${ rb }" /> </title>

        <%@include file="jspf/bootstrap.jspf"%>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    </head>
    <body>
        <c:set var="page" value="/jsp/registration.jsp" scope="session"/>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/controller?command=show_main_page">CinemaRating</a>
                </div>

                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#"><fmt:message key="button.language" bundle="${ rb }" />
                            <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="${pageContext.request.contextPath}/controller?command=change_language&language=en_en">English</a></li>
                            <li><a href="${pageContext.request.contextPath}/controller?command=change_language&language=ru_ru">Русский</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="container">
            <form method="POST" class="form-horizontal login-form" action="${pageContext.request.contextPath}/controller"
                  onsubmit="var elem = document.getElementById('password').value;
    document.getElementById('password').value = sha256(elem);">
                <input type="hidden" name="command" value="registration">
                <div class="form-group">
                    <label for="login" class="control-label compulsory">
                        <fmt:message key="label.login" bundle="${ rb }" />
                    </label>
                    <input id="login" type="text" name="login" value="${login}" class="form-control" required autofocus
                           minlength="1" maxlength="30" placeholder=<fmt:message key="label.login" bundle="${ rb }" /> />
                </div>
                <div class="form-group">
                    <label for="name" class="control-label compulsory">
                        <fmt:message key="label.name" bundle="${ rb }" />
                    </label>
                    <input id="name" type="text" name="name" value="${name}" class="form-control" required
                           minlength="1" maxlength="30" placeholder=<fmt:message key="label.name" bundle="${ rb }" /> />
                </div>
                <div class="form-group">
                    <label for="surname" class="control-label compulsory">
                        <fmt:message key="label.surname" bundle="${ rb }" />
                    </label>
                    <input id="surname" type="text" name="surname" value="${surname}" class="form-control" required
                           minlength="1" maxlength="30" placeholder=<fmt:message key="label.surname" bundle="${ rb }" /> />
                </div>
                <%-- ************************* --%>
                <div class="form-group">
                    <label for="email" class="control-label compulsory">
                        <fmt:message key="label.email" bundle="${ rb }" />
                    </label>
                    <input type="email" name="email" value="${email}" class="form-control" id="email" required
                           placeholder=<fmt:message key="label.email" bundle="${ rb }" /> />
                </div>
                <div class="form-group">
                    <label for="password" class="control-label compulsory">
                        <fmt:message key="label.password" bundle="${ rb }" />
                    </label>
                    <input id="password" type="password" name="password" value="${password}" class="form-control" required
                           minlength="4" maxlength="20" placeholder=<fmt:message key="label.password" bundle="${ rb }" /> />
                </div>
                <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="button.register" bundle="${ rb }" />" />
                <br/>
                <c:if test="${errorRegistrationValidation == true}">
                    <div class="alert alert-danger">
                        <span class="close" data-dismiss="alert">&times;</span>
                        <fmt:message key="message.registrationError" bundle="${ rb }" />
                    </div>
                </c:if>
                <c:if test="${errorRegistrationMessage == true}">
                    <div class="alert alert-danger">
                        <span class="close" data-dismiss="alert">&times;</span>
                        <fmt:message key="message.userOrEmailExists" bundle="${ rb }" />
                    </div>
                </c:if>
                <hr>
                <div class="text-center">
                    <ul class="pagination">
                        <li><a href="${pageContext.request.contextPath}/controller?command=redirect&next=path.page.login"><fmt:message key="button.login" bundle="${ rb }" /></a></li>
                        <li class = "active"><a href="#"><fmt:message key="button.registration" bundle="${ rb }" /></a></li>
                    </ul>
                </div>
            </form>
        </div>
    </body>
</html>