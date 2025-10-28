<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ include file="/WEB-INF/views/taglib.jsp" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
	<head>
		<meta charset="UTF-8">
		<title>登録確認 - 商品管理機能</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/product.css" />
	</head>
	<body>
		<header>
			<h1>商品管理機能</h1>
		</header>
		<main>
			<article class="insert">
				<h2>登録確認</h2>
				<section class="insert__entry">
					<form class="entry__form" action="/tutorials-root/ProductServlet/insert?action=execute" method="post">
						<p>以下の内容で登録していいですか？</p>
						<table border="1">
							<tr class="ul__${requestScope.display != null ? requestScope.display : ''}">
								<th>商品ID</th>
								<td>${requestScope.id}</td>
							</tr>
							<tr>
								<th>商品カテゴリ</th>
								<td>
									<c:forEach items="${applicationScope.categories}" var="category">
										<c:if test="${requestScope.categoryId == category.id}">
												${category.name}
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th>商品名</th>
								<td>${requestScope.name}</td>
							</tr>
							<tr>
								<th>価格</th>
								<td>${requestScope.price}</td>
							</tr>
							<tr>
								<th>数量</th>
								<td>${requestScope.quantity}</td>
							</tr>
						</table>
						<button>この情報で登録</button>
						<!-- 登録処理のservletに引き継ぐための隠しパラメータ -->
						<input type="hidden" name="name" value="requestScope.name" />
						<input type="hidden" name="categoryId" value="requestScope.categoryId" />
						<input type="hidden" name="price" value="requestScope.price" />
						<input type="hidden" name="quantity" value="requestScope.quantity" />
					</form>
				</section>
			</article>
		</main>
		<footer>
			<div class="copyright">&copy;2025 Java Tutorials.</div>
		</footer>
	</body>
</html>