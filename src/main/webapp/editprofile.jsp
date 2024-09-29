<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="bank.BankUserBean,java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% 
BankUserBean bb = (BankUserBean)application.getAttribute("bankuserbean");
out.println("Edit Profile");

%>
<form action="updtprofile" method="post">
Email<input type="email" name="mail" value="<%=bb.getMail()%>">
Phone N.O<input type="text" name="phn" value="<%=bb.getPhn() %>">
Password:<input type="text" name="pass" value="<%=bb.getPass() %>">
<input type="submit" value="Edit">
</form>
</body>
</html> 