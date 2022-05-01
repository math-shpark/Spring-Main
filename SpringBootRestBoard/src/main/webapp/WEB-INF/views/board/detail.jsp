<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8'>
<title>자유게시판</title>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
</head>
<body>
	<div class="container">
		<h2>자유게시판</h2>
		<hr />
		<div class="card" style="width: 30rem;">
			<div class="card-body">
			<div id="detail"></div>
				
			</div>
		</div>
	</div>
	<script type="text/javascript">
		const xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4){
				if (xhr.status == 200){
					let board = JSON.parse(xhr.response);
					let html = `
						<h5 class="card-title">\${board.title}<span class="badge bg-danger">\${board.viewCnt}	</span></h5>
						<div class="d-flex justify-content-between">
							<div class="card-subtitle mb-2 text-muted">\${board.writer}</div>
							<div class="card-subtitle mb-2 text-muted">\${board.regDate}</div>
						</div>
						<hr>
						<p class="card-text">\${board.content}</p>
						<hr>
						<a href="${root}/api/download/\${board.id}">\${board.fileName }</a>
						<img alt="\${board.fileName }" src="${root}/api/download/\${board.id}">
						<div>
							<a class= "btn btn-secondary" href='${root }/board/updateForm?id=\${board.id}'>수정</a>
							<button class="btn btn-success" type="button" onclick="doDelete()">삭제</button>
							<a class="btn btn-info" href='${root }/board/list'>목록</a>
						</div>
					`;
					document.getElementById("detail").innerHTML = html;
				} else {
					alert("문제 발생" + xhr.status);
				}
			}
		};
		let id = new URLSearchParams(location.search).get("id");
		xhr.open("GET", "${root}/api/board/" + id + "/1");
		xhr.send();
		
		function doDelete(){
			xhr.onreadystatechange = function(){
				if(xhr.readyState == 4){
					if (xhr.status == 200){
						location.href='${root}/board/list';
					} else {
						alert("문제 발생" + xhr.status);
					}
				}
			}
			xhr.open("DELETE", "${root}/api/board/" + id);
			xhr.send();
		}
	</script>
</body>
</html>












