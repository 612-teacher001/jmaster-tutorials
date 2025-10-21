<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ include file="/WEB-INF/views/taglib.jsp" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
	<head>
		<meta charset="UTF-8">
		<title>商品一覧</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/product.css">
	</head>
	<body id="product">
		<header>
			<h1>商品一覧</h1>
		</header>
		<main>
			<article class="search">
				<section class="criteria">
					<ul>
						<li><a href="${pageContext.request.contextPath}/ProductServlet/list">全商品</a></li>
						<li><a href="${pageContext.request.contextPath}/ProductServlet/list?categoryId=1">本</a></li>
						<li><a href="${pageContext.request.contextPath}/ProductServlet/list?categoryId=2">DVD</a></li>
						<li><a href="${pageContext.request.contextPath}/ProductServlet/list?categoryId=3">ゲーム</a></li>
					</ul>
				</section>
				<section class="result">
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
				</section>
			</article>
		</main>
		<footer>
			<div class="copyright">&copy;2025 Java Tutorials.</div>
		</footer>
	</body>
</html>