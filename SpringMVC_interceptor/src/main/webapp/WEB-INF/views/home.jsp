<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Home</title>
</head>
<body>
<c:choose>
	<c:when test="${empty id }">
		<a href="login">로그인</a>
	</c:when>
	<c:otherwise>
		${id }님 반갑습니다! <a href="logout">로그아웃</a>
	</c:otherwise>
</c:choose>
	<h1>Hello world!</h1>
</body>
</html>
