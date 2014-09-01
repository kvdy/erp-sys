$(document).ready(function(){	
	$("#updatereportinfo_dialog").hide();
	$('#updatebutton').click(function(){		
		//alert('update button is clicked');
		var ids = document.getElementsByName('reportInfoIdRadio');
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
		reportTypeNames = document.getElementById("reportinfo_update_reportTypeNames");
		for(var i=0; i<reportTypeNames.options.length; i++){		
			reportTypeNames.options[i].value = null;
			reportTypeNames.options[i].text = null;
			reportTypeNames.options.length = 0;
		}		
		if(id_value ==''){
			alert('Please select the Report Info to be updated by selecting the Radio Button');
		}else{	
			req = createRequest();
			//alert('update='+id_value);
			//alert('request ='+req);
			req.onreadystatechange=function(){					
				  if (req.readyState==4 && req.status==200){					
					 var temp = req.responseText ;		 		 		 
					 obj = JSON.parse(temp);
					 
					 document.getElementById("reportinfo_update_reportTypeNames").innerHTML= obj[1];
					 document.getElementById("reportinfo_update_name").value=obj[2].name;
					
					 reportTypeNames.options[0] = new Option('','');
					 for(var i=0; i<obj[0].length; i++){						
						 reportTypeNames.options[reportTypeNames.options.length] = new Option(obj[0][i],obj[1][i]); 					
						 if(obj[0][i] == obj[3]){					     
							 reportTypeNames.options[i+1].selected = true;
						 }
					}	
							
			$("#updatereportinfo_dialog").dialog({ modal: true, width: 650, title: 'Update Report Info', autoOpen:false, height: 350, dialogClass: 'no-close'});
			var form_original_data = $("#updatereportinfoForm").serialize();
			//alert(form_original_data);
			$("#updatereportinfosavebutton").click(function(){
				if ($("#updatereportinfoForm").serialize() != form_original_data) {					
					updatereportinfo();
				}else{							
					return false;
				}		
			});			
		    $("#updatereportinfo_dialog").dialog('open');
					 
		  };//end of if 
		};//end of function	
				 
		   req.open("GET","populateReportInfoDetails?selectedId="+id_value+"&t="+Math.random(),true);		
		   req.send();	
			
		};
	});
});
function reportInfoIdRadioClicked(){		
	var request = createRequest();
	//alert('Inside reportInfoIdRadioClicked');
	
	var ids = document.getElementsByName('reportInfoIdRadio');
	var id_value="";
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;
	    	//alert(id_value);
	    };
	}
	var indata = id_value.split("|");
	id_value = indata[0];	
	for(var i = 0; i < ids.length; i++){
		id_value_1 = ids[i].value.split("|")[0];
		//alert('here ='+id_value_1);
		var row = document.getElementById(id_value_1);
		//alert(row);
		if(ids[i].checked){	 
			//alert('he');
	    	row.style.background="red";
	    	//F5F5DC, B1FB17,CCFB5D , BCE954
	    }else{	    	
	    	if(i%2 === 0){
	    		row.style.background="lightblue";
	    	}else{
	    		row.style.background="#b2d281";
	    	}
	    }		
	}//end of for
	request.onreadystatechange=function(){		
		  if (request.readyState==4 && request.status==200){			 
			 var temp = request.responseText ;		 		 		 
			 obj = JSON.parse(temp);
			 document.getElementById("td_reportinfo_name").innerHTML = obj[2].name;
			 document.getElementById("td_reportinfo_rcn").innerHTML = obj[2].rcn;
//			 alert(obj[2].reportStatus);
			 var status = obj[2].reportStatus;
			 if(status != "Released") {
//				 alert("Inside non released status");
				 $('#viewReportButton').hide();
				 $('#td_viewReportButton').hide();
				 $('#viewReportCommentsButton').hide();
				 $('#td_viewReportCommentsButton').hide();
				 $('#td_reportinfo_reportstatus').attr('colspan',3);
//				 $('#viewReportButton').attr('src','../images/pdf_striked_button.png');
				 document.getElementById("td_reportinfo_reportstatus").innerHTML = "Not Available";
				 document.getElementById("td_reportinfo_createdDate").innerHTML ='';
				 document.getElementById("td_reportinfo_createdBy").innerHTML = '';
				 document.getElementById("td_reportinfo_rcn").innerHTML = '';
				 
			 } else {
//				 alert("Inside released status");
//				 $('#viewReportButton').attr('disabled',false);
//				 $('#viewReportButton').attr('src','../images/pdf_button_32x32.png');
				 document.getElementById("td_reportinfo_reportstatus").innerHTML = "Available";
				 $('#viewReportButton').show();
				 $('#td_viewReportButton').show();
				 $('#viewReportCommentsButton').show();
				 $('#td_viewReportCommentsButton').show();
				 $('#td_reportinfo_reportstatus').attr('colspan',1);
			 
			 if(obj[2].createdDate==undefined){
				 document.getElementById("td_reportinfo_createdDate").innerHTML ='';
			 }else{
				 document.getElementById("td_reportinfo_createdDate").innerHTML = obj[2].createdDate;
			 }
			 document.getElementById("td_reportinfo_createdBy").innerHTML = obj[2].createdBy;
			 if(obj[2].deletedDate==undefined){
				 document.getElementById("td_reportinfo_deletedDate").innerHTML='';
			 }else{
			 	document.getElementById("td_reportinfo_deletedDate").innerHTML = obj[2].deletedDate;
		  	}
			 document.getElementById("td_reportinfo_deletedBy").innerHTML = obj[2].deletedBy;
			 if(obj[2].lastViewedDate==undefined){
				 document.getElementById("td_reportinfo_lastViewedDate").innerHTML='';
			 }else{
				 document.getElementById("td_reportinfo_lastViewedDate").innerHTML = obj[2].lastViewedDate;
			 }
			 document.getElementById("td_reportinfo_lastViewedBy").innerHTML = obj[2].lastViewedBy;
			 }
			// alert("reportVariableForReportInfoPresent ="+reportVariableForReportInfoPresent);
			 if(obj[4]=='yes'){				 
				 //document.getElementById("addbutton").disabled=true;
				// $("#addbutton").hide();
				// alert('Report Variables are already present, cant add anymore');
			 }
			 if(obj[4]=='no'){
				// document.getElementById("updateReportVariablesbutton").disabled=true;
				// $("#updateReportVariablesbutton").hide();
				// alert('Report Variables are not present, cant update');
			 }
			};//end of if 
		  };//end of function			
		  request.open("GET","populateReportDetails?selectedId="+id_value+"&t="+Math.random(),true);		 
		  request.send();	
};
$(document).ready(function(){	
	var reportInfoIdRadio = document.getElementsByName('reportInfoIdRadio');
	if(reportInfoIdRadio[0] != null){
		reportInfoIdRadio[0].checked=true;
		reportInfoIdRadioClicked();
	}
});  
$(document).ready(function(){	
	$('#updatereportinfoclosebutton').click(function(){
		$("#updatereportinfo_dialog").dialog("close");
		return false;	
	});	
});

