$(document).ready(function() 
{  
	if ($("#alertSuccess").text().trim() == "")  
	{   
		$("#alertSuccess").hide();  
	} 
	$("#alertError").hide(); 
}); 

//SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 

	// Form validation-------------------  
	var status = validatePatientForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 

	// If valid------------------------  
	var t = ($("#hidAppIDSave").val() == "") ? "POST" : "PUT";
	
	var patientNIC = $('#patientNIC').val();
    var patientName = $('#patientName').val();
    var patientGender = $('#patientGender').val();
    var patientMobileNo = $('#patientMobileNo').val();
    var patientAddress = $('#patientAddress').val();
    var hidAppIDSave = $('#hidAppIDSave').val();
	
	$.ajax(
	{
		url : "PatientAPI",
		type : t,
		data : "patientNIC=" + patientNIC + "&patientName=" + patientName +"&patientGender=" + patientGender + "&patientMobileNo=" + patientMobileNo + "&patientAddress=" + patientAddress + "&hidAppIDSave=" + hidAppIDSave,
		dataType : "text",
		complete : function(response, status)
		{
			onPatientSaveComplete(response.responseText, status);
		}
	});
}); 

function onPatientSaveComplete(response, status){
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Saved.");
			$("#alertSuccess").show();
					
			$("#divPatientGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Saving.");
		$("#slertError").show();
	}else{
		$("#alertError").text("Unknown Error while Saving.");
		$("#alertError").show();
	}
	$("#hidAppIDSave").val("");
	$("#formPatient")[0].reset();
}

//UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
		{     
			$("#hidAppIDSave").val($(this).closest("tr").find('#hidAppIDUpdate').val());     
			$("#patientNIC").val($(this).closest("tr").find('td:eq(0)').text());     
			$("#patientName").val($(this).closest("tr").find('td:eq(1)').text());     
			$("#patientGender").val($(this).closest("tr").find('td:eq(2)').text());     
			$("#patientMobileNo").val($(this).closest("tr").find('td:eq(3)').text());
			$("#patientAddress").val($(this).closest("tr").find('td:eq(4)').text()); 

});


//Remove Operation
$(document).on("click", ".btnRemove", function(event){
	$.ajax(
	{
		url : "PatientAPI",
		type : "DELETE",
		data : "pId=" + $(this).data("patientid"),
		dataType : "text",
		complete : function(response, status)
		{
			onPatientDeletedComplete(response.responseText, status);
		}
	});
});

function onPatientDeletedComplete(response, status)
{
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Deleted.");
			$("#alertSuccess").show();
					
			$("#divPatientGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Deleting.");
		$("#alertError").show();
	}else{
		$("#alertError").text("Unknown Error While Deleting.");
		$("#alertError").show();
	}
}

//CLIENTMODEL
function validatePatientForm() {  
	// NIC  
	if ($("#patientNIC").val().trim() == "")  
	{   
		return "Insert NIC.";  
	} 

	// Name  
	if ($("#patientName").val().trim() == "")  
	{  
		return "Insert Name.";  
	}
	
	// Gender  
	if ($("#patientGender").val().trim() == "")  
	{   
		return "Insert Gender.";  
	} 

	// Phone
	if ($("#patientMobileNo").val().trim() == "")  
	{  
		return "Insert Phone number .";
	}
	
	//is Numerical value
	var phoneNum = $("#patientMobileNo").val().trim();  
	if (!$.isNumeric(phoneNum))  {   
		return "Insert valid phone number.";  
	} 
	
	// Address  
	if ($("#patientAddress").val().trim() == "")  
	{   
		return "Insert Address.";  
	} 
	
	
	
	return true;
}
