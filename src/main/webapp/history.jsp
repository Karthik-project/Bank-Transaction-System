<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="bank.HistoryBean,java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%-- <%
 ArrayList<HistoryBean> al=(ArrayList<HistoryBean>) request.getAttribute("arylist");
Iterator<HistoryBean> il =al.iterator();
while(il.hasNext()){
	  HistoryBean bb= (HistoryBean)il.next();
	  out.println("Book code"+bb.getAccNum()+"<br>"+"Book Name"+bb.getDebiet()+"<br>"+"Book Price"+bb.getCredit()+"<br>"+"Book Qty"+bb.getbAmount());
}

%> --%>
<table border="1">
        <tr>
            <th>Account Number</th>
            <th>Credit</th>
            <th>Debited</th>
            <th>Balance</th>
        </tr>
        <% 
            ArrayList<HistoryBean> al
            = (ArrayList<HistoryBean>) request.getAttribute("arylist");
            Iterator<HistoryBean> il =al.iterator();
           if(il.hasNext()){
            while (il.hasNext()) {
          	  HistoryBean bb= (HistoryBean)il.next();
        %>
                    <tr>
                        <td><%= bb.getAccNum() %></td>
                        <td><%= bb.getDebiet() %></td>
                        <td><%= bb.getCredit() %></td>
                        <td><%= bb.getbAmount() %></td>
                    </tr>
        <% 
                }
            } else {
        %>
                <tr>
                    <td colspan="4">No history records found.</td>
                </tr>
        <% 
            }
        %>
    </table></body>
</html>