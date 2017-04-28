var status = true;

$(document).ready(function(){
	$("#registProduct").click(function(){//[상품등록] 버튼 클릭
		window.location.href("/shoppingmall/mg/bookRegisterForm.do");
	
	});//[상품등록] 버튼 끝
	
	$("#updateProduct").click(function(){//[상품수정/삭제]버튼 클릭
		window.location.href("/shoppingmall/mg/bookList.do?book_kind=all");
	}); //[상품수정/삭제] 버튼끝
	
	$("#orderedProduct").click(function(){//[전체구매목록 확인] 버튼 클릭
		window.location.href("/shoppingmall/mg/orderList.do");
	}); //[전체구매목록 확인] 버튼 끝
	
	$("#qna").click(function(){//[상품 QnA답변] 버튼 클릭
		window.location.href("/shoppingmall/mg/qnaList.do");
	});//[상품 QnA답변] 버튼 클릭 끝
	
});//document ready끝