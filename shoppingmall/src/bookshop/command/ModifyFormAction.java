package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.LogonDBBean;
import bookshop.bean.LogonDataBean;
import bookshop.process.CommandAction;

public class ModifyFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response)  {
		// TODO Auto-generated method stub
		try{

			request.setCharacterEncoding("utf-8");
			System.out.println("모디리폼액션인!!!! 왜 프로로는 안가냐");
			String id = request.getParameter("id");
			String passwd = request.getParameter("passwd");
			
			
			//회원정보를 수정하기 위한 정보를 얻어냄
			LogonDBBean manager = LogonDBBean.getInstance();
			LogonDataBean m = manager.getMember(id,passwd);
			
			request.setAttribute("m", m);
			request.setAttribute("id", id);
			request.setAttribute("type", new Integer(1));
			System.out.println("모디리폼액션아웃!!!!");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/member/modifyForm.jsp";
	}

}
