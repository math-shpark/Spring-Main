<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
</head>
<body>
	<div class="container">

		<h2>자유게시판</h2>
		<hr>
		<div>
			<table class="table text-center">
				<thead>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>등록일</th>
					</tr>
				</thead>
				<tbody id="boardTbody"></tbody>
			</table>
			<div>
				<form id="searchForm">
					<select id="mode" name="mode">
						<option value="1">제목
						<option value="2">내용
						<option value="3">제목+내용
					</select> <input type="text" id="keyword" name="keyword"> <input
						type="button" onclick="doSearch()" value="검색">
				</form>
			</div>
			<div class="d-flex justify-content-end">
				<a class="btn btn-outline-danger" href="${root }/board/writeForm">등록</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		const xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4){
				if(xhr.status == 200){
					const list = JSON.parse(xhr.response);
					let html = "";
					
					for(let board of list){
						html += `
							<tr>
							<td>\${board.id}</td>
							<td><a href='${root }/board/detail?id=\${board.id}'>\${board.title}</a></td>
							<td>\${board.writer}</td>
							<td>\${board.viewCnt}</td>
							<td>\${board.regDate}</td>
							</tr>
						`;
					}
					
					document.getElementById('boardTbody').innerHTML = html;
				} else {
					alert("문제 발생 " + xhr.status);
				}
			}
		};
		let mode = new URLSearchParams(location.search).get("mode");
		let keyword = new URLSearchParams(location.search).get("keyword");
		if(mode != null || keyword != null)
			xhr.open("GET", "${root}/api/board?mode=" + mode + "&keyword=" + keyword);
		else
			xhr.open("GET","${root}/api/board");
		xhr.send();
		
		function doSearch(){
			let sMode = document.getElementById('mode').value;
			let word = document.getElementById('keyword').value;
			location.href="${root}/board/list?mode=" + sMode + "&keyword=" + word;
		}
	</script>
</body>
</html>
