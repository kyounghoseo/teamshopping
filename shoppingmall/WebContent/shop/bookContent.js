$(document).ready(function(){
	$("#insertCart").click(function(){// 장바구니에 담기 버튼 눌렀을때
		var buyer = $("#buyer").val();
		var book_kind = $("#book_kind").val();
		var query = {book_id:$("book_id").val(),
				buy_count:$("#buy_count").val(),
				book_image:$("#book_image").val(),
				book_title:$("#book_title").val(),
				buy_price:$("#buy_price").val(),
				buyer:buyer};
		$.ajax({
			type: "POST",
			url: "/shoppingmall/insertCart.do",
			data: query,
			success: function(data){
				alert("장바구니에 담겼습니다.");
			}
		});
});
	
	//목록버튼 눌렀을때 -> list.jsp or .do
		$("list").click(function(){
			window.location.href("/shoppingmall/list.do?book_kind=all");
		});
		
		//메인버튼 눌렀을대 -> index.jsp or .do
		$("shopMain").click(function(){
			window.location.href("/shoppingmall/index.do");
		});
		
		//상품 Q&A 쓰기 눌렀을때
		$("#writeQna").click(function(){
			var book_id = $("#book_id").val();
			var book_kind = $("#book_kind").val();
			
			var query="/shoppingmall/qnaForm.do?book_id="+book_id;
			query += "&book_kind="+book_kind; 
			window.location.href(query);
		});	
});
		
		//수정 버튼 눌렀을때
		function edit(editBtn){
			var rStr = editBtn.name;
			var arr = rStr.split(",");
			var query = "/shoppingmall/qnaUpdateForm.do?qna_id="+arr[0];
			query += "&book_kind="+arr[1];
			window.location.href(query);
		}
		
		//삭제 버튼 눌렀을때
		function del(delBtn){
			var rStr = delBtn.name;
			var arr = rStr.split(",");
			
			var query = {qna_id: arr[0]};
			$.ajax({
				type: "POST",
				url: "/shoppingmall/qnaDeletePro.do",
				data: query,
				success: function(data){
					var str1 = '<p id = "ck">';
					var loc = data.index0f(str1);
					var len = str1.length;
					var check = data.substr(loc+len,1);
					if(check == "1"){ // 아이디가 인증된 상태일때
						alert("QnA가 삭제되었습니다.");
						var query = "/shoppingmall//bookContent.do?book_id="+arr[1];
						query += "&book_kind=" + arr[2];
						window.location.href(query);
					}else //사용할 수 있는 아이디
						alert("QnA가 삭제 실패");
				}
			});
		}