<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン</title>


<script src="./js/login.js"></script>

</head>
<body>
	<form action="/chat/login" method="POST">
		<center>
			<table>
				<tr>
					<td>会員ID</td>
					<td><input type="text" name="userId" value="${been.userId}"
						title="会員ID" class="placeholder"></td>
				</tr>
				<tr>
					<td>パスワード</td>
					<td><input type="password" name="password"
						value="${been.password}" title="パスワード" class="placeholder"></td>
			</table>
			<br> <font color="red"><strong>${ errorMessage }</strong></font>
			<br>
			<br>



			<input type="submit" value="ログイン">
		</center>
	</form>
</body>
</html>