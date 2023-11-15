<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="user" required="true" type="com.expertsoft.phoneshop.persistence.model.PhoneshopUser" %>
<%@ attribute name="dateFormatter" required="true" type="java.text.SimpleDateFormat" %>

<tr>
    <td>
        <c:choose>
            <c:when test="${not empty user.id}">
                <c:out value="${user.id}"/>
            </c:when>
            <c:otherwise>
                Unknown
            </c:otherwise>
        </c:choose>
    </td>
    <td>
        <c:choose>
            <c:when test="${not empty user.name}">
                <c:out value="${user.name}"/>
            </c:when>
            <c:otherwise>
                Unknown
            </c:otherwise>
        </c:choose>
    </td>
    <td>
        <c:choose>
            <c:when test="${not empty user.location}">
                <c:out value="${user.location}"/>
            </c:when>
            <c:otherwise>
                Unknown
            </c:otherwise>
        </c:choose>
    </td>
    <td>
        <c:choose>
            <c:when test="${not empty user.createdAt}">
                <c:out value="${dateFormatter.parse(user.createdAt)}"/>
            </c:when>
            <c:otherwise>
                Unknown
            </c:otherwise>
        </c:choose>
    </td>
</tr>