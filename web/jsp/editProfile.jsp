<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 30.08.2016
  Time: 19:45
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="lang" var="rb"/>

<html>
    <head>
        <title><fmt:message key="title.editProfile" bundle="${rb}"/></title>

        <%@include file="jspf/bootstrap.jspf"%>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    </head>
    <body>
        <c:set var="page" value="/jsp/editProfile.jsp" scope="session"/>
        <%@include file="jspf/navigationBar.jspf"%>

        <div class="container-fluid text-center">
            <div class="row content">
                <div class="col-sm-8 col-sm-offset-2 text-left">
                    <ul class="breadcrumb">
                        <li><a href="${pageContext.request.contextPath}/controller?command=show_main_page"><fmt:message key="title.home" bundle="${rb}"/></a></li>
                        <li><a href="#"><fmt:message key="title.editProfile" bundle="${rb}"/></a></li>
                    </ul>
                    <h1>${userEdited.login}</h1>
                    <hr>
                    <form method="POST" class="form-horizontal login-form clearfix" enctype="multipart/form-data" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="edit_profile"/>
                        <input type="hidden" name="user_id" value="${userEdited.userId}"/>
                        <div class="form-group">
                            <label for="photo" class="control-label compulsory">
                                <fmt:message key="label.photo" bundle="${ rb }" />
                            </label>
                            <input type="file" name="photo" class="form-control" id="photo" accept="image/png,image/jpeg"
                                   placeholder="<fmt:message key="label.photo" bundle="${ rb }" />" />
                        </div>
                        <div class="form-group">
                            <label for="name" class="control-label compulsory">
                                <fmt:message key="label.name" bundle="${ rb }" />
                            </label>
                            <input id="name" type="text" name="name" value="${userEdited.name}" class="form-control" autofocus
                                    maxlength="30" placeholder="<fmt:message key="label.name" bundle="${ rb }" />" />
                        </div>
                        <div class="form-group">
                            <label for="surname" class="control-label compulsory">
                                <fmt:message key="label.surname" bundle="${ rb }" />
                            </label>
                            <input id="surname" type="text" name="surname" value="${userEdited.surname}" class="form-control"
                                    maxlength="30" placeholder="<fmt:message key="label.surname" bundle="${ rb }" />" />
                        </div>
                        <div class="form-group">
                            <label for="newPassword" class="control-label compulsory">
                                <fmt:message key="label.newPassword" bundle="${ rb }" />
                            </label>
                            <input id="newPassword" type="password" name="newPassword" class="form-control"
                                   minlength="4" maxlength="20" placeholder="<fmt:message key="label.newPassword" bundle="${ rb }" />" />
                        </div>
                        <div class="form-group">
                            <label for="repeatPassword" class="control-label compulsory">
                                <fmt:message key="label.repeatPassword" bundle="${ rb }" />
                            </label>
                            <input id="repeatPassword" type="password" name="repeatPassword" class="form-control"
                                   minlength="4" maxlength="20" placeholder="<fmt:message key="label.repeatPassword" bundle="${ rb }" />" />
                        </div>
                        <c:if test="${invalidDataMessage == true}">
                            <div class="alert alert-danger">
                                <span class="close" data-dismiss="alert">&times;</span>
                                <fmt:message key="message.invalidData" bundle="${ rb }" />
                            </div>
                        </c:if>
                        <input class="btn btn-primary btn-block" type="submit" value="<fmt:message key="button.submit" bundle="${ rb }" />" />
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>

