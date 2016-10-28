<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 07.09.2016
  Time: 0:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="lang" var="rb"/>

<html>
<head>
    <title><fmt:message key="title.banUser" bundle="${rb}"/></title>

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
                <li><a href="#"><fmt:message key="title.banUser" bundle="${rb}"/></a></li>
            </ul>
            <h1>Ban ${banUser.login}</h1>
            <hr>
            <form method="POST" class="form-horizontal login-form" style="width: 700px;" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="ban_user">
                <input type="hidden" name="user_id" value="${banUser.userId}">
                <div class="form-group">
                    <label for="till" class="control-label compulsory">
                        <fmt:message key="label.till" bundle="${ rb }" />
                    </label>
                    <input id="till" type="datetime-local" name="till" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label class="control-label" for="reason"><fmt:message key="label.reason" bundle="${rb}"/></label>
                    <textarea class="form-control" name="reason" rows="3" required maxlength="512" id="reason"></textarea>
                </div>
                <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="button.submit" bundle="${ rb }" />" />
            </form>
        </div>
    </div>
</div>

<%@include file="jspf/footer.jspf"%>

</body>
</html>
