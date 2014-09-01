
$(document).ready(function($){	
	$('#thePatientDataTable').dataTable( {			
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"aoColumns": [
		              null,
		              null,
		              null,
		              { "sType": "date-uk" },
		              null
		          ]
	} );
});

$(document).ready(function() { 
    $("#membershipExpirationDate").datepicker({ 
        dateFormat: "dd/mm/yy" ,
        
        onSelect: function() { 
	      $(this).focus(); 
	   }         	
    });
});
$(document).ready(function() { 
    $("#insuranceExpirationDate").datepicker({ 
        dateFormat: "dd/mm/yy" ,       
        onSelect: function() { 
	      $(this).focus(); 
	   }         	
    });
});

$(document).ready(function(){	
	$("#addpatient_dialog").hide();		
	$('#addbutton').click(function(){
		unassignedPatientUserNames = document.getElementById("unassignedPatientUserNames");
		for(var i=0; i<unassignedPatientUserNames.options.length; i++){		
			unassignedPatientUserNames.options[i].value = null;
			unassignedPatientUserNames.options[i].text = null;
			unassignedPatientUserNames.options.length = 0;
		}			
		req = createRequest();	
		req.onreadystatechange=function(){					
			  if (req.readyState==4 && req.status==200){					 
				 var temp = req.responseText;
				 //alert('temp= '+temp);
				 obj = JSON.parse(temp);	
				 //alert('obj '+obj.length);
				 unassignedPatientUserNames.options[0] = new Option('','');
				 for(var i=0; i<obj.length; i++){						
					 unassignedPatientUserNames.options[unassignedPatientUserNames.options.length] = new Option(obj[i],obj[i]);							 
				 }						 
				 $("#addpatient_dialog").dialog({ modal: true, width: 875, title: 'Add Patient', autoOpen:false, height: 600,dialogClass: 'no-close'});
				 $("#addpatient_dialog").dialog('open');					 
		  };//end of if 
		};//end of function
		req.open("GET","displayaddpatient?t="+Math.random(),true);
		req.send();
	});	 
});

$(document).ready(function() {
	$('#addpatientsavebutton').click(function(){
		$("#addpatientForm").validate({				
			ignore:'hidden',
	  		rules:{
	  			userName:{
	  				required:true
	  			},
	  			enrollmentMode:{
	  				required:true
	  			},
	  			employmentType:{
	  				required:true
	  			},
	  			relationshipType:{
	  				required:true
	  			},
	  			phoneNumber1:{
	  				required:true
	  			},
	  			emailAddress:{
	  				email:true
	  			},
	  			sumAssured:{
	  				number:true
	  			}
	  		},
	  		messages:{
	  			userName:"Select User Name.",
	  			enrollmentMode:"Select enrollment mode.",
	  			employmentType:"Select employment type.",
	  			relationshipType:"Select relationship type with primary insured.",
	  			phoneNumber1:"Enter primary contact phone."	  			
	  		},
	  		errorElement:"div",
			wrapper:"div",
			errorPlacement: function(error, element) {
				error.appendTo( element.parent().next() );
				offset=element.offset();
				error.insertBefore(element);
				error.addClass('message');
				error.css('position','absolute');	
				error.css('margin-left','-470px');
				error.css('margin-top','6px');
			}
		
		});
		document.getElementById("huserName").value = document.getElementById("unassignedPatientUserNames").value;
		//alert("userName ="+document.getElementById("huserName").value);
		document.getElementById("hphoneNumber1").value = document.getElementById("phoneNumber1").value;
		document.getElementById("hphoneNumber2").value = document.getElementById("phoneNumber2").value;
		document.getElementById("hflatNumber").value = document.getElementById("flatNumber").value;
		document.getElementById("hlocality").value = document.getElementById("locality").value;
		document.getElementById("hstreet").value = document.getElementById("street").value;
		document.getElementById("hcity").value = document.getElementById("city").value;
		document.getElementById("hpincode").value = document.getElementById("pincode").value;
		document.getElementById("hstate").value = document.getElementById("state").value;
		document.getElementById("hcountry").value = document.getElementById("country").value;
		document.getElementById("hemailAddress").value = document.getElementById("emailAddress").value;
		document.getElementById("hmembershipExpirationDate").value = document.getElementById("membershipExpirationDate").value;
		document.getElementById("hinsuranceExpirationDate").value = document.getElementById("insuranceExpirationDate").value;
	});
});

