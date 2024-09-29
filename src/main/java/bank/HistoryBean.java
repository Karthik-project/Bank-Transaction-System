package bank;

import java.io.*;

public class HistoryBean implements Serializable {
private String accNum;
private int credit,debiet,bAmount;
public HistoryBean() {}
public String getAccNum() {
	return accNum;
}
public void setAccNum(String accNum) {
	this.accNum = accNum;
}
public int getCredit() {
	return credit;
}
public void setCredit(int credit) {
	this.credit = credit;
}
public int getDebiet() {
	return debiet;
}
public void setDebiet(int debiet) {
	this.debiet = debiet;
}
public int getbAmount() {
	return bAmount;
}
public void setbAmount(int bAmount) {
	this.bAmount = bAmount;
}

}
