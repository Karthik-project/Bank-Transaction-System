package bank;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import org.apache.catalina.mbeans.UserMBean;
@WebServlet("/sendserv")
@SuppressWarnings("serial")
public class SendServlet extends HttpServlet {

	public void doPost(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException 
	{
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");
		Cookie[] ck=req.getCookies();
		if(ck.length!=0) 
		{
		String receiveracc=req.getParameter("recacc");
	    int  receiveramount=Integer.parseInt(req.getParameter("recamt"));
	    String senderAcc=ck[0].getValue();
	    ServletContext sc = req.getServletContext();
	    BankMoneyBean senderBean = BankDAO.bankMoneyBean(senderAcc);
	    BankMoneyBean receiverBean = BankDAO.bankMoneyBean(receiveracc);
	    int senderamount=senderBean.getAmount();
	    if(senderamount>=receiveramount) 
	    {
	    	boolean transactionUpdate = BankDAO.transactionUpdate(senderBean, receiverBean,receiveramount);
	    	if(transactionUpdate)
	    	{
	    	    req.getRequestDispatcher("transaction.jsp").forward(req, res);
	    	    
	    	}
	    }
	    else
	    {
	    	pw.print("In-Sufficient Balance....<br>");
	    	req.getRequestDispatcher("sendinterface.html").forward(req, res);
	    }
	    
		}
	
	}
}
