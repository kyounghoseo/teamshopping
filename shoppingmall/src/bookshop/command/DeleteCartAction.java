package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.CartDBBean;
import bookshop.process.CommandAction;

public class DeleteCartAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		try{

			request.setCharacterEncoding("UTF-8");

			String list = request.getParameter("list");
			String msg = "";

			CartDBBean bookProcess = CartDBBean.getInstance();

			if (list.equals("all")) { // list ���� all�̸� ����
				// �ش� buyer�� ��ٱ��� ����� ��� ����
				String buyer = request.getParameter("buyer");
				bookProcess.deleteAll(buyer);
				msg = "��ٱ��ϰ� ��� ��������ϴ�.";

			} else { // list���� all�̿� (cart_id)�� ���̸� ����
						// list���� �ش��ϴ� ���ڵ� ����
				bookProcess.deleteList(Integer.parseInt(list));
				msg = "������ �׸��� �����Ǿ����ϴ�.";
			}

			request.setAttribute("msg", msg);
			request.setAttribute("type", new Integer(1));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "cart/deleteCart.jsp";

	}

}
