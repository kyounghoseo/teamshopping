package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.CartDBBean;
import bookshop.bean.CartDataBean;
import bookshop.process.CommandAction;

public class InsertCartAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)  {
		// TODO Auto-generated method stub
		try{

			request.setCharacterEncoding("UTF-8");
			
			//��ٱ��Ͽ� �߰��� ������ �Ķ���Ϳ��� �޾Ƴ�
			int buy_count = Integer.parseInt(request.getParameter("buy_count"));
			int book_id = Integer.parseInt(request.getParameter("book_id"));
			String book_title = request.getParameter("book_title");
			String book_image = request.getParameter("book_image");
			int buy_price = (int)Float.parseFloat(request.getParameter("buy_price"));
			String buyer = request.getParameter("buyer");
			
			//��ٱ��Ͽ� �߰��ϱ� ���� ���� ����
			CartDataBean cart = new CartDataBean();
			cart.setBook_id(book_id);
			cart.setBook_image(book_image);
			cart.setBook_title(book_title);
			cart.setBuy_count(buy_count);
			cart.setBuy_price(buy_price);
			cart.setBuyer(buyer);
			
			//��ٱ��Ͽ� �߰�
			CartDBBean bookProcess = CartDBBean.getInstance();
			bookProcess.insertCart(cart);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return "/cart/insertCart.jsp";
	}

}
