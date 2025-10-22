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
					<form class="criteria__entry" action="${pageContext.request.contextPath}/ProductServlet/list" method="get">
						<dl>
							<dt>並べ替え</dt>
							<dd>
								<label for="asc"><input type="radio" name="sortOrder" value="asc" id="asc" />価格の安い順</label>
								<label for="desc"><input type="radio" name="sortOrder" value="desc" id="desc" />価格の高い順</label>
								<button>検索</button>
							</dd>
						</dl>
						<dl>
							<dt>キーワード</dt>
							<dd>
								<input type="text" name="keyword" />
								<button>検索</button>
							</dd>
						</dl>
					</form>
					<ul>
						<li><a href="${pageContext.request.contextPath}/ProductServlet/list">全商品</a></li>
						<c:forEach items="${applicationScope.categories}" var="category">
						<li><a href="${pageContext.request.contextPath}/ProductServlet/list?categoryId=${category.id}">${category.name}</a></li>
						</c:forEach>
					</ul>
				</section>
				<section class="result">
					<c:choose>
						<c:when test="${requestScope.count == 0}">
							<p>商品は見つかりませんでした。</p>
						</c:when>
						<c:otherwise>
							<p>${requestScope.count}件の商品が見つかりました。</p>
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
						</c:otherwise>
					</c:choose>
				</section>
			</article>
		</main>
		<footer>
			<div class="copyright">&copy;2025 Java Tutorials.</div>
		</footer>
	</body>
</html>