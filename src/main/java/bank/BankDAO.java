package bank;
import java.awt.print.Book;
import java.util.*;
import java.net.http.HttpResponse;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static bank.BankDB.*;
import static bank.BankUserBean.*;
import static bank.BankMoneyBean.*;
public class BankDAO {
	
	public static int insertData(BankUserBean b) {
		Connection con=BankDbCon.getcon();
		int k=0;
		try {

			PreparedStatement ps = con.prepareStatement("insert into bankusers values(?,?,?,?,?)");
			ps.setString(1, b.getName());
			ps.setString(2, b.getAccNumber());
			ps.setString(3, b.getMail());
			ps.setString(4, b.getPhn());
			ps.setString(5, b.getPass());
			k = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return k;
	}
	public static boolean logData(BankUserBean b) {
		Connection con=BankDbCon.getcon();
		boolean k=false;
		try {
		PreparedStatement ps = con.prepareStatement("select * from bankusers where acc=?");
		ps.setString(1, b.getAccNumber());
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			k=true;
		}
		
		else {
			k=false;
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return k;
	}
	
	public static HttpServletRequest senderMoney(String acc, HttpServletRequest req)  {
		int amount=0;
		String senderName=null;
		Connection con=BankDbCon.getcon();
		try {
			PreparedStatement ps = con.prepareStatement("select * from bankmoney where acc=?");
			ps.setString(1, acc);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				//senderName=rs.getString(1);
				req.setAttribute("sendername", rs.getString(1));
				req.setAttribute("senderamt", rs.getInt(3));
				//amount=rs.getInt(3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	     return req;
	}
	

	/*
	 * public static boolean transactionUpdate(BankMoneyBean sender,BankMoneyBean
	 * receiver,int receiverAmt ) { Connection con=BankDbCon.getcon(); boolean
	 * transaction=false; try {
	 * 
	 * String senderAccNum = sender.getAccNum(); int senderAmount =
	 * sender.getAmount(); String receiverAccNum = receiver.getAccNum(); int
	 * receiverAmount = receiver.getAmount(); Statement sc = con.createStatement();
	 * int s1 = sc.
	 * executeUpdate("update bankmoney set amount=amount-recAmount where acc='senderAccNum'"
	 * ); int r1 = sc.
	 * executeUpdate("update bankmoney set amount=amount+recAmount where acc='receiverAccNum'"
	 * ); int s2 = sc.
	 * executeUpdate("insert into transhistory (acc,debut,balanceamount) values('senderAccNum',-recAmount,senderAmount-recAmount)"
	 * ); int r2 = sc.
	 * executeUpdate("insert into transhistory (acc,credit,balanceamount) values('receiverAccNum',+recAmount,receiverAmount+recAmount)"
	 * ); if(s1>0 && r1>0 && s2>0 && r2>0) { transaction=true; } else {
	 * transaction=false; } } catch (Exception e) { e.printStackTrace(); } return
	 * transaction; }
	 */ 
	 public static boolean transactionUpdate(BankMoneyBean sender, BankMoneyBean receiver, int receiverAmt) {
	        Connection con = null;
	        boolean transaction = false;
	        try {
	            con = BankDbCon.getcon();
	            con.setAutoCommit(false); // Start transaction

	            String senderAccNum = sender.getAccNum();
	            int senderAmount = sender.getAmount();
	            String receiverAccNum = receiver.getAccNum();
	            int receiverAmount = receiver.getAmount();
	            int recAmount = receiverAmt;

	            // Prepare statements
	            String updateSenderSQL = "UPDATE bankmoney SET amount = amount - ? WHERE acc = ?";
	            String updateReceiverSQL = "UPDATE bankmoney SET amount = amount + ? WHERE acc = ?";
	            String insertSenderHistorySQL = "INSERT INTO transhistory (acc, debut, balanceamount) VALUES (?, ?, ?)";
	            String insertReceiverHistorySQL = "INSERT INTO transhistory (acc, credit, balanceamount) VALUES (?, ?, ?)";

	            PreparedStatement updateSenderStmt = con.prepareStatement(updateSenderSQL);
	            PreparedStatement updateReceiverStmt = con.prepareStatement(updateReceiverSQL);
	            PreparedStatement insertSenderHistoryStmt = con.prepareStatement(insertSenderHistorySQL);
	            PreparedStatement insertReceiverHistoryStmt = con.prepareStatement(insertReceiverHistorySQL);

	            // Execute updates
	            updateSenderStmt.setInt(1, recAmount);
	            updateSenderStmt.setString(2, senderAccNum);
	            int s1 = updateSenderStmt.executeUpdate();

	            updateReceiverStmt.setInt(1, recAmount);
	            updateReceiverStmt.setString(2, receiverAccNum);
	            int r1 = updateReceiverStmt.executeUpdate();

	            insertSenderHistoryStmt.setString(1, senderAccNum);
	            insertSenderHistoryStmt.setInt(2, -recAmount);
	            insertSenderHistoryStmt.setInt(3, senderAmount - recAmount);
	            int s2 = insertSenderHistoryStmt.executeUpdate();

	            insertReceiverHistoryStmt.setString(1, receiverAccNum);
	            insertReceiverHistoryStmt.setInt(2, recAmount);
	            insertReceiverHistoryStmt.setInt(3, receiverAmount + recAmount);
	            int r2 = insertReceiverHistoryStmt.executeUpdate();

	            if (s1 > 0 && r1 > 0 && s2 > 0 && r2 > 0) {
	                con.commit(); // Commit transaction if all updates successful
	                transaction = true;
	            } else {
	                con.rollback(); // Rollback if any update fails
	            }
	        } catch (SQLException e) {
	            try {
	                if (con != null) {
	                    con.rollback(); // Rollback in case of exception
	                }
	            } catch (SQLException rollbackEx) {
	                rollbackEx.printStackTrace();
	            }
	            e.printStackTrace();
	        } finally {
	            // Close resources in the finally block
	            try {
	                if (con != null) {
	                    con.setAutoCommit(true); // Restore auto-commit mode
	                    con.close();
	                }
	            } catch (SQLException closeEx) {
	                closeEx.printStackTrace();
	            }
	        }
	        return transaction;
	    }
	
	public static BankMoneyBean checkBalance(String account) {
	  BankMoneyBean b = null;
	  Connection con=BankDbCon.getcon();
	  	  try {
		PreparedStatement ps = con.prepareStatement("select amount from bankmoney where acc=?");
		ps.setString(1, account);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			b=new BankMoneyBean();					
			b.setAmount(rs.getInt(1));
		}
		
			
	} catch (Exception e) {
		e.printStackTrace();
	}
	  return b;
  }
  public static ArrayList<HistoryBean> transactionHistory(String acc){
	  ArrayList<HistoryBean> a=new ArrayList<HistoryBean>();
	  Connection con=BankDbCon.getcon();
	  	  try {
		PreparedStatement ps = con.prepareStatement("select * from transhistory where acc=?");
		ps.setString(1, acc);
		ResultSet rs = ps.executeQuery();
	
		while(rs.next()) 
		{
			HistoryBean hb = new HistoryBean();
			hb.setAccNum(rs.getString(1));
			hb.setCredit(rs.getInt(2));
			hb.setDebiet(rs.getInt(3));
			hb.setbAmount(rs.getInt(4));
			a.add(hb);
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	  return a;
  }
  public static BankUserBean userBean(String accNumber) {
	  BankUserBean b=null;
	  Connection con=BankDbCon.getcon();
	  try {
		PreparedStatement ps = con.prepareStatement("select * from bankusers where acc=?");
		ps.setString(1, accNumber);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
		    b = new BankUserBean();
			b.setName(rs.getString(1));
			b.setAccNumber(rs.getString(2));
			b.setMail(rs.getString(3));
			b.setPhn(rs.getString(4));
			b.setPass(rs.getString(5));
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return b;
  }
  public static BankMoneyBean bankMoneyBean(String accNumber) {
	  BankMoneyBean b = null;
	  Connection con=BankDbCon.getcon();
	  try {
		 PreparedStatement ps = con.prepareStatement("select * from bankmoney where acc=?");
		 ps.setString(1, accNumber);
		 ResultSet rs = ps.executeQuery();
		 if(rs.next()) {
			b= new BankMoneyBean();
			b.setName(rs.getString(1));
			b.setAccNum(rs.getString(2));
			b.setAmount(rs.getInt(3));
					
		 }
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
	  return b;
  }
  public static int updateProfile(BankUserBean b) {
	  int k=0;
	  Connection con=BankDbCon.getcon();
	  try {
		  PreparedStatement ps = con.prepareStatement("update bankusers set mail=?,phn=?,pass=? where name=? and acc=? ");
		  ps.setString(1, b.getMail());
		  ps.setString(2, b.getPhn());
		  ps.setString(3, b.getPass());
		  ps.setString(4, b.getName());
		  ps.setString(5, b.getAccNumber());
		   k = ps.executeUpdate();
	  }
	  catch(Exception e) {
		  e.printStackTrace();
	  }
	  return k;
  }
}