package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.QnaDBBean;
import bookshop.process.CommandAction;

public class QnaDeleteProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try{

			int qna_id = Integer.parseInt(request.getParameter("qna_id"));
			
			//qna_id 에 해당하는 qna 삭제
			
			QnaDBBean qnaProcess = QnaDBBean.getInstance();
			int check = qnaProcess.deleteArticle(qna_id);
			
			request.setAttribute("check", new Integer(check));
					
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "qna/qnaDeletePro.jsp";
	}

}
