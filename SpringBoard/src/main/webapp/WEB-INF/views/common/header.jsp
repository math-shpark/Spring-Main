<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 긴 변수명일 경우 별칭을 붙여서 사용하는 방법 -->
<c:set var="root" value="${pageContext.request.contextPath }"></c:set>

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">

<!-- Bootstrap JavaScript -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
<div>
	<c:choose>
		<c:when test="${!empty username }">
			${username }님 안녕하세요. <a href="${roo }/user/logout">Log Out</a>
		</c:when>
		<c:otherwise>
			<a href="${root }/user/login">Log In</a>
			<a href="${root }/user/join">Sing Up</a>
		</c:otherwise>
	</c:choose>
</div>