<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>>エラー</h1>
<noscript>
<font color="#ff0000">jsが無効です。有効にしてください。</font>
</noscript>
<font color="#ff0000"><c:out value="${errorMsg}"></c:out></font>
<br>
お手数ですがログインページからやり直してください
<form action="/chat/logout" method="POST">
<input type="submit" value="ログインページへ">
</form>

</body>
</html>