<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 06.09.2016
  Time: 23:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="lang" var="rb"/>

<html>
    <head>
        <title><fmt:message key="title.editMovie" bundle="${rb}"/></title>

        <%@include file="jspf/bootstrap.jspf"%>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    </head>
    <body>
    <c:set var="page" value="/jsp/editMovie.jsp" scope="session"/>
    <%@include file="jspf/navigationBar.jspf"%>

    <div class="container-fluid text-center">
        <div class="row content">
            <div class="col-sm-8 col-sm-offset-2 text-left">
                <ul class="breadcrumb">
                    <li><a href="${pageContext.request.contextPath}/controller?command=show_main_page"><fmt:message key="title.home" bundle="${rb}"/></a></li>
                    <li><a href="#"><fmt:message key="title.editMovie" bundle="${rb}"/></a></li>
                </ul>
                <c:if test="${movieAddedStatus == true}">
                    <div class="alert alert-info">
                        <span class="close" data-dismiss="alert">&times;</span>
                        <fmt:message key="message.movieAdded" bundle="${ rb }" />
                    </div>
                </c:if>
                <h1><fmt:message key="title.editMovie" bundle="${rb}"/></h1>
                <hr>
                <form method="POST" class="form-horizontal login-form" enctype="multipart/form-data" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="edit_movie"/>
                    <input type="hidden" name="movie_id" value="${movieEdited.movieId}"/>
                    <div class="form-group">
                        <label for="movieName" class="control-label compulsory">
                            <fmt:message key="label.movieName" bundle="${ rb }" />
                        </label>
                        <input id="movieName" type="text" name="name" value="${movieEdited.name}" class="form-control"  autofocus
                               maxlength="30" placeholder="<fmt:message key="label.movieName" bundle="${ rb }" />" />
                    </div>
                    <div class="form-group">
                        <label for="year" class="control-label compulsory">
                            <fmt:message key="label.year" bundle="${ rb }" />
                        </label>
                        <input id="year" type="text" name="year" value="${movieEdited.year}" class="form-control" pattern="[12](\d){3}"
                               maxlength="30" placeholder="<fmt:message key="label.year" bundle="${ rb }" />" />
                    </div>
                    <div class="form-group">
                        <label for="country" class="control-label compulsory">
                            <fmt:message key="label.country" bundle="${ rb }" />
                        </label>
                        <input id="country" type="text" name="country" value="${movieEdited.country}" class="form-control"
                               maxlength="30" placeholder="<fmt:message key="label.country" bundle="${ rb }" />" />
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="description"><fmt:message key="label.description" bundle="${ rb }" /></label>
                        <textarea class="form-control" name="description" rows="3" maxlength="512" id="description">${movieEdited.description}</textarea>
                    </div>
                    <div class="form-group">
                        <label for="poster" class="control-label compulsory">
                            <fmt:message key="label.poster" bundle="${ rb }" />
                        </label>
                        <input type="file" name="poster" class="form-control" id="poster" accept="image/png,image/jpeg"
                               placeholder=<fmt:message key="label.photo" bundle="${ rb }" /> />
                    </div>
                    <c:if test="${invalidDataMessage == true}">
                        <div class="alert alert-danger">
                            <span class="close" data-dismiss="alert">&times;</span>
                            <fmt:message key="message.invalidData" bundle="${ rb }" />
                        </div>
                    </c:if>
                    <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="button.submit" bundle="${ rb }" />" />
                    <br/>
                </form>
            </div>
        </div>
    </div>
    </body>
</html>

