<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>プロフィール</title>
</head>
<body>
<br><br><br><br>
	<form action="/chat/showProfile" method="POST">
		<center>
			<table>
				<tr>
					<td>名前</td>
					<td>${ showName }</td>
				</tr>
				<tr>
					<td>自己紹介</td>
					<td>${ showMyPageText }</td>
				</tr>
			</table>
			<a href="#" onClick="window.close();"><input type="button" value="戻る"></a>
		</center>
	</form>
</body>
</html>