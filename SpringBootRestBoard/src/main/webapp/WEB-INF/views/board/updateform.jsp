<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/common/header.jsp" %></head>
<body>
	<div class="container">
		<h2>자유게시판</h2>
		<div id="update"></div>
		
	</div>
	
	<script type="text/javascript">
		const xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function(){
			if (xhr.readyState == 4){
				if (xhr.status == 200){
					let board = JSON.parse(xhr.response);
					let html = `
						<form id="updateForm">
						<input type="hidden" name="act" value="update" />
					 	<input type="hidden" name="id" value="\${board.id}" />
						<div class="mb-3">
							<label for="title" class="form-label">제목</label> 
							<input type="text"
								class="form-control" id="title" placeholder="제목을 입력하세요."
								name="title" value="\${board.title}">
						</div>
						<div class="mb-3">
							<label for="writer" class="form-label">글쓴이</label> 
							<input
								type="text" class="form-control" id="writer" name="writer" readonly  value="\${board.writer}">
						</div>
						<div class="mb-3">
							<label for="content" class="form-label">내용</label>
							<textarea class="form-control" id="content" rows="7" name="content">\${board.content}</textarea>
						</div>
						<button class="btn btn-warning" type="button" onclick="doUpdate()">수정</button>
						</form>
					`;
					document.getElementById('update').innerHTML = html;
				} else {
					alert("문제 발생" + xhr.status);
				}
			}
		}
		let id = new URLSearchParams(location.search).get("id");
		xhr.open("GET", "${root}/api/board/" + id + "/2");
		xhr.send();
		
		function doUpdate(){
			xhr.onreadystatechange = function(){
				if(xhr.readyState == 4){
					if(xhr.status == 200){
						location.href="${root}/board/list";
					} else {
						alert("문제 발생" + xhr.status);
					}
				}
			}
			
			let updateForm = document.getElementById('updateForm');
			let data = new FormData(updateForm);
			xhr.open("PUT", "${root}/api/board");
			xhr.send(data);
		}
		
	</script>
</body>
</html>



















