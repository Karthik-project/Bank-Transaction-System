package bank;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
@WebServlet("/historyserv")
@SuppressWarnings("serial")
public class HistoryServlet extends HttpServlet 
{


	public void doPost(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException 
	{
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");
		Cookie[] ck=req.getCookies();
		if(ck.length!=0) 
		{
		 
		 String acc=req.getParameter("acc");
		 ArrayList<HistoryBean> result = BankDAO.transactionHistory(acc);
		 if(result.isEmpty())
		 {
			RequestDispatcher rd = req.getRequestDispatcher("transactionhistory.html");
			  rd.include(req, res);
			  pw.print("Invalid Account Number....");
		 
		 }
		 else 
		 {
			 RequestDispatcher rd = req.getRequestDispatcher("history.jsp");
			 req.setAttribute("arylist", result);
			  rd.forward(req, res);
			  
			 
		 }
	    }
	}
}
