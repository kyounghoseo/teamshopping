package bookshop.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.QnaDBBean;
import bookshop.bean.QnaDataBean;
import bookshop.process.CommandAction;

public class QnaListAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response){
		// TODO Auto-generated method stub
		try{
			List<QnaDataBean> qnaLists;
			
			//DB연동 - 상품 QnA 수를 얻어냄
			QnaDBBean qnaProcess = QnaDBBean.getInstance();
			int count = qnaProcess.getArticleCount();
			
			if(count > 0){ //상품 qna가 있으면 수행
				//지정한 수만큼의 상품 qna를 얻어냄
				qnaLists = qnaProcess.getArticles(count);
				request.setAttribute("qnaLists", qnaLists);
			}
			
			request.setAttribute("count", new Integer(count));
			request.setAttribute("type", new Integer(0));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/mngr/qnaProcess/qnaList.jsp";
	}

}
