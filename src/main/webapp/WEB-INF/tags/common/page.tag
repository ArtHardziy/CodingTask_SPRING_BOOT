<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ attribute name="pageTitle" required="true" type="java.lang.String" %>
<%@ attribute name="showMenu" required="true" type="java.lang.Boolean" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/phoneshop.css"/>"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/js-cookie/js.cookie.js"></script>
    <meta charset="utf-8">
    <title><c:out value="${pageTitle}"/></title>
</head>
<body>
<div class="container-fluid">
    <header>
        <div class="row justify-content-center">
            <h1>Phone Shop Application</h1>
        </div>
    </header>
    <c:if test="${showMenu}">
        <common:menu/>
    </c:if>
    <div class="container authenticated" style="display:none">
        Logged in as: <span id="user"></span>
        <form method="post" action="${pageContext.request.contextPath}/logout">
            <button type="submit" class="btn btn-primary">Logout</button>
        </form>
    </div>
    <div class="container unauthenticated" style="display:none">
        Your are logged out!
    </div>
    <jsp:doBody/>
</div>
</body>
<script type="text/javascript">
    $.ajaxSetup({
        beforeSend : function(xhr, settings) {
            if (settings.type == 'POST' || settings.type == 'PUT'
                || settings.type == 'DELETE') {
                if (!(/^http:.*/.test(settings.url) || /^https:.*/
                    .test(settings.url))) {
                    // Only send the token to relative URLs i.e. locally.
                    xhr.setRequestHeader("X-XSRF-TOKEN",
                        Cookies.get('XSRF-TOKEN'));
                }
            }
        }
    });
    $.get("/user", function (data) {
        if (data !== null && data.name != null) {
            $("#user").html(data.name);
            $(".unauthenticated").hide()
            $(".authenticated").show()
        }
    });

    var logout = function() {
        $.post("/logout", function() {
            $("#user").html('');
            $(".unauthenticated").show();
            $(".authenticated").hide();
        })
        return true;
    }
</script>
</html>