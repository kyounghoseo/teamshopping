package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDBBean;
import bookshop.bean.MngrDataBean;
import bookshop.process.CommandAction;

public class BookUpdateFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try{

			int book_id = Integer.parseInt(request.getParameter("book_id"));
			String book_kind = request.getParameter("book_kind");

			// DB 연동 book_id에 해당하는 상품을 얻어내서 book에 저장
			MngrDBBean bookProcess = MngrDBBean.getInstance();
			MngrDataBean book = bookProcess.getBook(book_id);

			request.setAttribute("book_id", book_id);
			request.setAttribute("book_kind", book_kind);
			request.setAttribute("book", book);
			request.setAttribute("type", new Integer(0));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/mngr/productProcess/bookUpdateForm.jsp";
	}

}
