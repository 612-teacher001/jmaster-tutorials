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
		<table class="enquete" border="1">
			<tr>
				<th>氏名</th>
				<td>${name}</td>
			</tr>
			<tr>
				<th>電子メール</th>
				<td>${email}</td>
			</tr>
			<tr>
				<th>性別</th>
				<td>${gender}</td>
			</tr>
			<tr>
				<th>年代</th>
				<td>${age}</td>
			</tr>
			<tr>
				<th>やってみたい言語</th>
				<td>
					<c:forEach items="${languages}" var="language">
						${language}、
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>ご意見</th>
				<td>${description}</td>
			</tr>
			<tr>
				<td colspan="2">
					<a href="/tutorials-root/EnqueteServlet">入力ページに戻る</a>
				</td>
			</tr>
		</table>

	</body>
</html>
