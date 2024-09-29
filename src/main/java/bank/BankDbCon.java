package bank;

import static bank.BankDB.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class BankDbCon {
	static Connection con;
	public static Connection getcon() {
		 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url,uname,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
}
