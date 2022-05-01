<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
</head>
<body>
	<div class="container">
	<h2>자유게시판</h2>
	<form enctype="multipart/form-data" id="createForm">
		<input type="hidden" name="act" value="write" />
		<div class="mb-3">
			<label for="title" class="form-label">제목</label> <input type="text"
				class="form-control" id="title" placeholder="제목을 입력하세요."
				name="title">
		</div>
		<div class="mb-3">
			<label for="writer" class="form-label">글쓴이</label> <input
				type="text" class="form-control" id="writer" name="writer" value="${username }">
		</div>
		<div class="mb-3">
			<label for="content" class="form-label">내용</label>
			<textarea class="form-control" id="content" rows="7" name="content"></textarea>
		</div>
		<div class="mb-3">
			<input class="form-control" type="file" name="upload_file">		
		</div>
		<button class="btn btn-primary" type="button" onclick="doCreate()">등록</button>
	</form>
	</div>
	
	<script type="text/javascript">
		function doCreate(){
			const xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function(){
				if (xhr.readyState == 4){
					if (xhr.status == 201){
						location.href='${root}/board/list';
					} else {
						alert("문제 발생" + xhr.status);
					}
				}
			}
			let createForm = document.getElementById('createForm');
			let data = new FormData(createForm);
			xhr.open("POST", "${root}/api/board");
			xhr.send(data);
		}
	</script>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>