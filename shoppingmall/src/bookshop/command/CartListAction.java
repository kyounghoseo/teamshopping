package bookshop.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.CartDBBean;
import bookshop.bean.CartDataBean;
import bookshop.process.CommandAction;

public class CartListAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		try{

			request.setCharacterEncoding("UTF-8");
			
			String buyer = request.getParameter("buyer");
			
			List<CartDataBean> cartLists = null;
			int count = 0;
			
			//�ش� buyer�� ��ٱ��� ����� ���� ��
			CartDBBean bookProcess = CartDBBean.getInstance();
			count = bookProcess.getListCount(buyer);
			
			//�ش� buyer�� ��ٱ��� ����� ������ ����
			if(count >0){
				cartLists = bookProcess.getCart(buyer, count);
				request.setAttribute("cartLists", cartLists);
			}
			request.setAttribute("count", new Integer(count));
			request.setAttribute("type", new Integer(1));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return "/cart/cartList.jsp";
	}

}
