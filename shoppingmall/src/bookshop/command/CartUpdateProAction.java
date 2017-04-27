package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.CartDBBean;
import bookshop.process.CommandAction;

public class CartUpdateProAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub

		request.setCharacterEncoding("UTF-8");
		int cart_id = Integer.parseInt(request.getParameter("cart_id"));
		int buy_count = Integer.parseInt(request.getParameter("buy_count"));

		// cart_id에 해당하는 buy_count의 값을 수정
		CartDBBean bookProcess = CartDBBean.getInstance();
		bookProcess.updateCount(cart_id, buy_count);

		request.setAttribute("type", new Integer(1));

		return "/cart/cartUpdatePro.jsp";
	}

}
