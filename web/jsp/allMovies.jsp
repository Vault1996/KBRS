<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 27.08.2016
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="lang" var="rb"/>

<html>
    <head>
        <title><fmt:message key="title.movies" bundle="${ rb }" /></title>

        <%@include file="jspf/bootstrap.jspf" %>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    </head>
    <body>
        <c:set var="page" value="/jsp/allMovies.jsp" scope="session"/>
        <%@include file="jspf/navigationBar.jspf" %>

        <div class="container-fluid text-center">
            <div class="row content">
                <div class="col-sm-8 col-sm-offset-2 text-left">
                    <ul class="breadcrumb">
                        <li><a href="${pageContext.request.contextPath}/controller?command=show_main_page"><fmt:message key="title.home" bundle="${ rb }" /></a></li>
                        <c:if test="${sortBy eq 'name'}">
                            <li><a href="#"><fmt:message key="title.allMovies" bundle="${ rb }" /></a></li>
                        </c:if>
                        <c:if test="${sortBy eq 'rating'}">
                            <li><a href="#"><fmt:message key="title.topMovies" bundle="${ rb }" /></a></li>
                        </c:if>
                    </ul>

                    <c:if test="${sortBy eq 'name'}">
                        <h1><a name="movies"></a><fmt:message key="title.allMovies" bundle="${ rb }" /></h1>
                    </c:if>
                    <c:if test="${sortBy eq 'rating'}">
                        <h1><a name="movies"></a><fmt:message key="title.topMovies" bundle="${ rb }" /></h1>
                    </c:if>

                    <c:if test="${movies.isEmpty()}">
                        <h3><fmt:message key="message.moviesNotFound" bundle="${ rb }" /></h3>
                    </c:if>

                    <c:set var="numberOfElementsOnPage" value="5" scope="page"/>

                    <c:forEach var="movie" items="${movies}" begin="${pageNumber * numberOfElementsOnPage}"
                               end="${pageNumber * numberOfElementsOnPage + numberOfElementsOnPage - 1}">
                        <div class="container-fluid">
                            <div class="picture col-sm-4">
                                <img src="${pageContext.request.contextPath}/${movie.poster}"
                                     class="img-rounded img-thumbnail mini" alt="movie">
                            </div>
                            <div class="col-sm-6 text-container">
                                <a href="${pageContext.request.contextPath}/controller?command=show_movie&movie_id=${movie.movieId}">
                                    <h2><c:out value="${movie.name}"/></h2>
                                </a>
                                <h4 class="sub-text">
                                    <fmt:message key="label.rating" bundle="${rb}"/>: ${movie.rating}
                                </h4>
                                <div class="justify"><c:out value="${movie.description}"/></div>
                            </div>
                        </div>
                        <hr>
                    </c:forEach>
                    <c:if test="${not movies.isEmpty()}">
                        <ctg:pagination numberOfElements="${movies.size()}" numberOfElementsOnPage="${numberOfElementsOnPage}"/>
                    </c:if>
                </div>
            </div>
        </div>
        <%@include file="jspf/footer.jspf" %>
    </body>
</html>