<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="phone" tagdir="/WEB-INF/tags/phone" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<common:page pageTitle="Phone list" showMenu="true">
    <div class="row justify-content-center font-italic mb-3">
        Found <c:out value="${phones.page().totalElements}"/> results!
    </div>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Image</th>
            <th scope="col">Brand <util:sorting/></th>
            <th scope="col">Model <util:sorting/></th>
            <th scope="col">Price <util:sorting/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="phone" items="${phones.page().content}">
            <phone:tile phone="${phone}"/>
        </c:forEach>
        </tbody>
    </table>
    <common:paginationOnPage totalNumber="${phones.page().totalPages}" pageNum="${phones.pageNum()}" pageSize="${phones.pageSize()}" sortBy="${phones.sortBy()}" sortOrder="${phones.sortOrder()}">
    </common:paginationOnPage>

</common:page>