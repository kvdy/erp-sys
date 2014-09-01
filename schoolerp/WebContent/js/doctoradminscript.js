$(document).ready(function(){	
	$("#adddoctor_dialog").hide();		
	$('#addbutton').click(function(){
		unassignedDoctorUserNames = document.getElementById("unassignedDoctorUserNames");
		for(var i=0; i<unassignedDoctorUserNames.options.length; i++){		
			unassignedDoctorUserNames.options[i].value = null;
			unassignedDoctorUserNames.options[i].text = null;
			unassignedDoctorUserNames.options.length = 0;
		}			
		req = createRequest();	
		req.onreadystatechange=function(){					
			  if (req.readyState==4 && req.status==200){					 
				 var temp = req.responseText;
				 //alert('temp= '+temp);
				 obj = JSON.parse(temp);	
				 //alert('obj '+obj.length);
				 unassignedDoctorUserNames.options[0] = new Option('','');
				 for(var i=0; i<obj.length; i++){						
					 unassignedDoctorUserNames.options[unassignedDoctorUserNames.options.length] = new Option(obj[i],obj[i]);							 
				 }						 
				 $("#adddoctor_dialog").dialog({ modal: true, width: 875, title: 'Add Doctor', autoOpen:false, height: 600,dialogClass: 'no-close'});
				 $("#adddoctor_dialog").dialog('open');					 
		  };//end of if 
		};//end of function
		req.open("GET","displayadddoctor?t="+Math.random(),true);
		req.send();
	});	 
});

