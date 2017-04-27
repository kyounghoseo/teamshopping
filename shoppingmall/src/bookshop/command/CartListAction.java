package bookshop.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.CartDBBean;
import bookshop.bean.CartDataBean;
import bookshop.process.CommandAction;

public class CartListAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		String buyer = request.getParameter("buyer");
		
		List<CartDataBean> cartLists = null;
		int count = 0;
		
		//해당 buyer의 장바구니 목록의 수를 얻어냄
		CartDBBean bookProcess = CartDBBean.getInstance();
		count = bookProcess.getListCount(buyer);
		
		//해당 buyer의 장바구니 목록이 있으면 수행
		if(count >0){
			cartLists = bookProcess.getCart(buyer, count);
			request.setAttribute("cartLists", cartLists);
		}
		request.setAttribute("count", new Integer(count));
		request.setAttribute("type", new Integer(1));
		
		
		return "/cart/cartList.jsp";
	}

}
