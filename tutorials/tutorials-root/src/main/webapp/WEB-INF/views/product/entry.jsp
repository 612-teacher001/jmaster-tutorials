<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ include file="/WEB-INF/views/taglib.jsp" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
	<!-- ヘッド要素領域 -->
	<jsp:include page="../common/head.jsp" />

	<body>
		<!-- ページヘッダ要素領域 -->
		<jsp:include page="../common/header.jsp" />
		<main>
			<article class="insert">
				<h2>商品登録</h2>
				<section class="insert__entry">
					<c:if test="${not empty requestScope.errors}">
						<ul class="error">
						<c:forEach items="${requestScope.errors}" var="error">
							<li>${error}</li>
						</c:forEach>
						</ul>
					</c:if>
					<form class="entry__form" action="/tutorials-root/ProductServlet/insert?action=confirm" method="post">
						<table border="1">
							<caption class="ul__small ul__aright">すべての項目は<em class="ul__bold">必須</em>です。</caption>
							<tr class="ul__${requestScope.display != null ? requestScope.display : ''}">
								<th>商品ID</th>
								<td>${requestScope.id}</td>
							</tr>
							<tr>
								<th>商品カテゴリ</th>
								<td>
									<select name="categoryId">
										<c:forEach items="${applicationScope.categories}" var="category">
											<c:choose>
												<c:when test="${category.id == requestScope.categoryId}">
													<option value="${category.id}" selected>${category.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${category.id}">${category.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th>商品名</th>
								<td>
									<input type="text" name="name" value="${requestScope.name}" />
								</td>
							</tr>
							<tr>
								<th>価格</th>
								<td>
									<input type="number" name="price" value="${requestScope.price}" />
								</td>
							</tr>
							<tr>
								<th>数量</th>
								<td>
									<input type="number" name="quantity" value="${requestScope.quantity}" />
								</td>
							</tr>
						</table>
						<button>確認</button>
					</form>
				</section>
			</article>
		</main>
		
		<!-- ページフッタ要素領域 -->
		<jsp:include page="../common/footer.jsp" />
	</body>
</html>