<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<common:page pageTitle="Admin panel" showMenu="false">
    <div class="row mb-3">
        <common:back/>
    </div>
    <div class="row justify-content-center">
        <h2>Admin panel page stub</h2>
    </div>
<c:choose>
    <c:when test="${not empty registeredUsers}">
        <div class="row justify-content-center font-italic mb-3">
            Found <c:out value="${registeredUsers.numberRegisteredUsers}"/> results!
        </div>

        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Username</th>
                <th scope="col">Location</th>
                <th scope="col">Date registered</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="registeredUser" items="${registeredUsers.registeredUsersList}">
                <user:registeredUserTile
                 user="${registeredUser}"
                 dateFormatter="${registeredUsers.simpleDateFormat}"
                />
            </c:forEach>
            </tbody>
        </table>

        <common:paginationOnPage totalNumber="${registeredUsers.totalPages}"
                                 pageNum="${registeredUsers.currentPageNumber + 1}"
                                 pageSize="${registeredUsers.pageSize}"
                                 sortBy="${null}"
                                 sortOrder="${null}"
                                 plpPagingNumbers="${plpPagingNumbers}"
                            url="/admin">
        </common:paginationOnPage>


    </c:when>
    <c:otherwise>
        No users registered yet :(
    </c:otherwise>

</c:choose>

</common:page>
