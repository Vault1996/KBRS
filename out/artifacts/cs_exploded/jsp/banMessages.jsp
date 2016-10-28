<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05.09.2016
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="lang" var="rb"/>

<html>
<head>
    <title><fmt:message key="title.banMessages" bundle="${rb}"/></title>

    <%@include file="jspf/bootstrap.jspf"%>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<c:set var="page" value="/jsp/banMessages.jsp" scope="session"/>
<%@include file="jspf/navigationBar.jspf"%>

<div class="container-fluid text-center">
    <div class="row content">
        <div class="col-sm-8 col-sm-offset-2 text-left">
            <ul class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}/controller?command=show_main_page"><fmt:message key="title.home" bundle="${rb}"/></a></li>
                <li><a href="#"><fmt:message key="title.banMessages" bundle="${rb}"/>s</a></li>
            </ul>

            <h1><fmt:message key="title.banMessages" bundle="${rb}"/></h1>

            <c:if test="${banMessages.isEmpty()}">
                <h3><fmt:message key="message.noBanMessages" bundle="${rb}"/></h3>
            </c:if>

            <c:if test="${not banMessages.isEmpty()}">
                <div class="text-center">
                    <form method="POST" class="form-horizontal" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="delete_all_ban_messages"/>
                        <input class="btn btn-primary" type="submit" value="<fmt:message key="button.deleteMessages" bundle="${ rb }" />" />
                    </form>
                </div>
            </c:if>

            <c:set var="numberOfElementsOnPage" value="5" scope="page"/>

            <c:forEach var="message" items="${banMessages}" begin="${pageNumber * numberOfElementsOnPage}" end="${pageNumber * numberOfElementsOnPage + numberOfElementsOnPage - 1}">
                <div class="container-fluid">
                    <div class="picture col-sm-2 col-sm-offset-2">
                        <img src="${pageContext.request.contextPath}/${message.user.photo}" class="img-rounded img-thumbnail mini" alt="user"/>
                    </div>
                    <div class="col-sm-6 text-container">
                        <a href="${pageContext.request.contextPath}/controller?command=show_user&user_id=${message.user.userId}">
                            <h2><c:out value="${message.user.login}"/></h2>
                        </a>
                        <p>${message.banMessage.message}</p>
                    </div>
                </div>
                <hr>
            </c:forEach>
            <c:if test="${not banMessages.isEmpty()}">
                <ctg:pagination numberOfElements="${banMessages.size()}" numberOfElementsOnPage="${numberOfElementsOnPage}"/>
            </c:if>
        </div>
    </div>
</div>
<%@include file="jspf/footer.jspf"%>
</body>
</html>

