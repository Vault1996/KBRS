<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04.09.2016
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="lang" var="rb"/>

<html>
<head>
    <title><fmt:message key="title.editReview" bundle="${rb}"/></title>

    <%@include file="jspf/bootstrap.jspf"%>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<c:set var="page" value="/jsp/editReview.jsp" scope="session"/>
<%@include file="jspf/navigationBar.jspf"%>

<div class="container-fluid text-center">
    <div class="row content">
        <div class="col-sm-8 col-sm-offset-2 text-left">
            <ul class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}/controller?command=show_main_page"><fmt:message key="title.home" bundle="${rb}"/></a></li>
                <li><a href="#"><fmt:message key="title.editReview" bundle="${rb}"/></a></li>
            </ul>
            <h1>${review.user.login}</h1>
            <hr>
            <form method="POST" class="form-horizontal login-form" style="width: 700px;" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="edit_review">
                <input type="hidden" name="movie_id" value="${review.rating.movieId}">
                <input type="hidden" name="user_id" value="${review.user.userId}">
                <div class="form-group">
                    <label class="control-label"><fmt:message key="label.rating" bundle="${rb}"/>:</label>
                    <c:forEach var="i" begin="1" end="10">
                        <c:if test="${i == review.rating.rating}">
                            <label class="radio-inline"><input type="radio" name="rating" value="${i}" checked>${i}</label>
                        </c:if>
                        <c:if test="${i != review.rating.rating}">
                            <label class="radio-inline"><input type="radio" name="rating" value="${i}">${i}</label>
                        </c:if>
                    </c:forEach>
                </div>
                <div class="form-group">
                    <label class="control-label" for="review"><fmt:message key="label.review" bundle="${rb}"/>:</label>
                    <textarea class="form-control" name="review" rows="3" maxlength="512" id="review" autofocus>${review.review.review}</textarea>
                </div>
                <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="button.submit" bundle="${ rb }" />" />
            </form>
        </div>
    </div>
</div>
</body>
</html>
