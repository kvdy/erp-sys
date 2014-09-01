function checkUserNameAvailability() { 
	var xmlhttp = createRequest();	
	xmlhttp.onreadystatechange=function()
	  {
	  //alert(xmlhttp.readyState);
	  //alert(xmlhttp.status);	  
	  if (xmlhttp.readyState==4 && xmlhttp.status==200){	    
	    document.getElementById("userNameAvailabilityMsg").innerHTML=xmlhttp.responseText;
	    //alert(xmlhttp.responseText);
	    if(xmlhttp.responseText == 'User name is not available, please use different User name.'){	    	
	    	document.getElementById('add_userName').select();	    
	    }
	   }
	  };	
	xmlhttp.open("GET","checkUserNameAvailability?userName="+document.getElementById("add_userName").value+"&t="+Math.random(),true);
	
	xmlhttp.send();
};

function userIdRadioClicked(){		
	var request = createRequest();
	//alert('Inside useruseruserIdRadioClicked');
	$('#groups').empty();
	var ids = document.getElementsByName('userIdRadio');
	var id_value="";
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;
	    	//alert(id_value);
	    }
	}
	var indata = id_value.split("|");
	id_value = indata[0];	
	request.onreadystatechange=function(){		
		  if (request.readyState==4 && request.status==200){			 
			 var temp = request.responseText ;		 		 		 
			 obj = JSON.parse(temp);
			 //alert('obj = '+obj);
			 document.getElementById("td_userName").innerHTML = obj[0].userName;
			 document.getElementById("td_prefix").innerHTML = obj[0].prefix;
			 document.getElementById("td_firstname").innerHTML = obj[0].firstName;
			 document.getElementById("td_middlename").innerHTML = obj[0].middleName;
			 document.getElementById("td_lastname").innerHTML = obj[0].lastName;
			 document.getElementById("td_userstatus").innerHTML = obj[0].userStatus;
			 document.getElementById("td_usertype").innerHTML = obj[0].userType;			 
			 document.getElementById("td_note").innerHTML = obj[0].note;
			 document.getElementById("td_lastlogindate").innerHTML = obj[0].strLastLoginDate;
			 document.getElementById("td_passwordexpireson").innerHTML = obj[0].passwordExpirationDays;
			 var html = '';
			 for (var key=0, size=obj[1].length; key<size;key++) {
			   html += '<tr><td>'
			   			  + '</td><td class="tdClass">'	
			              + obj[1][key].name
			              + '</td><td class="tdClass">'
			              + obj[1][key].note;			              	             
			 }
			 $('#groups').append(html);
			};//end of if 
		  };//end of function			
		  request.open("GET","populateUserDetails?selectedId="+id_value+"&t="+Math.random(),true);		
		  request.send();	
};

$(document).ready(function(){
	$('#resetpassword_dialog').hide();
	$('#resetbutton').click(function(){		
		//alert('reset button is clicked');
		var ids = document.getElementsByName('userIdRadio');
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
			alert('Please select the user, for whom password is to be reset, by selecting the Radio Button');
		}else{	
			req = createRequest();
			//alert('resetpassword='+id_value);
			//alert('request ='+req);
			req.onreadystatechange=function(){					
				  if (req.readyState==4 && req.status==200){					
					 var temp = req.responseText ;		 		 		 
					 obj = JSON.parse(temp);
					 //alert('obj = '+obj);
					 //alert('temp ='+temp);
					 document.getElementById("reset_label_userName").innerHTML = obj[0].userName;
					 					
			$("#resetpassword_dialog").dialog({ modal: true, width: 600, title: 'Reset Password', autoOpen:false, height: 325, dialogClass: 'no-close'});
			var form_original_data = $("#resetpasswordFormAdmin").serialize();
			//alert(form_original_data);
			$("#resetpasswordsavebuttonAdmin").click(function(){
				if ($("#resetpasswordFormAdmin").serialize() != form_original_data) {					
					resetpassword();
				}else{							
					return false;
				}		
			});			
		    $("#resetpassword_dialog").dialog('open');			
		  };//end of if 
		};//end of function	
						 
		req.open("GET","populateUserDetails?selectedId="+id_value+"&t="+Math.random(),true);		
		req.send();	
			
		};
	});	
});	

