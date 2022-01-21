<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
	function checkValidate(f){
		if(f.pass.value==""){
			alert("패스워드를 입력하세요");
			f.pass.focus();
			return false;
		}
	}
</script>
</head>
<body>
<div class="container">
	<h2>회원제 게시판 - 패스워드검증폼</h2>
	
	<!-- 패스워드 검증에 실패했을때 에러메세지 출력용 -->
	<span style="color:red; font-size:1.8em;">
		${isCorrMsg }
	</span>
	
	<form name="writeFrm" method="post" 
		action="./passwordAction.do"
		onsubmit="return checkValidate(this);">
	
	<!-- 일련번호는 컨트롤러에서 model에 저장한 값을 가져온다. (방법1) -->
	<input type="hid den" name="idx" value="${idx }" />
	<!-- 아래 2개의 값은 EL의 param내장 객체를 통해 즉시 가져온다. (방법2) -->
	<input type="hid den" name="mode" value="${param.mode }" />
	<input type="hid den" name="nowPage" value="${param.nowPage }" />
		
	<table border=1 width=800>
	<colgroup>
		<col width="25%"/>
		<col width="*"/>
	</colgroup>
	
	<tr>
		<td>패스워드</td>
		<td>
			<input type="password" name="pass" style="width:30%;" />
		</td>
	</tr>
	 
	<tr>
		<td colspan="2" align="center">
			<button type="submit">작성완료</button>
			<button type="reset">RESET</button>
			<button type="button" onclick="location.href='./list.do?nowPage=${param.nowPage}';">
				리스트바로가기
			</button>
		</td>
	</tr>
	</table>	
	</form>
</div>
</body>
</html>