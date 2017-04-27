package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.CartDBBean;
import bookshop.bean.CartDataBean;
import bookshop.process.CommandAction;

public class InsertCartAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");
		
		//장바구니에 추가할 정보를 파라미터에서 받아냄
		int buy_count = Integer.parseInt(request.getParameter("buy_count"));
		int book_id = Integer.parseInt(request.getParameter("book_id"));
		String book_title = request.getParameter("book_title");
		String book_image = request.getParameter("book_image");
		int buy_price = (int)Float.parseFloat(request.getParameter("buy_price"));
		String buyer = request.getParameter("buyer");
		
		//장바구니에 추가하기 위한 정복 구성
		CartDataBean cart = new CartDataBean();
		cart.setBook_id(book_id);
		cart.setBook_image(book_image);
		cart.setBook_title(book_title);
		cart.setBuy_count(buy_count);
		cart.setBuy_price(buy_price);
		cart.setBuyer(buyer);
		
		//장바구니에 추가
		CartDBBean bookProcess = CartDBBean.getInstance();
		bookProcess.insertCart(cart);
		
		
		return "/cart/insertCart.jsp";
	}

}
