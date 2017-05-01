package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.QnaDBBean;
import bookshop.bean.QnaDataBean;
import bookshop.process.CommandAction;

public class QnaReplyFormAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response){
		// TODO Auto-generated method stub
		try{

			int qna_id = Integer.parseInt(request.getParameter("qna_id"));
			
			//qna_id에 해당하는 QnA를 가져옴
			QnaDBBean qnaProcess = QnaDBBean.getInstance();
			QnaDataBean qna = qnaProcess.updateGetArticle(qna_id);
			
			//qna 답변에 필요한 정보를 얻어냄
			int book_id = qna.getBook_id();
			String book_title = qna.getBook_title();
			String qna_content = qna.getQna_content();
			int qora = 2; //답변글 우선순위
			
			request.setAttribute("qna_id", new Integer(qna_id));
			request.setAttribute("book_id", new Integer(book_id));
			request.setAttribute("book_title", book_title);
			request.setAttribute("qna_content", qna_content);
			request.setAttribute("qora", new Integer(qora));
			request.setAttribute("type", new Integer(0));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/mngr/qnaProcess/qnaReplyForm.jsp";
	}

}