$(document).ready(function(){
	$('#updateuser_dialog').hide();
	$('#updatebutton').click(function(){		
		//alert('update button is clicked');
		var ids = document.getElementsByName('userIdRadio');
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
			alert('Please select the user to be updated by selecting the Radio Button');
		}else{	
			req = createRequest();
			//alert('update='+id_value);
			//alert('request ='+req);
			req.onreadystatechange=function(){					
				  if (req.readyState==4 && req.status==200){					
					 var temp = req.responseText ;		 		 		 
					 obj = JSON.parse(temp);
					// alert('obj = '+obj);
					// alert('temp ='+temp);
					 document.getElementById("update_label_userName").innerHTML = obj[0].userName;					 
					 document.getElementById("update_firstname").value = obj[0].firstName;
					 document.getElementById("update_middlename").value = obj[0].middleName;
					 document.getElementById("update_lastname").value = obj[0].lastName;
					 document.getElementById("update_prefix").value = obj[0].prefix;
					 document.getElementById("update_userstatus").value = obj[0].userStatus;
					 document.getElementById("update_usertype").value = obj[0].userType;					 
					 document.getElementById("update_note").value = obj[0].note;	
					 					
			$("#updateuser_dialog").dialog({ modal: true, width: 875, title: 'Update User', autoOpen:false, height: 500, dialogClass: 'no-close'});
			var form_original_data = $("#updateuserForm").serialize();
			//alert(form_original_data);
			$("#updateusersavebutton").click(function(){
				if ($("#updateuserForm").serialize() != form_original_data) {
					//alert('updating = '+$("#updateuserForm").serialize() );
					updateuser();
				}else{							
					return false;
				}		
			});			
		    $("#updateuser_dialog").dialog('open');
			//alert('hiddenuserName = '+document.getElementById("update_hiddenuserName").value);		 
		  };//end of if 
		};//end of function	
				 
		   req.open("POST","populateUserDetails?selectedId="+id_value+"&t="+Math.random(),true);		
		   req.send();	
			
		};
	});
});
$(document).ready(function(){
	var userIdRadio = document.getElementsByName('userIdRadio');
	if(userIdRadio[0] != null){
		userIdRadio[0].checked=true;
		userIdRadioClicked();
	}
});
function deleteuser(){
		//alert('delete button is clicked');		
		var ids = document.getElementsByName('userIdRadio');
		var id_value='';
		for(var i = 0; i < ids.length; i++){
		    if(ids[i].checked){
		    	id_value = ids[i].value;		    	
		    }
		}
		var userInSession = '${session.user.userName}';
		//alert('userInSession='+userInSession);
		//alert(id_value);
		var indata = id_value.split("|");
		id_value = indata[0];
		var userName = indata[1];
		if(id_value ==''){
			alert('Please Select The User To Be Deleted By Selecting The Radio Button');
		}else if(userName == 'admin'){
			alert('You are not allowed to delete admin user.');			
		}else if(userName == userInSession){
			alert('You can not delete yourself from the system.');
		}else{
			var answer = confirm('Do you want to delete User?');
			if(!answer){				
				//return false;	
				document.getElementById("deleteuserForm").action="displayuseradmin";
			}else{
				document.getElementById("deleteuserForm").action="deleteuser?selectedId="+id_value;				
			}
		}		
}

function updateuser(){
	//alert('update save button is clicked');		
	var ids = document.getElementsByName('userIdRadio');
	var id_value='';
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;	    	
	    }
	}
	var indata = id_value.split("|");
	id_value = indata[0];	
	//alert(id_value);
	document.getElementById("updateuserForm").action="updateuser?selectedId="+id_value;
}

function resetpassword(){
	//alert('Reset Password save button is clicked');		
	var ids = document.getElementsByName('userIdRadio');
	var id_value='';
	for(var i = 0; i < ids.length; i++){
	    if(ids[i].checked){
	    	id_value = ids[i].value;	    	
	    }
	}
	var indata = id_value.split("|");
	id_value = indata[0];	
	//alert(id_value);
	document.getElementById("resetpasswordFormAdmin").action="resetpassword?selectedId="+id_value;
}
$(document).ready(function(){
	$("#adduser_dialog").hide();
	$('#addbutton').click(function() {
	    $("#adduser_dialog").dialog({ modal: true, width: 875, title: 'Add User', autoOpen:false, height: 500, dialogClass: 'no-close'});
	    $("#adduser_dialog").dialog('open');	    
	    return false;
	});	
});
$(document).ready(function(){
	$('#resetpasswordsavebuttonAdmin').click(function(){	
		$("#resetpasswordFormAdmin").validate({			
	  		rules:{
	  			password:"required",	  			
	  			/*confirmpassword:{
	  				equalTo:"#password"
	  			},*/
				passwordExpirationDays:{
					required:true,
	  				number:true
				}
	  		},
	  		messages:{
	  			password:"Enter new password.",	  			
	  			/*confirmpassword:{
	  				equalTo:"Please enter same password again."
	  			},*/
	  			passwordExpirationDays:{
	  				required:"Please enter password expiration days.",
	  				number:"Please enter only numeric value."	
	  			}
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
				error.css('margin-top','10px');
			}
		});
	});	
});
$(document).ready(function(){
	$('#addusersavebutton').click(function(){		
		$("#adduserForm").validate({
	  		rules:{
	  			firstName:"required",	  			
	  			lastName:"required",
	  			userName:{
	  				required:true,
	  				noPipeAllowed:true
	  			},
	  			password:"required",
				passwordExpirationDays:{
					required:true,
	  				number:true
				}	  			
	  		},
	  		messages:{
	  			firstName:"Enter first name.",	  			
	  			lastName:"Enter last name.",
	  			userName:{
	  				required:"Enter username.",
	  				noPipeAllowed:"Please Do Not Include | (pipe) in The UserName"
	  			},
	  			password:"Enter initial password",
	  			passwordExpirationDays:{
	  				required:"Please enter password expiration days.",
	  				number:"Please enter only numeric value."	
	  			}	  			
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
	});	
});
$.validator.addMethod('noPipeAllowed', function (value) { 
	   return !/[|]/.test(value); 
	});

$(document).ready(function(){	
	$('#adduserclosebutton').click(function(){
		$("#adduser_dialog").dialog("close");
		return false;	
	});
	$('#updateuserclosebutton').click(function(){
		$("#updateuser_dialog").dialog("close");
		return false;	
	});	
	$('#resetpasswordclosebuttonAdmin').click(function(){
		$("#resetpassword_dialog").dialog("close");
		return false;	
	});
});
$(document).ready(function(){
	$('#updateusersavebutton').click(function(){		
		$("#updateuserForm").validate({
	  		rules:{
	  			firstName:"required",	  			
	  			lastName:"required"			
	  		},
	  		messages:{
	  			firstName:"Enter First Name.",	  			
	  			lastName:"Enter Last Name"	  			
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
	});
	
});