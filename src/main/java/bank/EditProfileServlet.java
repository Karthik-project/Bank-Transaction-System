package bank;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
@WebServlet("/editserv")
@SuppressWarnings("serial")
public class EditProfileServlet extends HttpServlet {

	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		Cookie[] ck = req.getCookies();
		if(ck==null) 
		{
			req.setAttribute("mesg", "Session Expired Login Again...");
			req.getRequestDispatcher("fail.jsp").forward(req, res);
	     }
		else 
		{
			ServletContext sc = req.getServletContext();
			BankUserBean bb = (BankUserBean)sc.getAttribute("bankuserbean");
			req.getRequestDispatcher("editprofile.jsp").forward(req, res);
		}
		}
		
		
		
	}

