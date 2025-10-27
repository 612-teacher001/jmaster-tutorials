<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ include file="/WEB-INF/views/taglib.jsp" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ja">
	<!-- ヘッド要素領域 -->
	<jsp:include page="../common/head.jsp" />

	<body id="product">
		<!-- ページヘッダ要素領域 -->
		<jsp:include page="../common/header.jsp" />

		<main>
			<article class="search">
				<section class="criteria">
					<form class="criteria__entry" action="${pageContext.request.contextPath}/ProductServlet/list" method="get">
						<dl>
							<dt>並べ替え</dt>
							<dd>
								<c:choose>
									<c:when test="${requestScope.sort eq 'asc'}">
											<label for="asc"><input type="radio" name="sortOrder" value="asc" id="asc" checked />価格の安い順</label>
											<label for="desc"><input type="radio" name="sortOrder" value="desc" id="desc" />価格の高い順</label>
									</c:when>
									<c:when test="${requestScope.sort eq 'desc'}">
											<label for="asc"><input type="radio" name="sortOrder" value="asc" id="asc" />価格の安い順</label>
											<label for="desc"><input type="radio" name="sortOrder" value="desc" id="desc" checked />価格の高い順</label>
									</c:when>
									<c:otherwise>
											<label for="asc"><input type="radio" name="sortOrder" value="asc" id="asc" />価格の安い順</label>
											<label for="desc"><input type="radio" name="sortOrder" value="desc" id="desc" />価格の高い順</label>
									</c:otherwise>
								</c:choose>
							</dd>
						</dl>
						<dl>
							<dt>キーワード</dt>
							<dd>
								<input type="text" name="keyword" value="${requestScope.keyword}" />
							</dd>
						</dl>
						<dl>
							<dt>範囲検索</dt>
							<input type="number" name="maxPrice" value="${requestScope.maxPrice}" /> 円以下
						</dl>
						<dl>
							<dt></dt>
							<dd><button type="submit">検索</button></dd>
						</dl>
					</form>
					<ul>
						<li><a href="${pageContext.request.contextPath}/ProductServlet/list">全商品</a></li>
						<c:forEach items="${applicationScope.categories}" var="category">
							<c:choose>
								<c:when test="${requestScope.categoryId eq category.id}">
									<li>
										<a class="ul__bold" href="${pageContext.request.contextPath}/ProductServlet/list?categoryId=${category.id}">${category.name}</a>
									</li>
								</c:when>
								<c:otherwise>
									<li>
										<a href="${pageContext.request.contextPath}/ProductServlet/list?categoryId=${category.id}">${category.name}</a>
									</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</ul>
				</section>
				<section class="result">
					<c:choose>
						<c:when test="${requestScope.products.size() == 0}">
							<p>商品は見つかりませんでした。</p>
						</c:when>
						<c:otherwise>
							<p>${requestScope.products.size()}件の商品が見つかりました。</p>
							<table border="1">
								<tr>
									<th>商品ID</th>
									<th>商品名</th>
									<th>価格</th>
									<th>数量</th>
								</tr>
								<c:forEach items="${requestScope.productList}" var="product">
								<tr>
									<td>${product.id}</td>
									<td>${product.name}</td>
									<td><fmt:formatNumber value="${product.price}" pattern="###,###" /> <span class="ul__small">円</span></td>
									<td>${product.quantity}</td>
								</tr>
								</c:forEach>
							</table>
						</c:otherwise>
					</c:choose>
					<p><a href="${pageContext.request.contextPath}/ProductServlet/insert">新規登録ページへ</a></p>
				</section>
			</article>
		</main>
		
		<!-- ページフッタ要素領域 -->
		<jsp:include page="../common/footer.jsp" />
	</body>
</html>