$(document).ready(function(){	
	$('#addpatientclosebutton').click(function(){
		$("#addpatient_dialog").dialog("close");
		return false;	
	});
	
});
$(document).ready(function(){
	$('#updatepatient_dialog').hide();
	$('#updatebutton').click(function(){		
		//alert('update button is clicked');
		var ids = document.getElementsByName('patientIdRadio');
		var id_value='';
		for(var i = 0; i < ids.length; i++){
		    if(ids[i].checked){
		    	id_value = ids[i].value;		    	
		    }
		}
		//alert(id_value);
		var indata = id_value.split("|");		
		id_value = indata[0];
		//alert('id_value again='+id_value);
		if(id_value ==''){
			alert('Please select the patient to be updated by selecting the Radio Button');
		}else{	
			req = createRequest();
			//alert('update='+id_value);
			//alert('request ='+req);
			req.onreadystatechange=function(){					
				  if (req.readyState==4 && req.status==200){					
					 var temp = req.responseText ;		 		 		 
					 obj = JSON.parse(temp);
					 //alert('obj = '+obj);
					 //alert('temp ='+temp);
					 document.getElementById("update_label_userName").innerHTML = obj[2].userName;					 
					 document.getElementById("update_membershipType").value = obj[0].membershipType;
					 document.getElementById("update_employmentType").value = obj[0].employmentType;
					 document.getElementById("update_enrollmentMode").value = obj[0].enrollmentMode;
					 document.getElementById("update_phoneNumber1").value = obj[1].phoneNumber1;
					 document.getElementById("update_relationshipType").value = obj[0].relationshipType;
					 document.getElementById("update_membershipExpirationDate").value = obj[0].strMembershipExpirationDate;					 
					 document.getElementById("update_policyNumber").value = obj[0].policyNumber;
					 document.getElementById("update_primaryInsured").value = obj[0].primaryInsured;
					 document.getElementById("update_insuranceExpirationDate").value = obj[0].strInsuranceExpirationDate;
					 document.getElementById("update_issuedBy").value = obj[0].issuedBy;
					 document.getElementById("update_webURL").value = obj[0].webURL;
					 document.getElementById("update_sumAssured").value = obj[0].sumAssured;
					 document.getElementById("update_emailAddress").value = obj[1].emailAddress;
					 document.getElementById("update_note").value = obj[0].note;
					 document.getElementById("update_enrollmentReferredBy").value = obj[0].enrollmentReferredBy;
					 document.getElementById("update_companyName").value = obj[0].companyName;
					 document.getElementById("update_phoneNumber2").value = obj[1].phoneNumber2;
					 document.getElementById("update_flatNumber").value = obj[1].flatNumber;
					 document.getElementById("update_locality").value = obj[1].locality;
					 document.getElementById("update_street").value = obj[1].street;
					 document.getElementById("update_city").value = obj[1].city;
					 document.getElementById("update_pincode").value = obj[1].pincode;
					 document.getElementById("update_state").value = obj[1].state;
					 document.getElementById("update_country").value = obj[1].country;
					 					
			$("#updatepatient_dialog").dialog({ modal: true, width: 875, title: 'Update Patient', autoOpen:false, height: 600, dialogClass: 'no-close'});
			var form_original_data = $("#updatepatientForm").serialize();
			//alert(form_original_data);
			$("#updatepatientsavebutton").click(function(){
				if ($("#updatepatientForm").serialize() != form_original_data) {
					//alert('updating = '+$("#updateuserForm").serialize() );	
					document.getElementById("update_hphoneNumber1").value = document.getElementById("update_phoneNumber1").value;
					//alert('update_hphoneNumber1 ='+document.getElementById("update_hphoneNumber1").value);
					document.getElementById("update_hphoneNumber2").value = document.getElementById("update_phoneNumber2").value;
					//alert('update_hphoneNumber2 ='+document.getElementById("update_hphoneNumber2").value);
					document.getElementById("update_hflatNumber").value = document.getElementById("update_flatNumber").value;
					//alert('update_hflatNumber ='+document.getElementById("update_hflatNumber").value);
					document.getElementById("update_hlocality").value = document.getElementById("update_locality").value;
					//alert('update_hlocality ='+document.getElementById("update_hlocality").value);
					document.getElementById("update_hstreet").value = document.getElementById("update_street").value;
					//alert('update_hstreet ='+document.getElementById("update_hstreet").value);
					document.getElementById("update_hcity").value = document.getElementById("update_city").value;
					//alert('update_hcity ='+document.getElementById("update_hcity").value);
					document.getElementById("update_hpincode").value = document.getElementById("update_pincode").value;
					//alert('update_hpincode ='+document.getElementById("update_hpincode").value);
					document.getElementById("update_hstate").value = document.getElementById("update_state").value;
					//alert('update_hstate ='+document.getElementById("update_hstate").value);
					document.getElementById("update_hcountry").value = document.getElementById("update_country").value;
					//alert('update_hcountry ='+document.getElementById("update_hcountry").value);
					document.getElementById("update_hemailAddress").value = document.getElementById("update_emailAddress").value;
					//alert('update_hemailAddress ='+document.getElementById("update_hemailAddress").value);
					updatepatient();
				}else{							
					return false;
				}		
			});			
		    $("#updatepatient_dialog").dialog('open');
			//alert('hiddenuserName = '+document.getElementById("update_hiddenuserName").value);		 
		  };//end of if 
		};//end of function	
				 
		   req.open("POST","populatePatientDetails?selectedId="+id_value+"&t="+Math.random(),true);		
		   req.send();	
			
		};
	});
});
$(document).ready(function() { 
    $("#update_insuranceExpirationDate").datepicker({ 
        dateFormat: "dd/mm/yy" ,       
        onSelect: function() { 
	      $(this).focus(); 
	   }         	
    });
    $("#update_membershipExpirationDate").datepicker({ 
        dateFormat: "dd/mm/yy" ,       
        onSelect: function() { 
	      $(this).focus(); 
	   }         	
    });
});
function updatepatient(){
	//alert('update save button is clicked');	
	
	var ids = document.getElementsByName('patientIdRadio');
	var id_value='';
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;	    	
	    }
	}
	var indata = id_value.split("|");
	id_value = indata[0];	
	//alert(id_value);
	$("#updatepatientForm").validate({
  		rules:{  			
  			enrollmentMode:{
  				required:true
  			},
  			employmentType:{
  				required:true
  			},
  			relationshipType:{
  				required:true
  			},
  			phoneNumber1:{
  				required:true
  			},
  			emailAddress:{
  				email:true
  			},
  			sumAssured:{
  				number:true
  			}
  		},
  		messages:{  			
  			enrollmentMode:"Select enrollment mode.",
  			employmentType:"Select employment type.",
  			relationshipType:"Select relationship type with primary insured.",
  			phoneNumber1:"Enter primary contact phone."	  			
  		},
  		errorElement:"div",
		wrapper:"div",
		errorPlacement: function(error, element) {
			error.appendTo( element.parent().next() );
			offset=element.offset();
			error.insertBefore(element);
			error.addClass('message');
			error.css('position','absolute');	
			error.css('margin-left','-470px');
			error.css('margin-top','6px');
		}	
	});
	document.getElementById("updatepatientForm").action="updatepatient?selectedId="+id_value;
}
$(document).ready(function(){	
	$('#updatepatientclosebutton').click(function(){
		$("#updatepatient_dialog").dialog("close");
		return false;	
	});
	
});
function deletepatient(){
	//alert('delete button is clicked');		
	var ids = document.getElementsByName('patientIdRadio');
	var id_value='';
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;		    	
	    }
	}
	var indata = id_value.split("|");
	id_value = indata[0];
	
	if(id_value ==''){
		alert('Please Select The Patient To Be Deleted By Selecting The Radio Button');
	}else{
		var answer = confirm('Do you want to delete Patient?');
		if(!answer){				
			document.getElementById("deletepatientForm").action="displaypatientadmin";
		}else{
			document.getElementById("deletepatientForm").action="deletepatient?selectedId="+id_value;				
		}
	}		
}

