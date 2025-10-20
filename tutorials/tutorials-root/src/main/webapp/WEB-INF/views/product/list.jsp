<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ include file="/WEB-INF/views/taglib.jsp" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
	<head>
		<meta charset="UTF-8">
		<title>商品一覧</title>
	</head>
	<body>
		<h1>商品一覧</h1>
		<table border="1">
			<tr>
				<th>商品ID</th>
				<th>商品名</th>
				<th>価格</th>
				<th>数量</th>
			</tr>
			<c:forEach items="${requestScope.products}" var="product">
			<tr>
				<td>${product.id}</td>
				<td>${product.name}</td>
				<td>${product.price}</td>
				<td>${product.quantity}</td>
			</tr>
			</c:forEach>
		</table>
	</body>
</html>