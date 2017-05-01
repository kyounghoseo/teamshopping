package bookshop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookshop.process.CommandAction;

public class ManagerLoginFormAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, 
			HttpServletResponse response){
		
		return "/mngr/logon/mLoginForm.jsp";
	}
	
}
