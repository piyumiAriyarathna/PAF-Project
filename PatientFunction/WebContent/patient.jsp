<%@page import="model.Patient1"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Patient Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery-3.4.1.min.js"></script> 
<script src="Components/patient.js"></script> 
</head>
<body>
<div class="container"> 
<div class="row"> 
<div class="col-6">  
	<h3 align="center">Patient Registration</h3>
	
		<form id="formPatient" name="formPatient" method='post'  action="patient.jsp">   
 		Patient NIC:   
 		<input id="patientNIC" name="patientNIC" type="text" class="form-control form-control-sm"> 
 
  		<br> Patient Name:   
  		<input id="patientName" name="patientName" type="text" class="form-control form-control-sm"> 
 
 		<br> Gender:   
 		<input id="patientGender" name="patientGender" type="text" class="form-control form-control-sm"> 
 
  		<br> Mobile Number:   
  		<input id="patientMobileNo" name="patientMobileNo" type="text" class="form-control form-control-sm"> 
 
  		<br> Address:   
  		<input id="patientAddress" name="patientAddress" type="text" class="form-control form-control-sm"> 
 
  		<br>   
  		<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
  		<input type="hidden" id="hidAppIDSave" name="hidAppIDSave" value="">  
  	</form> 
 
 	<div id="alertSuccess" class="alert alert-success"></div>  
 	<div id="alertError" class="alert alert-danger"></div> 

 	<br> 
 	<div id="divPatientsGrid">
 			<%    
 			Patient1 patientObj = new Patient1();    
 				    out.print(patientObj.readPatient());   
 				    
 				    
 			%> 
 	</div>
 </div>   
 </div>  
 </div>   

</body>
</html>