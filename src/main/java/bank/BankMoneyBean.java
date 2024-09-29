package bank;

import java.io.*;

public class BankMoneyBean implements Serializable {
 private String name,accNum;
 private int amount;
 public BankMoneyBean() {}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getAccNum() {
	return accNum;
}
public void setAccNum(String accNum) {
	this.accNum = accNum;
}
public int getAmount() {
	return amount;
}
public void setAmount(int amount) {
	this.amount = amount;
}
 
}
