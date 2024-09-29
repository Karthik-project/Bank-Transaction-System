package bank;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
@WebServlet("/logserv")
@SuppressWarnings("serial")
public class LoginServlet  extends HttpServlet{

	public void doPost(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException 
	{
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");
		BankUserBean b = new BankUserBean();
		b.setAccNumber(req.getParameter("acc"));
		b.setPass(req.getParameter("pass"));
		boolean lg = BankDAO.logData(b);

        if(lg) {
    		RequestDispatcher rd1 = req.getRequestDispatcher("userinterface.html");
          	Cookie ck = new Cookie("account", req.getParameter("acc"));
        	res.addCookie(ck);
        	BankUserBean ub = BankDAO.userBean(req.getParameter("acc"));
        	ServletContext sc = req.getServletContext();
        	sc.setAttribute("bankuserbean", ub);
        	rd1.forward(req, res);
        }
        else {
    		RequestDispatcher rd2 = req.getRequestDispatcher("login.html");
        	rd2.include(req, res);
        	pw.print("Invalid email/password...");
        }
	}
}

