package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Patient1 {
	
	//A common method to connect to the DB
	   private Connection connect()
	   {
		 Connection con = null;
		 try
		 {
		   Class.forName("com.mysql.jdbc.Driver");

		 	//Provide the correct details: DBServer/DBName, username, password
		 	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/health?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		 }
		 catch (Exception e)
		 {e.printStackTrace();}
		 
		 return con;
		 
	    }
	   
	  
	   
		public String insertPatient(String NIC, String FullName, String Gender, String MobileNo, String Address)
		 {
				String output = "";
				
				try
				{
					Connection con = connect();
					
					if (con == null)
					{return "Error while connecting to the database for inserting."; }
					
					String query = " insert into patientdetails(pId,`patientNIC`,`patientName`,`patientGender`,`patientMobileNo`,`patientAddress`)"
									+ "value(?,?,?,?,?,?)";
					
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 		preparedStmt.setInt(1,0);
		 		preparedStmt.setString(2, NIC);
		 		preparedStmt.setString(3, FullName);
		 		preparedStmt.setString(4, Gender);
		 		preparedStmt.setString(5, MobileNo);
		 		preparedStmt.setString(6, Address);
		 		
		 			preparedStmt.execute();
		 			con.close();
		 			
		 			String newPatient = readPatient();
		 			output = "{\"status\":\"success\", \"data\": \"" + newPatient + "\"}";
				}
		 catch (Exception e)
		  {
			 	
			 	output = "{\"status\":\"error\", \"data\": \"Error while Inserting Patient.\"}";
			 	System.err.println(e.getMessage());
		  }
				
			return output;
	    }
		
		public String readPatient(){
			
			String output = "";
				
				try
				{
					Connection con = connect();
					if (con == null)
					 {return "Error while connecting to the database for reading."; 	
						
					}
					
					output = "<table border=\"1\"><tr><th>Patient NIC</th><th>Patient Name</th><th>Patient Gender</th><th>Contact Number</th><th>Address</th></tr>";
								
					String query = "select * from patientdetails";
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					
					while (rs.next())
					{
						String pId = Integer.toString(rs.getInt("pId"));
						String patientNIC = rs.getString("patientNIC");
						String patientName = rs.getString("patientName");
						String patientGender = rs.getString("patientGender");
						String patientMobileNo = rs.getString("patientMobileNo");
						String patientAddress = rs.getString("patientAddress");
						
						output += "<tr><td><input id='hidAppIDUpdate' name='hidAppIDUpdate' type='hidden' value='" + pId + "'>" + patientNIC + "</td>"; 
						
						
						 output += "<td>" + patientName + "</td>";
						 output += "<td>" + patientGender + "</td>";
						 output += "<td>" + patientMobileNo + "</td>";
						 output += "<td>" + patientAddress + "</td>";

						 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
							  		+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-patientid='"+ pId +"'>"+"</td></tr>"; 
						 
					}
							
						
				con.close();
				
				 output += "</table>";
				 }
				 catch (Exception e)
				 {
				 output = "Error while reading the Patient Details.";
				 System.err.println(e.getMessage());
				 }
				 return output;
	  } 
		
	  public String updatePatient(String ID, String NIC, String Fullname, String Gender, String MobileNo, String Address)
	  {
		  		String output = "";
		  		
		  		try
		  		{
		  			Connection con = connect();
		  			
		  			if (con == null)
		  			{return "Error while connecting to the database for updating."; }
		  			
		  			String query = "UPDATE patientdetails SET patientNIC=?,patientName=?,patientGender=?,patientMobileNo=?,patientAddress=? WHERE pId=?";
		        
		            PreparedStatement preparedStmt = con.prepareStatement(query);
		  			
		  			preparedStmt.setString(1, NIC);
			 		preparedStmt.setString(2, Fullname);
			 		preparedStmt.setString(3, Gender);
			 		preparedStmt.setString(4, MobileNo);
			 		preparedStmt.setString(5, Address);
		  			preparedStmt.setInt(6, Integer.parseInt(ID));
		  			
		  			preparedStmt.execute();
		  			con.close();
		  			
		  			String newPatient = readPatient();
		  			output = "{\"status\":\"success\", \"data\": \"" + newPatient + "\"}";
				   }   catch (Exception e)   {    
					   output = "{\"status\":\"error\", \"data\": \"Error while Updating Patient Details.\"}";      
					   System.err.println(e.getMessage());   
				   } 
		  		
		  		return output;
		 }
	 
	  
		public String deletePatient(String pId)
		 {
				String output = "";
				
				try
				{
					Connection con = connect();
					
					if (con == null)
				    {
					return "Error while connecting to the database for deleting."; }
					
					String query = "delete from patientdetails where pId=?";
					
					PreparedStatement preparedStmt = con.prepareStatement(query);
					
					preparedStmt.setInt(1, Integer.parseInt(pId));
					
					preparedStmt.execute();
					con.close();
					
					String newPatient = readPatient();
					  output = "{\"status\":\"success\", \"data\": \"" + newPatient + "\"}";
					  }  catch (Exception e)  {  
						  //Create JSON object 
						  output = "{\"status\":\"error\", \"data\": \"Error while Deleting Patient.\"}";
						  System.err.println(e.getMessage());  
						  
					 } 
				
				return output;
		 }

}
