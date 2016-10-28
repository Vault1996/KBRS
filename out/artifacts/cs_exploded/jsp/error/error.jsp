<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 31.07.2016
  Time: 19:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="lang" var="rb"/>

<html>
<head>
    <title>Exception</title>

    <%@include file="/jsp/jspf/bootstrap.jspf"%>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>

<div class="container-fluid text-center">
    <%@include file="/jsp/jspf/navigationBar.jspf"%>
    <div class="row content">
        <div class="col-sm-2 sidenav">

        </div>
        <div class="col-sm-8 text-left">
            <h1>Exception</h1>
            <p>
                ${exception}
            </p>
            <p>
                ${pageContext.errorData.throwable}
            </p>
        </div>
        <div class="col-sm-2 sidenav">

        </div>
    </div>
</div>
<%@include file="/jsp/jspf/footer.jspf"%>
</body>
</html>
