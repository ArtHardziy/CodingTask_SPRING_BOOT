<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ attribute name="totalNumber" type="java.lang.Integer" required="true" %>

<%@ attribute name="pageNum" type="java.lang.Integer" required="true" %>
<%@ attribute name="pageSize" type="java.lang.Integer" required="true" %>
<%@ attribute name="sortBy" type="java.lang.String" required="true" %>
<%@ attribute name="sortOrder" type="java.lang.String" required="true" %>

<div class="float-right">
<uL class="pagination justify-content-end">

    <li class="page-item ${pageNum eq 1 ? "hidden" : ""}">
        <a class="page-link"
           href="${pageContext.request.contextPath}/phones?<c:if test="${not empty pageNum}">pageNum=${pageNum - 1}&</c:if><c:if test="${not empty pageSize}">pageSize=${pageSize}&</c:if><c:if test="${not empty sortBy}">sortBy=${sortBy}&</c:if><c:if test="${not empty sortOrder}">sortOrder=${sortOrder}</c:if>"
           aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
            <span class="sr-only">Previous</span>
        </a>
    </li>

    <c:set var="startPage" value="${pageNum <= 5 ? 1 : pageNum - 5}"/>
    <c:set var="endPage" value="${startPage + 9 > totalNumber ? totalNumber : startPage + 9}"/>
    <c:set var="startPage" value="${endPage - 9 <= 0 ? 1 : endPage - 9}"/>
    <c:forEach var="i" begin="${startPage}" end="${endPage}">
        <li class="page-item ${i eq pageNum ? "active" : ""}">
            <a class="page-link"
               href="${pageContext.request.contextPath}/phones?pageNum=${i}<c:if test="${not empty pageSize}">&pageSize=${pageSize}&</c:if><c:if test="${not empty sortBy}">sortBy=${sortBy}&</c:if><c:if test="${not empty sortOrder}">sortOrder=${sortOrder}</c:if>">${i}</a>
        </li>
    </c:forEach>

    <li class="page-item ${pageNum eq totalNumber ? "hidden" : ""}">
        <a class="page-link"
           href="${pageContext.request.contextPath}/phones?<c:if test="${not empty pageNum}">pageNum=${pageNum + 1}&</c:if><c:if test="${not empty pageSize}">pageSize=${pageSize}&</c:if><c:if test="${not empty sortBy}">sortBy=${sortBy}&</c:if><c:if test="${not empty sortOrder}">sortOrder=${sortOrder}</c:if>"
           aria-label="Next">
            <span class="sr-only">Next</span>
            <span aria-hidden="true">&raquo;</span>
        </a>
    </li>

</uL>
</div>