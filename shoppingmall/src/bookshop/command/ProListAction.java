package bookshop.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.MngrDBBean;
import bookshop.bean.MngrDataBean;
import bookshop.process.CommandAction;

public class ProListAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response){
		// TODO Auto-generated method stub
		try{
			List<MngrDataBean> bookList = null;
			int count = 0;
			String book_kind = request.getParameter("book_kind");
			MngrDBBean bookProcess = MngrDBBean.getInstance();
			
			//kind 값이 all이면 전체 상품의 수를 얻어냄
			if(book_kind.equals("all"))
				count = bookProcess.getBookCount();
			else//all이 아니면 해당 카테고리의 상품 수를 얻어냄
				count = bookProcess.getBookCount(book_kind);
			
			if(count > 0){//상품이 있으면 수행
				//상품목록을 얻어냄
				bookList = bookProcess.getBooks(book_kind);
				request.setAttribute("bookList", bookList);
			}
			//해당 뷰에서 사용할 속성
			request.setAttribute("count", new Integer(count));
			request.setAttribute("book_kind", book_kind);
			request.setAttribute("type", new Integer(1));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/shop/showList.jsp";
	}

}
