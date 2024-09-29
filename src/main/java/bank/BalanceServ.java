package bank;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
@WebServlet("/balserv")
@SuppressWarnings("serial")
public class BalanceServ  extends HttpServlet{

	public void doPost(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException 
	{
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");
		Cookie[] ck=req.getCookies();
		if(ck.length!=0) 
		{
		  String accountNumber=req.getParameter("acc"); 
		   BankMoneyBean balance = BankDAO.checkBalance(accountNumber);
		  if(balance==null) 
		  {
			  RequestDispatcher rd = req.getRequestDispatcher("balance.html");
			  rd.include(req, res);
			  pw.print("Invalid Account Number....");
		  }
		  else 
		  {
			   pw.print("Your Account Balance:"+balance.getAmount());	  
		
		   }
		}
		
		
		
		
		
		
		
		
	}
	
}
