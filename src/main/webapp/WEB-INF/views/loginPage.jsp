<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<common:page pageTitle="Login" showMenu="false">
    <div class="row mb-3">
        <common:back/>
    </div>
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <form:form action="/signin" method="post" modelAttribute="signInRequest">
                        <div class="form-group row">
                            <form:label path="username" for="username" class="col-sm-3 col-form-label">
                                Username:
                            </form:label>
                            <div class="col-sm-8">
                                <form:input path="username" type="text" id="username" name="username" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <form:label path="password" for="password" class="col-sm-3 col-form-label">
                                Password:
                            </form:label>
                            <div class="col-sm-8">
                                <form:input path="password" type="password" id="password" name="password" class="form-control"/>
                            </div>
                        </div>
                        <div class="row justify-content-center">
                            <button type="submit" class="btn btn-primary">Login</button>
                        </div>
                        <div class="row justify-content-center">
                            <a href="${pageContext.request.contextPath}/oauth2/authorization/github">Login via GitHub >></a>
                        </div>
                        <div class="row justify-content-center">
                            <a href="${pageContext.request.contextPath}/oauth2/authorization/google">Login via Google >></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</common:page>