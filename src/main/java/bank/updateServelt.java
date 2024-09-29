package bank;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
@WebServlet("/updtprofile")
@SuppressWarnings("serial")

public class updateServelt extends HttpServlet 
{
	public void doPost(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException 
	{
		Cookie[] ck = req.getCookies();
		if(ck==null)
		{
			
		}
		else {
			ServletContext sc = req.getServletContext();
			BankUserBean bb =(BankUserBean) sc.getAttribute("bankuserbean");
			bb.setMail(req.getParameter("mail"));
			bb.setPhn(req.getParameter("phn"));
			bb.setPass(req.getParameter("pass"));
			int k = BankDAO.updateProfile(bb);
			if(k>0) {
				PrintWriter pw = res.getWriter();
				res.setContentType("text/html");
				pw.print("Profile Updated Successfully....<br>");
				pw.print("<a href='userinterface.html' >Return</a>");

				
			}
			else {
				RequestDispatcher rd = req.getRequestDispatcher("fail.jsp");
				req.setAttribute("mesg", "Profile failed to Updated...");
				rd.forward(req, res);
			}
		}
		
		
		
	}

	private void print(String string) {
		// TODO Auto-generated method stub
		
	}

}
