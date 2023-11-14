<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="phone" tagdir="/WEB-INF/tags/phone" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<common:page pageTitle="Phone list" showMenu="true">
    <c:set var="pageable" value="${phonesPage.pageable()}"/>
    <c:set var="phonePage" value="${phonesPage.page()}"/>
    <c:set var="plpPagingNumbers" value="${phonesPage.plpPagingNumbers()}"/>

    <c:if test="${error != null}">
        <c:out value="${error}"/>
    </c:if>
    <div class="search-container">
        <form:form class="search-form" method="post" modelAttribute="searchFormModel">
            <div class="search-form__label-and-input">
                <form:label path="searchQuery" for="searchQuery">Search Query</form:label>
                <div>
                    <form:input path="searchQuery" class="search-form__input" type="text" id="searchQuery"
                                value="${searchFormModel.searchQuery}"/>
                </div>
            </div>
            <div class="search-form__label-and-input">
                <form:label path="fromPrice" for="fromPrice">From price</form:label>
                <div>
                    <form:input path="fromPrice" class="search-form__input" type="text" id="fromPrice"
                                value="${searchFormModel.fromPrice}"/>
                </div>
            </div>
            <div class="search-form__label-and-input">
                <form:label path="toPrice" for="toPrice">To price</form:label>
                <div>
                    <form:input path="toPrice" class="search-form__input" type="text" id="toPrice"
                                value="${searchFormModel.toPrice}"/>
                </div>
            </div>
            <form:button class="btn btn-primary search-button">Search</form:button>
        </form:form>
    </div>
    <div class="row justify-content-center font-italic mb-3">
        Found <c:out value="${phonePage.totalElements}"/> results!
    </div>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Image</th>
            <th scope="col">Brand <util:sorting sortingField="Brand"/></th>
            <th scope="col">Model <util:sorting sortingField="Model"/></th>
            <th scope="col">Price <util:sorting sortingField="Price"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="phone" items="${phonePage.content}">
            <phone:tile phone="${phone}"/>
        </c:forEach>
        </tbody>
    </table>
    <common:paginationOnPage totalNumber="${phonePage.totalPages}"
                             pageNum="${pageable.pageNumber + 1}"
                             pageSize="${pageable.pageSize}"
                             sortBy="${sortBy}"
                             sortOrder="${sortOrder}"
                             plpPagingNumbers="${plpPagingNumbers}">
    </common:paginationOnPage>

</common:page>