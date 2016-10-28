<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 29.08.2016
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="lang" var="rb"/>

<html>
<head>
    <title>${user.login}</title>

    <%@include file="jspf/bootstrap.jspf"%>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<c:set var="page" value="/jsp/user.jsp" scope="session"/>
<%@include file="jspf/navigationBar.jspf"%>

<div class="container-fluid text-center">
    <div class="row content">
        <div class="col-sm-8 col-sm-offset-2 text-left">
            <ul class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}/controller?command=show_main_page"><fmt:message key="title.home" bundle="${ rb }" /></a></li>
                <li><a href="#">${user.login}</a></li>
            </ul>
            <c:if test="${userBannedStatus == true}">
                <div class="alert alert-info">
                    <span class="close" data-dismiss="alert">&times;</span>
                    <fmt:message key="message.userBanned" bundle="${ rb }" />
                </div>
            </c:if>
            <c:if test="${unbanUserStatus == true}">
                <div class="alert alert-info">
                    <span class="close" data-dismiss="alert">&times;</span>
                    <fmt:message key="message.userUnbanned" bundle="${ rb }" />
                </div>
            </c:if>
            <h1>${user.login}</h1>
            <hr>
            <div class="clearfix">
                <img class = "main img-rounded img-thumbnail" src = "${pageContext.request.contextPath}/${user.photo}" alt="${user.login}"/>
                <h3><fmt:message key="label.registrationDate" bundle="${ rb }" />: <small><fmt:formatDate value="${user.createDate}" type="date"/> </small></h3>
                <h3><fmt:message key="label.status" bundle="${ rb }" />: <small>${user.status}</small></h3>
                <h3><fmt:message key="label.name" bundle="${ rb }" />: <small>${user.name}</small></h3>
                <h3><fmt:message key="label.surname" bundle="${ rb }" />: <small>${user.surname}</small></h3>
            </div>
            <ctg:admin>
                <div class="clearfix">
                    <c:if test="${isBanned}">
                        <form style="float: left;" method="POST" class="form-horizontal" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="unban_user"/>
                            <input type="hidden" name="user_id" value="${user.userId}"/>
                            <input class="btn btn-primary" type="submit" value="<fmt:message key="button.unbanUser" bundle="${ rb }" />" />
                        </form>
                    </c:if>
                    <c:if test="${not isBanned}">
                        <form style="float: left;" method="POST" class="form-horizontal" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="show_ban_user"/>
                            <input type="hidden" name="user_id" value="${user.userId}"/>
                            <input class="btn btn-primary" type="submit" value="<fmt:message key="button.banUser" bundle="${ rb }" />" />
                        </form>
                    </c:if>
                    <form style="float: right;" method="POST" class="form-horizontal" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="delete_user"/>
                        <input type="hidden" name="user_id" value="${user.userId}"/>
                        <input class="btn btn-primary" type="submit" value="<fmt:message key="button.deleteUser" bundle="${ rb }" />" />
                    </form>
                </div>
            </ctg:admin>
            <hr>
        </div>
    </div>
</div>

<%@include file="jspf/footer.jspf"%>

</body>
</html>