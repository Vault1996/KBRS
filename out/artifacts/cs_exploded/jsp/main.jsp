<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 31.07.2016
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="lang" var="rb"/>

<html>
    <head>
        <title><fmt:message key="title.home" bundle="${rb}"/></title>

        <%@include file="jspf/bootstrap.jspf"%>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    </head>
    <body>
        <c:set var="page" value="/jsp/main.jsp" scope="session"/>
        <%@include file="jspf/navigationBar.jspf"%>

        <div class="container-fluid text-center">
            <div class="row content">
                <div class="col-sm-2 text-center">
                    <c:if test="${not empty lastMovie}">
                        <h3><fmt:message key="label.lastMovie" bundle="${rb}"/></h3>
                        <hr>
                        <h3>
                            <a href="${pageContext.request.contextPath}/controller?command=show_movie&movie_id=${lastMovie.movieId}">
                                    ${lastMovie.name}
                            </a>
                        </h3>
                        <img class = "mini img-rounded img-thumbnail" src = "${pageContext.request.contextPath}/${lastMovie.poster}" alt="${lastMovie.name}"/>
                    </c:if>
                </div>
                <div class="col-sm-8 text-left">
                    <ul class="breadcrumb">
                        <li><a href="${pageContext.request.contextPath}/controller?command=show_main_page"><fmt:message key="title.home" bundle="${rb}"/></a></li>
                    </ul>
                    <c:if test="${movieDeletedStatus == true}">
                        <div class="alert alert-info">
                            <span class="close" data-dismiss="alert">&times;</span>
                            <fmt:message key="message.movieDeleted" bundle="${ rb }" />
                        </div>
                    </c:if>
                    <c:if test="${deleteUserStatus == true}">
                        <div class="alert alert-info">
                            <span class="close" data-dismiss="alert">&times;</span>
                            <fmt:message key="message.userDeleted" bundle="${ rb }" />
                        </div>
                    </c:if>

                    <h1><fmt:message key="label.welcome" bundle="${rb}"/></h1>
                    <p><fmt:message key="label.welcomeMessage" bundle="${rb}"/></p>
                    <hr>
                    <h3><fmt:message key="label.topThree" bundle="${rb}"/></h3>
                    <div id="myCarousel" class="carousel slide" data-ride="carousel">
                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <c:forEach items="${topMovies}" varStatus="status">
                                <c:if test="${status.index == 0}">
                                    <li data-target="#myCarousel" data-slide-to="${status.index}" class="active"></li>
                                </c:if>
                                <c:if test="${status.index != 0}">
                                    <li data-target="#myCarousel" data-slide-to="${status.index}"></li>
                                </c:if>
                            </c:forEach>
                        </ol>

                        <!-- Wrapper for slides -->
                        <div class="carousel-inner" role="listbox">
                            <c:forEach var="movie" items="${topMovies}" varStatus="status">
                                <c:if test="${status.index == 0}" >
                                    <div class="item active">
                                        <a href="${pageContext.request.contextPath}/controller?command=show_movie&movie_id=${movie.movieId}">
                                            <img src="${pageContext.request.contextPath}/${movie.poster}" alt="${movie.name}">
                                        </a>
                                    </div>
                                </c:if>
                                <c:if test="${status.index != 0}" >
                                    <div class="item">
                                        <a href="${pageContext.request.contextPath}/controller?command=show_movie&movie_id=${movie.movieId}">
                                            <img src="${pageContext.request.contextPath}/${movie.poster}" alt="${movie.name}">
                                        </a>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>

                        <!-- Left and right controls -->
                        <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                            <span class="sr-only"><fmt:message key="label.previous" bundle="${rb}"/></span>
                        </a>
                        <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                            <span class="sr-only"><fmt:message key="label.next" bundle="${rb}"/></span>
                        </a>
                    </div>
                </div>
                    <ctg:admin>
                        <div class="col-sm-2 sidenav">
                            <div class="well text-center">
                                <form method="GET" class="form-horizontal" action="${pageContext.request.contextPath}/controller">
                                    <input type="hidden" name="command" value="redirect"/>
                                    <input type="hidden" name="next" value="path.page.addMovie"/>
                                    <input class="btn btn-primary" type="submit" value="<fmt:message key="button.addMovie" bundle="${ rb }" />" />
                                </form>
                            </div>
                            <div class="well text-center">
                                <form method="GET" class="form-horizontal" action="${pageContext.request.contextPath}/controller">
                                    <input type="hidden" name="command" value="show_ban_messages"/>
                                    <input class="btn btn-primary" type="submit" value="<fmt:message key="button.showBanMessages" bundle="${ rb }" />" />
                                </form>
                            </div>
                        </div>
                    </ctg:admin>
            </div>
        </div>
        <%@include file="jspf/footer.jspf"%>
    </body>
</html>
