package bookshop.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDBBean;
import bookshop.bean.MngrDataBean;
import bookshop.process.CommandAction;

public class BookListAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub

		List<MngrDataBean> bookList = null;
		String book_kind = request.getParameter("book_kind");
		int count = 0;

		// DB 연동 - 전체 상품수
		MngrDBBean bookProcess = MngrDBBean.getInstance();
		count = bookProcess.getBookCount();

		if (count > 0) {
			bookList = bookProcess.getBooks(book_kind);
			request.setAttribute("bookList", bookList);
		}

		request.setAttribute("count", new Integer(count));
		request.setAttribute("book_kind", book_kind);
		request.setAttribute("type", new Integer(0));
		return "/mngr/productProcess/bookList.jsp";
	}

}
