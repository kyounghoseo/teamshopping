$(document).ready(function(){
	
		$("#modifyProcess").click(function(){
			var query= {id:$("#id").val(),
					passwd:$("#passwd").val(),
					name:$("#name").val(),
					address:$("#address").val(),
					tel:$("#tel").val()
			};
			
			$.ajax({
				type:"post",
				data: query,
				success: function(data){
					var str1='<p id="ck">';
					var loc = data.indexOf(str1);
					var len = str1.length;
					var check = data.substr(loc+len,1);
					if(check="1"){
						alert("회원정보가 수정되었어요");
						window.location.href = "/shoppingmall/modify.do";
					}else{
						alert("비밀번호가 틀려");
						$("#passwd").val("");
						$("#passwd").focus();
					}
				}
			});
		});
		
		//취소 버튼을 눌렀을 때
		$("#cancle").click(function(){
			window.location.href = "/shoppingmall/modify.do";
		});
		
});