<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/taglib.jsp" %>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>AOPのサンプルページ</title>
</head>
<body>
    <h1>JSPサンプルページ</h1>
    <p>送信されたデータは以下の通り：</p>
    <ul>
    <c:forEach items="${seasons}" var="season">
        <li>${season}</li>
    </c:forEach>
    </ul>
</body>
</html>