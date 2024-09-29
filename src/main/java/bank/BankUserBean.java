package bank;

import java.io.Serializable;

public class BankUserBean implements Serializable{
 private String name,accNumber,mail,phn,pass;
 public BankUserBean() {}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getAccNumber() {
	return accNumber;
}
public void setAccNumber(String accNumber) {
	this.accNumber = accNumber;
}
public String getMail() {
	return mail;
}
public void setMail(String mail) {
	this.mail = mail;
}
public String getPhn() {
	return phn;
}
public void setPhn(String phn) {
	this.phn = phn;
}
public String getPass() {
	return pass;
}
public void setPass(String pass) {
	this.pass = pass;
}
 
}
