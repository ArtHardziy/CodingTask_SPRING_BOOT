<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="row">
    <div class="col-4 offset-9">
        <c:choose>
            <c:when test="${not empty pageContext.request.userPrincipal}">
                Logged in as: <c:out value="${pageContext.request.userPrincipal.name}"/>
                <form method="post" action="${pageContext.request.contextPath}/logout">
                    <button type="submit" class="btn btn-primary">Logout</button>
                </form>
                <sec:authorize access="hasRole('ADMIN')">
                        <a href="<c:url value="/admin"/>">
                            <p>Admin panel</p>
                        </a>
                </sec:authorize>
            </c:when>
            <c:otherwise>
                <a href="<c:url value="/login"/>">
                    <button type="button" class="btn btn-primary">Login</button>
                </a>
            </c:otherwise>
        </c:choose>
    </div>
</div>