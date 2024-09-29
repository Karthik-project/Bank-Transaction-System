package bank;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
@WebServlet("/newserv")
@SuppressWarnings("serial")

public class NewUserServlet extends HttpServlet {
	public void doPost(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException 
	{
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");
		BankUserBean b = new BankUserBean();
		b.setName(req.getParameter("name"));
		b.setAccNumber(req.getParameter("acc"));
		b.setMail(req.getParameter("mail"));
		b.setPhn(req.getParameter("phn"));
		b.setPass(req.getParameter("pass"));
		int k=BankDAO.insertData(b);
		if(k>0) {
			pw.print("User Created... ");
			pw.print("<a href='index.html'>Index Page</a>");

		}
		else {
			pw.print("User Not Created.....");
		}
	}
	
}
