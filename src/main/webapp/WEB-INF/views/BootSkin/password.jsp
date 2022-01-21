<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../../resources/commons/header.jsp" %>
<script type="text/javascript">
	function checkValidate(f){
		if(f.pass.value==""){
			alert("패스워드를 입력하세요");
			f.pass.focus();
			return false;
		}
	}
</script>
<body>
<div class="container">
    <!-- Top영역 -->
    <%@ include file="../../../resources/commons/top.jsp" %>
    <!-- Body영역 -->
    <div class="row">
        <!-- Left메뉴영역 -->
        <%@ include file="../../../resources/commons/left.jsp" %>
        <!-- Contents영역 -->
        <div class="col-9 pt-3">
            <h3>게시판 - <small>패스워드 검증</small></h3>
            
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
			
		<table border=1 width=800 class="table table-bordered">
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
		</table>

                <!-- 각종버튼 -->
                <div class="row mb-3">
                    <div class="col d-flex justify-content-end">
                        <!-- <button type="button" class="btn btn-primary">글쓰기</button>
                        <button type="button" class="btn btn-secondary">수정하기</button>
                        <button type="button" class="btn btn-success">삭제하기</button>
                        <button type="button" class="btn btn-info">답글쓰기</button> -->
                        <button type="button" class="btn btn-warning"
                        	onclick="location.href='./list.do?nowPage=${param.nowPage}';">목록보기</button>
                        <button type="submit" class="btn btn-danger">전송하기</button>
                        <button type="reset" class="btn btn-dark">다시쓰기</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <!-- Copyright영역 -->
    <%@ include file="../../../resources/commons/copyright.jsp" %>
</div>
</body>
</html>