$(document).ready(function(){	
	$('#addpatientseccontactclosebutton').click(function(){
		$("#addsecondarycontact_dialog").dialog("close");
		return false;	
	});
	
});
$(document).ready(function(){
	$('#addpatientseccontactsavebutton').click(function(){	
		var ids = document.getElementsByName('patientIdRadio');
		var id_value='';
		for(var i = 0; i < ids.length; i++){
		    if(ids[i].checked){
		    	id_value = ids[i].value;		    	
		    }
		}
		//alert(id_value);
		var indata = id_value.split("|");		
		id_value = indata[0];
		$("#addpatientsecondarycontactForm").validate({
	  		rules:{
	  			phoneNumber1:"required"  					
	  		},
	  		messages:{
	  			phoneNumber1:"Enter Phone Number 1."  				
	  		},
	  		errorElement:"div",
			wrapper:"div",
			errorPlacement: function(error, element) {
				error.appendTo( element.parent().next() );
				offset=element.offset();
				error.insertBefore(element);
				error.addClass('message');
				error.css('position','absolute');	
				error.css('margin-left','-470px');
				error.css('margin-top','15px');
			}
		});
		document.getElementById("addpatientsecondarycontactForm").action="addpatientsecondarycontact?selectedId="+id_value;
	});	
});
$(document).ready(function(){
	$("#addsecondarycontact_dialog").hide();
	$('#addsecondarycontactbutton').click(function() {
		var ids = document.getElementsByName('patientIdRadio');
		var id_value='';
		for(var i = 0; i < ids.length; i++){
		    if(ids[i].checked){
		    	id_value = ids[i].value;		    	
		    }
		}
		//alert(id_value);
		var indata = id_value.split("|");		
		id_value = indata[0];
		//alert('id_value again='+id_value);
		if(id_value ==''){
			alert('Please select the patient by selecting the Radio Button');
		}else{
			req = createRequest();	
			req.onreadystatechange=function(){					
				  if (req.readyState==4 && req.status==200){					 
					 var temp = req.responseText;
					 //alert('temp= '+temp);
					 obj = JSON.parse(temp);	
					 //alert('obj '+obj.additionAllowed);
					 if(obj.additionAllowed=='no'){
						 alert('Cant add any more secondary contacts,limit of 1 secondary reord is reached');						 
					 }else{					 
					 	$("#addsecondarycontact_dialog").dialog({ modal: true, width: 875, title: 'Add Secondary Contact', autoOpen:false, height: 500, dialogClass: 'no-close'});
					 	$("#addsecondarycontact_dialog").dialog('open');	
					 }
			  };//end of if 
			};//end of function
			req.open("GET","getusercontactcollectionsize?selectedId="+id_value+"&t="+Math.random(),true);		
			req.send();			
		}
	});	
});
function patientIdRadioClicked(){		
	var request = createRequest();
	//alert('Inside patientIdRadioClicked');
	$('#secondarycontacts').empty();
	var ids = document.getElementsByName('patientIdRadio');
	var id_value="";
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;
	    	//alert(id_value);
	    };
	}
	var indata = id_value.split("|");
	id_value = indata[0];	
	request.onreadystatechange=function(){		
		  if (request.readyState==4 && request.status==200){			 
			 var temp = request.responseText ;		 		 		 
			 obj = JSON.parse(temp);
			 //alert('obj = '+obj);
			 
			 document.getElementById("td_enrollmentMode").innerHTML = obj[0].enrollmentMode;
			 document.getElementById("td_referredBy").innerHTML = obj[0].enrollmentReferredBy;
			 document.getElementById("td_patient_note").innerHTML = obj[0].note;			 
			 document.getElementById("td_fullName").innerHTML = obj[2].firstName+' '+obj[2].middleName+' '+obj[2].lastName;			 
			 document.getElementById("td_primaryInsured").innerHTML = obj[0].primaryInsured;			 
			 document.getElementById("td_insuranceExpirationDate").innerHTML = obj[0].strInsuranceExpirationDate;			 
			 document.getElementById("td_lastUpdatedBy").innerHTML = obj[0].lastUpdatedBy;
			 document.getElementById("td_lastUpdatedDate").innerHTML = obj[0].lastUpdatedDate;			 
			 document.getElementById("td_sumAssured").innerHTML = obj[0].sumAssured;
			 
			 document.getElementById("td_patient_phoneNumber1").innerHTML= obj[1].phoneNumber1;
			 document.getElementById("td_patient_phoneNumber2").innerHTML= obj[1].phoneNumber2;		
			 document.getElementById("td_patient_emailAddress").innerHTML= obj[1].emailAddress;
			 var patient_pc_address = obj[1].flatNumber +'   ' + obj[1].locality + '   ' + obj[1].street + '   ' + obj[1].city + '<br> ' + obj[1].state + '   ' + obj[1].pincode  +'<br>  ' + obj[1].country;
			 document.getElementById("td_patient_address").innerHTML= patient_pc_address;			  
			
			 document.getElementById("td_sec1_phoneNumber1").innerHTML=obj[3].phoneNumber1;
			 document.getElementById("td_sec1_phoneNumber2").innerHTML=obj[3].phoneNumber2;
			 document.getElementById("td_sec1_emailAddress").innerHTML=obj[3].emailAddress;
			 var patient_sec_address = obj[3].flatNumber +'   ' + obj[3].locality + '   ' + obj[3].street + '   ' + obj[3].city + '<br> ' + obj[3].state + '   ' + obj[3].pincode  +'<br>  ' + obj[3].country;
			 document.getElementById("td_sec1_address").innerHTML= patient_sec_address;			 				 
			
			};//end of if 
		  };//end of function			
		  request.open("GET","populatePatientDetails?selectedId="+id_value+"&t="+Math.random(),true);		 
		  request.send();	
};
$(document).ready(function(){
	$("#updatesecondarycontact_dialog").hide();
});

