<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="sortingField" type="java.lang.String" required="true" %>
<c:set var="upperArrow" value="&#x25B2;"/>
<c:set var="downArrow" value="&#x25BC;"/>
<c:url var="sortUrl" value="/phones"/>
<a href="${sortUrl}?<c:if test="${not empty pageNum}">pageNum=${pageContext.getAttribute("pageNum")}</c:if>&<c:if test="${not empty pageSize}">pageSize=${pageSize}&</c:if>sortBy=${sortingField}&sortOrder=asc"><c:out value="${upperArrow}" escapeXml="false"/></a>
<a href="${sortUrl}?<c:if test="${not empty pageNum}">pageNum=${pageContext.getAttribute("pageNum")}</c:if>&<c:if test="${not empty pageSize}">pageSize=${pageSize}&</c:if>sortBy=${sortingField}&sortOrder=desc"><c:out value="${downArrow}" escapeXml="false"/></a>