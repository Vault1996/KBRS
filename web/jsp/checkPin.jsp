<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 22.10.2016
  Time: 22:10
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 31.07.2016
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="lang" var="rb"/>

<html>
<head>
    <title> <fmt:message key="title.login" bundle="${ rb }" /> </title>

    <%@include file="jspf/bootstrap.jspf"%>

    <script src="${pageContext.request.contextPath}/js/sha256.js"></script>
    <script src="${pageContext.request.contextPath}/js/changeElemById.js"></script>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<c:set var="page" value="/jsp/login.jsp" scope="session"/>
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
<form method="POST" class="form-horizontal login-form" action="${pageContext.request.contextPath}/controller" onsubmit="var elem = document.getElementById('pin').value;
    document.getElementById('pin').value = sha256(elem);">
    <input type="hidden" name="command" value="check_pin">
    <input type="hidden" name="from" value="${from}">
    <input type="hidden" name="nextPage" value="${nextPage}">
    <div class="form-group">
        <label for="pin" class="control-label compulsory">
            <fmt:message key="label.pin" bundle="${ rb }" />
        </label>
        <input id="pin" type="password" name="pin" value="${password}" class="form-control" required
               pattern="[0-9]{4}" placeholder=<fmt:message key="label.pin" bundle="${ rb }" />  />
    </div>
    <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="button.enterPin" bundle="${ rb }" />" />
    <br/>
    <hr>
</form>
</body>
</html>