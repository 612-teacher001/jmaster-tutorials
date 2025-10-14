<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ include file="/WEB-INF/views/taglib.jsp" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
	<head>	
		<title>アンケート</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
	</head>
	<body>
		<h1>アンケート</h1>
		<p>${requestScope.message}</p>
	</body>
</html>