function updateSecondaryContactButtonClicked(){	
	var ids = document.getElementsByName('patientIdRadio');
	var id_value="";
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;
	    	//alert(id_value);
	    };
	}
	var indata = id_value.split("|");
	id_value = indata[0];
	req = createRequest();
	//alert('req ='+req);
	
	req.onreadystatechange=function(){			
		  if (req.readyState==4 && req.status==200){
			 var temp = req.responseText ;
			 obj = JSON.parse(temp);
			 //alert('obj = '+obj);
			 //alert('temp ='+temp);
			 if(obj == 'No Secondary Contact Found'){
				 alert('Secondary Contact is NOT available!');
				 return false;
			 }
			 contactId = obj[0].id;
			 document.getElementById("update_sec_phoneNumber1").value = obj[0].phoneNumber1;					 					 
			 document.getElementById("update_sec_phoneNumber2").value = obj[0].phoneNumber2;
			 document.getElementById("update_sec_flatNumber").value = obj[0].flatNumber;
			 document.getElementById("update_sec_locality").value = obj[0].locality;
			 document.getElementById("update_sec_street").value = obj[0].street;
			 document.getElementById("update_sec_city").value = obj[0].city;
			 document.getElementById("update_sec_pincode").value = obj[0].pincode;
			 document.getElementById("update_sec_state").value = obj[0].state;
			 document.getElementById("update_sec_country").value = obj[0].country;
			 document.getElementById("update_sec_emailAddress").value = obj[0].emailAddress;
			 					
	$("#updatesecondarycontact_dialog").dialog({ modal: true, width: 875, title: 'Update Secondary Contact', autoOpen:false, height: 500, dialogClass: 'no-close'});
	var form_original_data = $("#updatepatientsecondarycontactForm").serialize();
	//alert(form_original_data);
	$("#updatepatientseccontactsavebutton").click(function(){
		if ($("#updatepatientsecondarycontactForm").serialize() != form_original_data) {
			//alert('updating = '+$("#updatepatientsecondarycontactForm").serialize() );
			updatesecondarycontact(contactId);
		}else{							
			return false;
		}		
	});			
    $("#updatesecondarycontact_dialog").dialog('open');
			 
  };//end of if 
};//end of function     
   req.open("GET","displayupdatesecondarycontact?selectedId="+id_value+"&t="+Math.random(),true);
   req.send();	
};
function updatesecondarycontact(id){
	//alert('update save button is clicked');
	//alert('id ='+id);
	$("#updatepatientsecondarycontactForm").validate({
  		rules:{
  			phoneNumber1:"required"  					
  		},
  		messages:{
  			phoneNumber1:"Enter Phone Number 1."  				
  		},
  		errorElement:"div",
		wrapper:"div",
		errorPlacement: function(error, element) {
			error.appendTo( element.parent().next() );
			offset=element.offset();
			error.insertBefore(element);
			error.addClass('message');
			error.css('position','absolute');	
			error.css('margin-left','-470px');
			error.css('margin-top','15px');
		}
	});
	document.getElementById("updatepatientsecondarycontactForm").action="updatepatientsecondarycontact?selectedId="+id;
	
}
$(document).ready(function(){	
	var patientIdRadio = document.getElementsByName('patientIdRadio');
	if(patientIdRadio[0] != null){
		patientIdRadio[0].checked=true;
		patientIdRadioClicked();
	}
});    
$(document).ready(function(){	
	$('#updatepatientseccontactclosebutton').click(function(){
		$("#updatesecondarycontact_dialog").dialog("close");
		return false;	
	});	
});