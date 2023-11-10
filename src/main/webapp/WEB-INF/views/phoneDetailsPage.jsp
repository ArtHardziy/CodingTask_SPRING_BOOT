<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<common:page pageTitle="Phone details" showMenu="true">
    <div class="row mb-3">
        <common:back/>
    </div>
    <div class="row justify-content-center">
        <c:choose>
            <c:when test="${phone == null}">
                <p><em>Nothing found</em></p>
            </c:when>
            <c:otherwise>
                <div class="phone-details-container">
                    <div class="phone-details-container__phone-image">
                        <img class="my-image"
                             src="<c:url value="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.image()}"/>"
                             alt="Phone image">
                    </div>
                    <div class="phone-details-container__description-row">
                        <div class="phone-description__phone-brand">
                            <span class="fw-bold">Brand:</span> <c:out value="${phone.brand()}"/>
                        </div>
                        <div class="phone-description__description-row">
                            <span class="fw-bold">Model:</span> <c:out value="${phone.model()}"/>
                        </div>
                        <div class="phone-description__description-row">
                            <span class="fw-bold">Price:</span> <c:out value="${phone.price()}"/>$
                        </div>
                        <div class="phone-description__description-row">
                            <span class="fw-bold">Description:</span> <c:out value="${phone.description()}"/>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</common:page>
