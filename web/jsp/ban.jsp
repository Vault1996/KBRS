<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 01.09.2016
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="lang" var="rb"/>

<html>
    <head>
        <title> <fmt:message key="title.ban" bundle="${ rb }" /> </title>

        <%@include file="jspf/bootstrap.jspf"%>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    </head>
    <body>
        <c:set var="page" value="/jsp/ban.jsp" scope="session"/>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/controller?command=show_main_page">CinemaRating</a>
                </div>

                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${empty activeUser or empty activeUser.role or activeUser.role.toString() eq 'GUEST'}">
                        <li><a href="${pageContext.request.contextPath}/controller?command=redirect&next=path.page.login"><span class="glyphicon glyphicon-log-in"></span><fmt:message key="button.login" bundle="${ rb }" /></a></li>
                    </c:if>
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
        <c:if test="${banMessageAddedStatus == true}">
            <div class="alert alert-info">
                <span class="close" data-dismiss="alert">&times;</span>
                <fmt:message key="message.banMessageAdded" bundle="${ rb }" />
            </div>
        </c:if>
        <c:if test="${banMessageExistsStatus == true}">
            <div class="alert alert-info">
                <span class="close" data-dismiss="alert">&times;</span>
                <fmt:message key="message.banMessageExists" bundle="${ rb }" />
            </div>
        </c:if>
        <form method="POST" class="form-horizontal login-form" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="ban_message">
            <input type="hidden" name="banId" value="${ban.banId}">
            <c:if test="${not empty ban}">
                <h4><fmt:message key="label.ban" bundle="${ rb }" /></h4>
                <h4><fmt:message key="label.timeLeft" bundle="${ rb }" /></h4>
                <c:forTokens items="${timeLeft}" delims=",.;" var="item">
                    <p>${item}</p>
                </c:forTokens>
                <h4><fmt:message key="label.reason" bundle="${ rb }" /></h4>
                <p>${ban.reason}</p>
            </c:if>
            <c:if test="${empty banMessage or empty banMessage.message}">
                <div class="form-group">
                    <label class="control-label" for="banMessage"><fmt:message key="label.messageToAdmin" bundle="${ rb }" /></label>
                    <textarea required class="form-control" name="banMessage" rows="3" maxlength="512" id="banMessage"></textarea>
                </div>
                <input class="btn btn-primary btn-block" type="submit" value=<fmt:message key="button.leaveMessage" bundle="${ rb }" /> />
            </c:if>
            <c:if test="${not empty banMessage and not empty banMessage.message}">
                <h4><fmt:message key="label.yourMessage" bundle="${rb}"/></h4>
                <p>${banMessage.message}</p>
            </c:if>
        </form>
    </body>
</html>