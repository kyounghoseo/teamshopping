package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.process.CommandAction;

public class CartUpdateFormAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try{

			request.setCharacterEncoding("UTF-8");
			
			String cart_id = request.getParameter("cart_id");
			String buy_count = request.getParameter("buy_count");
			
			request.setAttribute("cart_id", cart_id);
			request.setAttribute("buy_count", buy_count);
			request.setAttribute("type", new Integer(1));
					
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/cart/cartUpdateForm.jsp";
	}
	

}
