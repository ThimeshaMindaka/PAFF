<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Receipt</title>
	<style type="text/css">
		table {border : 0;}
		table td { padding: 10px; }
	</style>
</head>
<body>
<div align="center"> 
		<h1>Payment Done.</h1>
		<form action="execute_payment" method="post">
		<table>
		
		<tr>
			<td>Merchant:</td>
			<td>Company Jaedel Ltd. </td>
		</tr>
		<tr>
			<td>Payer:</td>
			<td>${payer.firstName} ${payer.lastName }</td>
		</tr>
		<tr>
			<td>Payment for this month :</td>
			<td>${transaction.amount.details.subtotal} </td>
		</tr>
		<tr>
			<td>Charge for Arrears</td>
			<td>${transaction.amount.details.arrears} </td>
		</tr>
		<tr>
			<td>Tax:</td>
			<td>${transaction.amount.details.Tax} </td>
		</tr>
		<tr>
			<td>Total:</td>
			<td>${transaction.amount.total} </td>
		</tr>
		<tr><td><br></td></tr>
		
		
		</table>
		</form>
</div>
</body>
</html>