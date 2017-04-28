package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.QnaDBBean;
import bookshop.process.CommandAction;

public class QnaDeleteProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		
		int qna_id = Integer.parseInt(request.getParameter("qna_id"));
		
		//qna_id 에 해당하는 qna 삭제
		
		QnaDBBean qnaProcess = QnaDBBean.getInstance();
		int check = qnaProcess.deleteArticle(qna_id);
		
		request.setAttribute("check", new Integer(check));
				
		return "qna/qnaDeletePro.jsp";
	}

}
