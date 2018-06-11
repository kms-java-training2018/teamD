<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>メインページ</title>
<!-- css読み込み -->
<link rel="stylesheet" type="text/css" href="./css/mainPage.css"
	media="all">
<!-- JS読み込み	-->
<script src="./js/mainPage.js"></script>
</head>
<body id="page">
	<!-- 以下、ヘッダー部分になります。各自実装お願いします -->
	${ session.getUserName() }さん
	<br>
	<form name="log_out" action="/chat/logout" method="POST">
		<input type="button" value="logout"
			onClick="if(confirm ('本当にログアウトしますか？')){submit();}">
	</form>
	<hr>
	<!-- ここまでです -->
	<table class="titleTable">
		<tr>
			<td rowspan="2" colspan="2"><p class="title">Ch@</p></td>
			<td></td>
		</tr>
		<tr>
			<td>kms2018 chat tool</td>
		</tr>
		<tr>
			<td><h3>Main Menu</h3></td>
			<td id="directMessageBtn" onclick="DMBtnclick()">DirectMessage</td>
			<td id="groupMessageBtn" onclick="GMBtnclick()">GroupMessage"</td>
		</tr>
	</table>
	<div class="page" id="directMessage">
		<form name="DM">
			<!-- jstlで作成したform"DM"がひとつだけの場合、インデックスが機能しないのを避ける為に作成 -->
			<h4>～User List～</h4>
		</form>
		<!-- 綺麗じゃないから余裕があれば直す -->
		<table class="table">
			<c:forEach var="obj" items="${userbean}" varStatus="status">
				<tr>
					<td>
						<form name="DM" method="get" action="/chat/directMessage">
							<input type=hidden name="userNo"
								value="${userbean[status.index].userNo}"> ○<a
								href="javascript:DM[${status.index + 1}].submit()">${userbean[status.index].userName}</a>
						</form>
					</td>
					<td>: ${userbean[status.index].directMessage}</td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<div class="page" id="groupMessage">
		<form name="GM">
			<!-- jstlで作成したform"GM"がひとつだけの場合、インデックスが機能しないのを避ける為に作成 -->
			<h4>～Group List～</h4>
		</form>
		<!-- 綺麗じゃないから余裕があれば直す -->
		${groupbean[0].getGroupNullMes()}
		<table class="table">
			<c:forEach var="obj" items="${groupbean}" varStatus="status">
				<tr>
					<td>
						<form name="GM" method="get" action="/chat/groupMessage">
							<input type=hidden name="groupNo"
								value="${groupbean[status.index].groupNo}">○ <a
								href="javascript:GM[${status.index + 1}].submit()">${groupbean[status.index].groupName}</a>
						</form>
					</td>
					<td>: ${groupbean[status.index].groupMessage}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<br>
	<br>
	<form action="/chat/makeGroup" method="GET">
		<input type="submit" value="グループの作成">
	</form>
	<form action="/chat/myPage" method="GET">
		<input type="submit" value="プロフィール画面へ">
	</form>


</body>
</html>