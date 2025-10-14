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
		<!-- action属性：遷移先URL、method属性：送信モード（GET送信|POST送信） -->
		<form action="/tutorials-root/EnqueteServlet" method="post">
			<table class="enquete" border="1">
				<tr>
					<th>氏名</th>
					<td>
						<!-- テキストボックス：文字列入力用（改行を含む複数行は入力不可） -->
						<input type="text" name="name" />
					</td>
				</tr>
				<tr>
					<th>電子メール</th>
					<td>
						<!-- メールアドレス用入力ボックス -->
						<input type="email" name="email" />
					</td>
				</tr>
				<tr>
					<th>性別</th>
					<td>
						<!-- ラジオボタン：排他的選択 -->
						<label for="male"><input id="male" type="radio" name="gender" value="1" />男性</label>
						<label for="female"><input id="female" type="radio" name="gender" value="2" />女性</label>
						<label for="other"><input id="other" type="radio" name="gender" value="9" />その他</label>
					</td>
				</tr>
				<tr>
					<th>年代</th>
					<td>
						<!-- セレクトボックス：排他的選択 -->
						<select name="ages">
							<option value="00">10代未満</option>
							<option value="10">10代</option>
							<option value="20">20代</option>
							<option value="30">30代</option>
							<option value="40">40代</option>
							<option value="50">50代</option>
							<option value="60">60代以上</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>やってみたい言語</th>
					<td>
						<!-- チェックボックス：複数選択 -->
						<label for="language0"><input id="language0" type="checkbox" name="languages" value="C/C++" />C/C++</label>
						<label for="language1"><input id="language1" type="checkbox" name="languages" value="Java" />Java</label>
						<label for="language2"><input id="language2" type="checkbox" name="languages" value="PHP" />PHP</label>
						<label for="language3"><input id="language3" type="checkbox" name="languages" value="Ruby" />Ruby</label>
						<label for="language4"><input id="language4" type="checkbox" name="languages" value="other" />その他</label>
					</td>
				</tr>
				<tr>
					<th>ご意見</th>
					<td>
						<!-- テキストエリア：文字列入力（改行を含む複数行の入力用） -->
						<textarea name="description" cols="40" rows="4"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<!-- ボタン：formのaction属性のURLを呼び出す -->
						<button>送信</button>
					</td>
				</tr>
			</table>
		</form>

	</body>
</html>