$(document).ready(function() {
	$('#adddoctorsavebutton').click(function(){
		$("#adddoctorForm").validate({				
	  		rules:{
	  			userName:{
	  				required:true
	  			},	  			
	  			doc_phoneNumber1:{
	  				required:true
	  			}
	  		},
	  		messages:{
	  			userName:"Select User Name.",	  			
	  			doc_phoneNumber1:"Enter primary contact phone."	  			
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
		document.getElementById("doc_huserName").value = document.getElementById("unassignedDoctorUserNames").value;
		//alert("userName ="+document.getElementById("huserName").value);
		document.getElementById("doc_hphoneNumber1").value = document.getElementById("doc_phoneNumber1").value;
		document.getElementById("doc_hphoneNumber2").value = document.getElementById("doc_phoneNumber2").value;
		document.getElementById("doc_hflatNumber").value = document.getElementById("doc_flatNumber").value;
		document.getElementById("doc_hlocality").value = document.getElementById("doc_locality").value;
		document.getElementById("doc_hstreet").value = document.getElementById("doc_street").value;
		document.getElementById("doc_hcity").value = document.getElementById("doc_city").value;
		document.getElementById("doc_hpincode").value = document.getElementById("doc_pincode").value;
		document.getElementById("doc_hstate").value = document.getElementById("doc_state").value;
		document.getElementById("doc_hcountry").value = document.getElementById("doc_country").value;
		document.getElementById("doc_hemailAddress").value = document.getElementById("doc_emailAddress").value;		
	});
});
$(document).ready(function(){	
	$('#adddoctorclosebutton').click(function(){
		$("#adddoctor_dialog").dialog("close");
		return false;	
	});
	
});
function deletedoctor(){
	//alert('delete button is clicked');		
	var ids = document.getElementsByName('doctorIdRadio');
	var id_value='';
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;		    	
	    }
	}
	var indata = id_value.split("|");
	id_value = indata[0];
	
	if(id_value ==''){
		alert('Please Select The Doctor To Be Deleted By Selecting The Radio Button');
	}else{
		var answer = confirm('Do you want to delete Doctor?');
		if(!answer){				
			document.getElementById("deletedoctorForm").action="displaydoctoradmin";
		}else{
			document.getElementById("deletedoctorForm").action="deletedoctor?selectedId="+id_value;				
		}
	}		
}
function doctorIdRadioClicked(){		
	var request = createRequest();
	//alert('Inside doctorIdRadioClicked');
	
	var ids = document.getElementsByName('doctorIdRadio');
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
			 
			 document.getElementById("td_doc_userName").innerHTML = obj[2].userName;
			 document.getElementById("td_doc_fullName").innerHTML = obj[2].firstName+' '+obj[2].middleName+' '+obj[2].lastName;
			 document.getElementById("td_doc_degrees").innerHTML = obj[0].degrees;			 
			 document.getElementById("td_doc_specialization").innerHTML = obj[0].specialization;			 
			 document.getElementById("td_doc_webURL").innerHTML = obj[0].webURL;			 
			 document.getElementById("td_doc_note").innerHTML = obj[0].note;			 
			 document.getElementById("td_doc_lastUpdatedBy").innerHTML = obj[0].lastUpdatedBy;
			 document.getElementById("td_doc_lastUpdatedDate").innerHTML = obj[0].lastUpdatedDate;				
			 
			 document.getElementById("td_doc_phoneNumber1").innerHTML= obj[1].phoneNumber1;
			 document.getElementById("td_doc_phoneNumber2").innerHTML= obj[1].phoneNumber2;		
			 document.getElementById("td_doc_emailAddress").innerHTML= obj[1].emailAddress;
			 var doctor_pc_address = obj[1].flatNumber +'   ' + obj[1].locality + '   ' + obj[1].street + '   ' + obj[1].city + '<br> ' + obj[1].state + '   ' + obj[1].pincode  +'<br>  ' + obj[1].country;
			 document.getElementById("td_doc_address").innerHTML= doctor_pc_address;
			  
			
				 document.getElementById("td_doc_sec1_phoneNumber1").innerHTML=obj[3].phoneNumber1;
				 document.getElementById("td_doc_sec1_phoneNumber2").innerHTML=obj[3].phoneNumber2;
				 document.getElementById("td_doc_sec1_emailAddress").innerHTML=obj[3].emailAddress;
				 var doctor_sec_address = obj[3].flatNumber +'   ' + obj[3].locality + '   ' + obj[3].street + '   ' + obj[3].city + '<br> ' + obj[3].state + '   ' + obj[3].pincode  +'<br>  ' + obj[3].country;
				 document.getElementById("td_doc_sec1_address").innerHTML= doctor_sec_address;			 				 
			
			};//end of if 
		  };//end of function			
		  request.open("GET","populateDoctorDetails?selectedId="+id_value+"&t="+Math.random(),true);		 
		  request.send();	
};
$(document).ready(function(){	
	var doctorIdRadio = document.getElementsByName('doctorIdRadio');
	if(doctorIdRadio[0] != null){
		doctorIdRadio[0].checked=true;
		doctorIdRadioClicked();
	}
});   
$(document).ready(function(){
	$('#updatedoctor_dialog').hide();
	$('#updatebutton').click(function(){		
		//alert('update button is clicked');
		var ids = document.getElementsByName('doctorIdRadio');
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
			alert('Please select the doctor to be updated by selecting the Radio Button');
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
					 document.getElementById("update_label_doc_userName").innerHTML = obj[2].userName;					 
					 document.getElementById("update_doc_degrees").value = obj[0].degrees;
					 document.getElementById("update_doc_webURL").value = obj[0].webURL;
					 document.getElementById("update_doc_specialization").value = obj[0].specialization;
					 document.getElementById("update_doc_note").value = obj[0].note;
					 document.getElementById("update_doc_phoneNumber1").value = obj[1].phoneNumber1;					 
					 document.getElementById("update_doc_emailAddress").value = obj[1].emailAddress;					
					 document.getElementById("update_doc_phoneNumber2").value = obj[1].phoneNumber2;
					 document.getElementById("update_doc_flatNumber").value = obj[1].flatNumber;
					 document.getElementById("update_doc_locality").value = obj[1].locality;
					 document.getElementById("update_doc_street").value = obj[1].street;
					 document.getElementById("update_doc_city").value = obj[1].city;
					 document.getElementById("update_doc_pincode").value = obj[1].pincode;
					 document.getElementById("update_doc_state").value = obj[1].state;
					 document.getElementById("update_doc_country").value = obj[1].country;
					 					
			$("#updatedoctor_dialog").dialog({ modal: true, width: 875, title: 'Update Doctor', autoOpen:false, height: 600, dialogClass: 'no-close'});
			var form_original_data = $("#updatedoctorForm").serialize();
			//alert(form_original_data);
			$("#updatedoctorsavebutton").click(function(){
				if ($("#updatedoctorForm").serialize() != form_original_data) {
						
					document.getElementById("update_doc_hphoneNumber1").value = document.getElementById("update_doc_phoneNumber1").value;
					//alert('update_hphoneNumber1 ='+document.getElementById("update_hphoneNumber1").value);
					document.getElementById("update_doc_hphoneNumber2").value = document.getElementById("update_doc_phoneNumber2").value;
					//alert('update_hphoneNumber2 ='+document.getElementById("update_hphoneNumber2").value);
					document.getElementById("update_doc_hflatNumber").value = document.getElementById("update_doc_flatNumber").value;
					//alert('update_hflatNumber ='+document.getElementById("update_hflatNumber").value);
					document.getElementById("update_doc_hlocality").value = document.getElementById("update_doc_locality").value;
					//alert('update_hlocality ='+document.getElementById("update_hlocality").value);
					document.getElementById("update_doc_hstreet").value = document.getElementById("update_doc_street").value;
					//alert('update_hstreet ='+document.getElementById("update_hstreet").value);
					document.getElementById("update_doc_hcity").value = document.getElementById("update_doc_city").value;
					//alert('update_hcity ='+document.getElementById("update_hcity").value);
					document.getElementById("update_doc_hpincode").value = document.getElementById("update_doc_pincode").value;
					//alert('update_hpincode ='+document.getElementById("update_hpincode").value);
					document.getElementById("update_doc_hstate").value = document.getElementById("update_doc_state").value;
					//alert('update_hstate ='+document.getElementById("update_hstate").value);
					document.getElementById("update_doc_hcountry").value = document.getElementById("update_doc_country").value;
					//alert('update_hcountry ='+document.getElementById("update_hcountry").value);
					document.getElementById("update_doc_hemailAddress").value = document.getElementById("update_doc_emailAddress").value;
					//alert('update_hemailAddress ='+document.getElementById("update_hemailAddress").value);
					updatedoctor();
				}else{							
					return false;
				}		
			});			
		    $("#updatedoctor_dialog").dialog('open');
			//alert('hiddenuserName = '+document.getElementById("update_hiddenuserName").value);		 
		  };//end of if 
		};//end of function	
				 
		   req.open("POST","populateDoctorDetails?selectedId="+id_value+"&t="+Math.random(),true);		
		   req.send();	
			
		};
	});
});
$(document).ready(function(){	
	$('#updatedoctorclosebutton').click(function(){
		$("#updatedoctor_dialog").dialog("close");
		return false;	
	});
	
});
function updatedoctor(){
	//alert('update save button is clicked');		
	var ids = document.getElementsByName('doctorIdRadio');
	var id_value='';
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;	    	
	    }
	}
	var indata = id_value.split("|");
	id_value = indata[0];	
	//alert(id_value);
	$("#updatedoctorForm").validate({				
		
  		rules:{    			
  			phoneNumber1:{
  				required:true
  			}
  		},
  		messages:{   			
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
	document.getElementById("updatedoctorForm").action="updatedoctor?selectedId="+id_value;
}

$(document).ready(function(){
	$("#adddoctorsecondarycontact_dialog").hide();
	$('#addsecondarycontactbutton').click(function() {
		var ids = document.getElementsByName('doctorIdRadio');
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
			alert('Please select the doctor by selecting the Radio Button');
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
					 	$("#adddoctorsecondarycontact_dialog").dialog({ modal: true, width: 875, title: 'Add Secondary Contact', autoOpen:false, height: 500, dialogClass: 'no-close'});
					 	$("#adddoctorsecondarycontact_dialog").dialog('open');	
					 }
			  };//end of if 
			};//end of function
			req.open("GET","getusercontactcollectionsize?selectedId="+id_value+"&t="+Math.random(),true);		
			req.send();			
		}
	});	
});
$(document).ready(function(){
	$('#adddoctorseccontactsavebutton').click(function(){	
		var ids = document.getElementsByName('doctorIdRadio');
		var id_value='';
		for(var i = 0; i < ids.length; i++){
		    if(ids[i].checked){
		    	id_value = ids[i].value;		    	
		    }
		}
		//alert(id_value);
		var indata = id_value.split("|");		
		id_value = indata[0];
		$("#adddoctorsecondarycontactForm").validate({
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
		document.getElementById("adddoctorsecondarycontactForm").action="adddoctorsecondarycontact?selectedId="+id_value;
	});	
});
$(document).ready(function(){	
	$('#adddoctorseccontactclosebutton').click(function(){
		$("#adddoctorsecondarycontact_dialog").dialog("close");
		return false;	
	});
	
});
$(document).ready(function(){
	$("#updatedoctorsecondarycontact_dialog").hide();
});

function updateDocSecondaryContactButtonClicked(){	
	var ids = document.getElementsByName('doctorIdRadio');
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
			 document.getElementById("update_doc_sec_phoneNumber1").value = obj[0].phoneNumber1;					 					 
			 document.getElementById("update_doc_sec_phoneNumber2").value = obj[0].phoneNumber2;
			 document.getElementById("update_doc_sec_flatNumber").value = obj[0].flatNumber;
			 document.getElementById("update_doc_sec_locality").value = obj[0].locality;
			 document.getElementById("update_doc_sec_street").value = obj[0].street;
			 document.getElementById("update_doc_sec_city").value = obj[0].city;
			 document.getElementById("update_doc_sec_pincode").value = obj[0].pincode;
			 document.getElementById("update_doc_sec_state").value = obj[0].state;
			 document.getElementById("update_doc_sec_country").value = obj[0].country;
			 document.getElementById("update_doc_sec_emailAddress").value = obj[0].emailAddress;
			 					
	$("#updatedoctorsecondarycontact_dialog").dialog({ modal: true, width: 875, title: 'Update Secondary Contact', autoOpen:false, height: 500, dialogClass: 'no-close'});
	var form_original_data = $("#updatedoctorsecondarycontactForm").serialize();
	//alert(form_original_data);
	$("#updatedoctorseccontactsavebutton").click(function(){
		if ($("#updatedoctorsecondarycontactForm").serialize() != form_original_data) {
			//alert('updating = '+$("#updatedoctorsecondarycontactForm").serialize() );
			updatesecondarycontact(contactId);
		}else{							
			return false;
		}		
	});			
    $("#updatedoctorsecondarycontact_dialog").dialog('open');
			 
  };//end of if 
};//end of function     
   req.open("GET","displayupdatesecondarycontact?selectedId="+id_value+"&t="+Math.random(),true);
   req.send();	
};
function updatesecondarycontact(id){
	//alert('update save button is clicked');
	//alert('id ='+id);
	$("#updatedoctorsecondarycontactForm").validate({
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
	document.getElementById("updatedoctorsecondarycontactForm").action="updatedoctorsecondarycontact?selectedId="+id;	
}
$(document).ready(function(){ 
	 $("#updatedoctorseccontactclosebutton").click(function(){
	  $("#updatedoctorsecondarycontact_dialog").dialog("close");
	  return false; 
	 });
});