function updatereportinfo(){
	//alert('update save button is clicked');		
	var ids = document.getElementsByName('reportInfoIdRadio');
	var id_value='';
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;	    	
	    }
	}
	var indata = id_value.split("|");
	id_value = indata[0];	
	//alert(id_value);
	
	document.getElementById("reportinfo_update_hreportTypeId").value = document.getElementById("reportinfo_update_reportTypeNames").value;	
	document.getElementById("updatereportinfoForm").action="updatereportinfo?selectedId="+id_value;
}

function addreportvariables(){
	
	//alert('addreportvariables button is clicked here');
	var ids = document.getElementsByName('reportInfoIdRadio');
	var id_value='';
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;		    	
	    }
	}
	//alert(id_value);
	var indata = id_value.split("|");		
	id_value = indata[0];
	
	if(id_value ==''){
		alert('Please select the Report Info by selecting the Radio Button');
	}else{
		//alert('add='+id_value);
		document.getElementById("addReportVariablesForm").action="displayaddreportvariables?selectedId="+id_value;
	};//end of if 
	
}
function updatereportvariables(){
	//alert('updatereportvariables button is clicked here');
	var ids = document.getElementsByName('reportInfoIdRadio');
	var id_value='';
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;		    	
	    }
	}
	//alert(id_value);
	var indata = id_value.split("|");		
	id_value = indata[0];
	
	if(id_value ==''){
		alert('Please select the Report Info by selecting the Radio Button');
	}else{
		//alert('add='+id_value);
		document.getElementById("updateReportVariablesForm").action="displayupdatereportvariables?selectedId="+id_value;
	};//end of if 
	
}
function viewreport(){
	//alert('viewreport button is clicked');		
	var ids = document.getElementsByName('reportInfoIdRadio');
	var id_value='';
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;	    	
	    }
	}
	var indata = id_value.split("|");
	id_value = indata[0];	
	//alert(id_value);
		
	document.getElementById("viewreportForm").action="displayviewreport?selectedId="+id_value;
}
function viewreportcomments(){
	//alert("viewreportcomments getting called");
	var ids = document.getElementsByName('reportInfoIdRadio');
	var id_value = '';
	for(var i = 0; i < ids.length; i++){
		if(ids[i].checked){
			id_value = ids[i].value;
		}
	}
	var indata = id_value.split("|");
	id_value = indata[0];
	
	document.getElementById("viewreportcommentsForm").action="displayreportcomments?reportId="+id_value;
}
$(document).ready(function(){
	//alert('link= '+document.getElementById("myReports"));
	document.getElementById("myReports").action="";
	return false;
});