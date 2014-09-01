$(document).ready(function(){	
	$("#addappointment_dialog").hide();		
	$('#addbutton').click(function(){
		doctorUserNames = document.getElementById("app_add_doctorUserNames");
		for(var i=0; i<doctorUserNames.options.length; i++){		
			doctorUserNames.options[i].value = null;
			doctorUserNames.options[i].text = null;
			doctorUserNames.options.length = 0;
		}
		patientUserNames = document.getElementById("app_add_patientUserNames");
		for(var i=0; i<patientUserNames.options.length; i++){		
			patientUserNames.options[i].value = null;
			patientUserNames.options[i].text = null;
			patientUserNames.options.length = 0;
		}
		labNames = document.getElementById("app_add_labNames");
		for(var i=0; i<labNames.options.length; i++){		
			labNames.options[i].value = null;
			labNames.options[i].text = null;
			labNames.options.length = 0;
		}
		req = createRequest();	
		req.onreadystatechange=function(){					
			  if (req.readyState==4 && req.status==200){					 
				 var temp = req.responseText;
				 //alert('temp= '+temp);
				 obj = JSON.parse(temp);	
				 //alert('obj '+obj.length);
				 doctorUserNames.options[0] = new Option('','');
				 for(var i=0; i<obj[0].length; i++){						
					 doctorUserNames.options[doctorUserNames.options.length] = new Option(obj[0][i],obj[3][i]);							 
				 }
				 labNames.options[0] = new Option('','');
				 for(var i=0; i<obj[1].length; i++){						
					 labNames.options[labNames.options.length] = new Option(obj[1][i],obj[1][i]);							 
				 }	
				 patientUserNames.options[0] = new Option('','');
				 for(var i=0; i<obj[2].length; i++){						
					 patientUserNames.options[patientUserNames.options.length] = new Option(obj[2][i],obj[4][i]);							 
				 }	
				 $("#addappointment_dialog").dialog({ modal: true, width: 875, title: 'Add Appointment', autoOpen:false, height: 600,dialogClass: 'no-close'});
				 $("#addappointment_dialog").dialog('open');					 
		  };//end of if 
		};//end of function
		req.open("GET","displayaddappointment?t="+Math.random(),true);
		req.send();
	});	 
});
$(document).ready(function(){	
	$('#addappointmentclosebutton').click(function(){
		$("#addappointment_dialog").dialog("close");
		return false;	
	});	
});
$(document).ready(function() { 
    $("#app_add_time").datetimepicker({ 
        dateFormat: "dd/mm/yy" , 
        controlType: 'select',
    	timeFormat: 'hh:mm tt',
        onSelect: function() { 
	      $(this).focus(); 
	   }         	
    });
});
$(document).ready(function() {
	$('#addappointmentsavebutton').click(function(){
		$("#addappointmentForm").validate({				
	  		rules:{
	  			status:{
	  				required:true
	  			},	  			
	  			app_add_patientUserNames:{
	  				required:true
	  			},
	  			name:{
	  				required:true
	  			}
	  		},
	  		messages:{
	  			status:"Select Appointment Status.",	  			
	  			app_add_patientUserNames:"Select Patient Name.",
	  			name:"Enter Appointment Name"	
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
		document.getElementById("app_add_hdocUserName").value = document.getElementById("app_add_doctorUserNames").value;		
		document.getElementById("app_add_hpatientUserName").value = document.getElementById("app_add_patientUserNames").value;				
		document.getElementById("app_add_hlabName").value = document.getElementById("app_add_labNames").value;		
	});
});
function appointmentIdRadioClicked(){		
	var request = createRequest();
	//alert('Inside appointmentIdRadioClicked');
	
	var ids = document.getElementsByName('appointmentIdRadio');
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
			 //alert(id_value);
			 document.getElementById("td_app_docName").innerHTML= obj[1][1];
			 document.getElementById("td_app_patientName").innerHTML= obj[1][0];
			 document.getElementById("td_app_labName").innerHTML= obj[1][2];
			 document.getElementById("td_app_referredTo").innerHTML= obj[0].referredTo;			 				 
			 document.getElementById("td_app_comments").innerHTML= obj[0].comments;
			 document.getElementById("td_app_details").innerHTML=obj[0].details;
			 document.getElementById("td_app_time").innerHTML=obj[0].strAppointmentDateTime;
			 document.getElementById("td_app_status").innerHTML=obj[0].status;
			 document.getElementById("td_app_name").innerHTML=obj[0].name;
			 document.getElementById("td_app_lastUpdatedBy").innerHTML=obj[0].lastUpdatedBy;
			 document.getElementById("td_app_lastUpdatedDate").innerHTML=obj[0].lastUpdatedDate;
			 $( '#td_app_id' ).val(id_value);
			 //alert(obj[7]);
			 var reportCount = obj[7];
			 if (reportCount < 0) {
				$( '#reportLink' ).attr('disabled','disabled');
			}
			 document.getElementById("reports").innerHTML=obj[7];
			 var reportURL = document.getElementById("reportLink").getAttribute('href');
			 //alert(reportURL+id_value);
			 document.getElementById("reportLink").setAttribute('href',reportURL+id_value);
		  };//end of if 
		  };//end of function			
		  request.open("GET","populateAppointmentDetails?selectedId="+id_value+"&t="+Math.random(),true);		 
		  request.send();	
};
$(document).ready(function(){	
	var appointmentIdRadio = document.getElementsByName('appointmentIdRadio');
	if(appointmentIdRadio[0] != null){
		appointmentIdRadio[0].checked=true;
		appointmentIdRadioClicked();
	}
});  
function deleteappointment(){
	//alert('delete button is clicked');		
	var ids = document.getElementsByName('appointmentIdRadio');
	var id_value='';
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;		    	
	    }
	}
	var indata = id_value.split("|");
	id_value = indata[0];
	
	if(id_value ==''){
		alert('Please Select The Appointment To Be Deleted By Selecting The Radio Button');
	}else{
		var answer = confirm('Do you want to delete Appointment?');
		if(!answer){				
			document.getElementById("deleteappointmentForm").action="displayappointmentadmin";
		}else{
			document.getElementById("deleteappointmentForm").action="deleteappointment?selectedId="+id_value;				
		}
	}		
}
$(document).ready(function(){
	$('#updateappointment_dialog').hide();
	$('#updatebutton').click(function(){		
		//alert('update button is clicked');
		var ids = document.getElementsByName('appointmentIdRadio');
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
		doctorUserNames = document.getElementById("app_update_doctorUserNames");
		for(var i=0; i<doctorUserNames.options.length; i++){		
			doctorUserNames.options[i].value = null;
			doctorUserNames.options[i].text = null;
			doctorUserNames.options.length = 0;
		}
		patientUserNames = document.getElementById("app_update_patientUserNames");
		for(var i=0; i<patientUserNames.options.length; i++){		
			patientUserNames.options[i].value = null;
			patientUserNames.options[i].text = null;
			patientUserNames.options.length = 0;
		}
		labNames = document.getElementById("app_update_labNames");
		for(var i=0; i<labNames.options.length; i++){		
			labNames.options[i].value = null;
			labNames.options[i].text = null;
			labNames.options.length = 0;
		}
		 status = document.getElementById("app_update_status");
		if(id_value ==''){
			alert('Please select the Appointment to be updated by selecting the Radio Button');
		}else{	
			req = createRequest();
			//alert('update='+id_value);
			//alert('request ='+req);
			req.onreadystatechange=function(){					
				  if (req.readyState==4 && req.status==200){					
					 var temp = req.responseText ;		 		 		 
					 obj = JSON.parse(temp);
					 
					 document.getElementById("app_update_doctorUserNames").innerHTML= obj[1][1];
					 document.getElementById("app_update_patientUserNames").innerHTML= obj[1][0];
					 document.getElementById("app_update_labNames").innerHTML= obj[1][2];
					 document.getElementById("app_update_referredTo").value= obj[0].referredTo;	
					 document.getElementById("app_update_name").value= obj[0].name;
					 document.getElementById("app_update_comments").innerHTML= obj[0].comments;
					 document.getElementById("app_update_details").innerHTML=obj[0].details;
					 document.getElementById("app_update_time").value=obj[0].strAppointmentDateTime;
					 document.getElementById("app_update_status").value=obj[0].status;					
					
					 doctorUserNames.options[0] = new Option('','');
					 for(var i=0; i<obj[2].length; i++){						
						 doctorUserNames.options[doctorUserNames.options.length] = new Option(obj[2][i],obj[5][i]); 					
						 if(obj[2][i] == obj[1][1]){					     
							 doctorUserNames.options[i+1].selected = true;
						 }
					}	
					 patientUserNames.options[0] = new Option('','');
					 for(var i=0; i<obj[3].length; i++){						
						 patientUserNames.options[patientUserNames.options.length] = new Option(obj[3][i],obj[6][i]);
						 if(obj[3][i] == obj[1][0]){						     
							 patientUserNames.options[i+1].selected = true;
						}
					 }	
					 labNames.options[0] = new Option('','');
					 for(var i=0; i<obj[4].length; i++){						
						 labNames.options[labNames.options.length] = new Option(obj[4][i],obj[4][i]);
						 if(obj[4][i] == obj[1][2]){						    
							 labNames.options[i+1].selected = true;
						}
					 }	
					
					 					
			$("#updateappointment_dialog").dialog({ modal: true, width: 875, title: 'Update Appointment', autoOpen:false, height: 600, dialogClass: 'no-close'});
			var form_original_data = $("#updateappointmentForm").serialize();
			//alert(form_original_data);
			$("#updateappointmentsavebutton").click(function(){
				if ($("#updateappointmentForm").serialize() != form_original_data) {					
					updateappointment();
				}else{							
					return false;
				}		
			});			
		    $("#updateappointment_dialog").dialog('open');
					 
		  };//end of if 
		};//end of function	
				 
		   req.open("GET","populateAppointmentDetails?selectedId="+id_value+"&t="+Math.random(),true);		
		   req.send();	
			
		};
	});
});
$(document).ready(function(){	
	$('#updateappointmentclosebutton').click(function(){
		$("#updateappointment_dialog").dialog("close");
		return false;	
	});	
});
$(document).ready(function() { 
    $("#app_update_time").datetimepicker({ 
        dateFormat: "dd/mm/yy" , 
        controlType: 'select',
    	timeFormat: 'hh:mm tt',
        onSelect: function() { 
	      $(this).focus(); 
	   }         	
    });
});
function updateappointment(){
	//alert('update save button is clicked');		
	var ids = document.getElementsByName('appointmentIdRadio');
	var id_value='';
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;	    	
	    }
	}
	var indata = id_value.split("|");
	id_value = indata[0];	
	//alert(id_value);
	$("#updateappointmentForm").validate({				
  		rules:{
  			status:{
  				required:true
  			},	  			
  			app_update_patientUserNames:{
  				required:true
  			},
  			name:{
  				required:true
  			}
  		},
  		messages:{
  			status:"Select Appointment Status.",	  			
  			app_update_patientUserNames:"Select Patient Name.",
  			name:"Enter Appointment Name"	
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
	document.getElementById("app_update_hdocUserName").value = document.getElementById("app_update_doctorUserNames").value;	
	document.getElementById("app_update_hpatientUserName").value = document.getElementById("app_update_patientUserNames").value;
	document.getElementById("app_update_hlabName").value = document.getElementById("app_update_labNames").value;	
	
	document.getElementById("updateappointmentForm").action="updateappointment?selectedId="+id_value;
}
$(document).ready(function(){	
	$("#addreportinfo_dialog").hide();		
	$('#addReportInfobutton').click(function(){
		reportTypes = document.getElementById("reportinfo_add_reportTypeNames");
		for(var i=0; i<reportTypes.options.length; i++){		
			reportTypes.options[i].value = null;
			reportTypes.options[i].text = null;
			reportTypes.options.length = 0;
		}		
		req = createRequest();	
		req.onreadystatechange=function(){					
			  if (req.readyState==4 && req.status==200){					 
				 var temp = req.responseText;
				 //alert('temp= '+temp);
				 obj = JSON.parse(temp);	
				 //alert('obj '+obj.length);
				 reportTypes.options[0] = new Option('','');
				 for(var i=0; i<obj[0].length; i++){						
					 reportTypes.options[reportTypes.options.length] = new Option(obj[0][i],obj[1][i]);							 
				 }				 
				 $("#addreportinfo_dialog").dialog({ modal: true, width: 650, title: 'Add Report Info', autoOpen:false, height: 350,dialogClass: 'no-close'});
				 $("#addreportinfo_dialog").dialog('open');					 
		  };//end of if 
		};//end of function
		req.open("GET","displayaddreportinfo?t="+Math.random(),true);
		req.send();
	});	 
});
$(document).ready(function(){	
	$('#addreportinfoclosebutton').click(function(){
		$("#addreportinfo_dialog").dialog("close");
		return false;	
	});	
});
$(document).ready(function() {
	$('#addreportinfosavebutton').click(function(){
		var ids = document.getElementsByName('appointmentIdRadio');
		var id_value='';
		for(var i = 0; i < ids.length; i++){
		    if(ids[i].checked){
		    	id_value = ids[i].value;	    	
		    }
		}
		var indata = id_value.split("|");
		id_value = indata[0];	
		//alert(id_value);
		$("#addreportinfoForm").validate({				
	  		rules:{	  						
	  			reportinfo_add_reportTypeNames:{
	  				required:true
	  			},
	  			name:{
	  				required:true
	  			}
	  		},
	  		messages:{	  				  			
	  			reportinfo_add_reportTypeNames:"Select Report Type Name.",
	  			name:"Enter Report Type Name"	
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
		document.getElementById("reportinfo_add_hreportTypeId").value = document.getElementById("reportinfo_add_reportTypeNames").value;	
		document.getElementById("reportinfo_add_hname").value = document.getElementById("reportinfo_add_name").value;
		document.getElementById("addreportinfoForm").action="addreportinfo?selectedId="+id_value;
				
	});
});
function checkReportInfoNameAvailability() { 
	var xmlhttp = createRequest();	
	xmlhttp.onreadystatechange=function()
	  {
	  //alert(xmlhttp.readyState);
	  //alert(xmlhttp.status);	  
	  if (xmlhttp.readyState==4 && xmlhttp.status==200){	    
	    document.getElementById("reportInfoNameAvailabilityMsg").innerHTML=xmlhttp.responseText;
	    //alert(xmlhttp.responseText);
	    if(xmlhttp.responseText == 'Report Info name is not available, please use different Report Info name.'){	    	
	    	document.getElementById('reportinfo_add_name').select();	    
	    }
	   }
	  };	
	xmlhttp.open("GET","checkReportInfoNameAvailability?reportInfoName="+document.getElementById("reportinfo_add_name").value+"&t="+Math.random(),true);
	
	xmlhttp.send();
};