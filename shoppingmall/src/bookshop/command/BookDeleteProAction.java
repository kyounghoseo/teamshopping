package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDBBean;
import bookshop.process.CommandAction;

public class BookDeleteProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response){
		// TODO Auto-generated method stub

		try{
			int book_id = Integer.parseInt(request.getParameter("book_id"));
			String book_kind = request.getParameter("book_kind");

			// DB연동 - book_id에 해당하는 상품을 삭제
			MngrDBBean bookProcess = MngrDBBean.getInstance();
			bookProcess.deleteBook(book_id);

			request.setAttribute("book_kind", book_kind);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/mngr/productProcess/bookDeletePro.jsp";
	}

}
