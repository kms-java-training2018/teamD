<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/chat/login" method="POST">
	<center>
		<p>会員ID
		<input type="text" name="userId" value="${been.userId}"></p>
		<p>パスワード
		<input type="password" name="password" value="${been.password}"></p>

		<br>
		<P>${errorMessage}</P>
		<input type="submit" value="ログイン">
</center>
	</form>
</body>
</html>