package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.bean.LogonDBBean;
import bookshop.process.CommandAction;

public class DeleteProAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("딜리트프로액션인");
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		
		//사용자가 입력한 id,passwd를 가지고 회원 정보 삭제 후 성공 여부 반환
		LogonDBBean manager = LogonDBBean.getInstance();
		int check = manager.deleteMember(id, passwd);
		System.out.println("딜리트프로액션 아웃");
		request.setAttribute("check", new Integer(check));		
		return "/member/deletePro.jsp";
	}

}
