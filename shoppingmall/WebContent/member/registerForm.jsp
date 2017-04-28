<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/shoppingmall/css/style.css"/>
<script src="/shoppingmall/js/jquery-1.11.0.min.js"></script>
<script src="/shoppingmall/member/register.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="regForm" class="box">
<ul>
<!-- 아이디 입력란 -->
<li><label for="id">아이디</label>
<input id="id" name="id" type="email" size="20" maxlength="50" palceholder="example@kings" autofocus>
<button id="checkId">ID중복확인</button>

<!-- 비밀번호 입력란 -->
<li><label for="passwd">비밀번호</label>
<input id="passwd" name="passwd" type="password" size="20" placeholder="6자~16자 숫자/문자" maxlength="16">

<!-- 비밀번호 재입력란 -->
<li><label for="repass">비밀번호 재입력</label>
<input id="repass" name="repass" type="password" size="20" placeholder="비밀번호 재입력" maxlength="16">

<!-- 이름 입력란 -->
<li><label for="name">이름</label>
<input type="text" id="name" name="name" size="20" placeholder="홍길동" maxlength="10">

<!-- 주소 입력란 -->
<li><label for="address">주소</label>
<input id="address" name="address" type="text" size="30" placeholder="주소 입력" maxlength="50">

<!-- 전화번호 입력란 -->
<li><label for="tel">전화번호</label>
<input id="tel" name="tel" type="tel" size="20" placeholder="전화번호 입력" maxlaength="20">

<!-- 가입하기 버튼 -->
<li class="label2"><button id="process">가입하기</button>
<button id="cancle">취소</button>
</ul>
</div>
</body>
</html>