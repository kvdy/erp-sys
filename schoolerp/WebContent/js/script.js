jQuery.extend( jQuery.fn.dataTableExt.oSort, {
	"date-uk-pre": function ( a ) {
	    var ukDatea = a.split('/');
	    return (ukDatea[2] + ukDatea[1] + ukDatea[0]) * 1;
	},

	"date-uk-asc": function ( a, b ) {
	    return ((a < b) ? -1 : ((a > b) ? 1 : 0));
	},

	"date-uk-desc": function ( a, b ) {
	    return ((a < b) ? 1 : ((a > b) ? -1 : 0));
	}
	} 
);
jQuery.extend(jQuery.fn.dataTableExt.oSort, {
	"date-euro-pre": function ( a ) {  
		if ($.trim(a) != '') {
			var frDatea = $.trim(a).split(' ');
			var frTimea = frDatea[1].split(':');
			var frDatea2 = frDatea[0].split('/');
			var x = (frDatea2[2] + frDatea2[1] + frDatea2[0] + frTimea[0] + frTimea[1] + frTimea[2]) * 1; 
		} else {
			var x = 10000000000000; // = l'an 1000 ...
		}
	   return x;
	},
	"date-euro-asc": function ( a, b ) {
		return a - b;     
	},
	"date-euro-desc": function ( a, b ) {
		return b - a;     
	} 
} );

$(document).ready(function(){
	$('#loginbutton').click(function(){
		login_cleanupMessages();
		$("#loginform").validate({
	  		rules:{
	  			userName:"required",	  			
	  			password:"required",
	  		},
	  		messages:{
	  			userName:"Enter username.",	  			
	  			password:"Enter password.",
	  		},
	  		//errorLabelContainer: "#notification",
			//wrapper: "li",
	  		errorElement:"div",
			wrapper:"div",
			errorPlacement: function(error, element) {
				error.appendTo( element.parent().next() );
				offset=element.offset();
				error.insertBefore(element);
				error.addClass('message');
				error.css('position','absolute');	
				error.css('margin-left','-300px');
				error.css('margin-top','-30px');
			}
		});
	});
});

$(document).ready(function(){
	
    //custom validation rule - text only
	$.validator.addMethod("lettersonly", function(value, element) 
			{
			return this.optional(element) || /^[a-z," "]+$/i.test(value);
			}, "Letters and spaces only please"); 
//    $.validator.addMethod("textOnly", 
//                          function(value, element) {
//                              return !/[a-z]*[A-Z]/.test(value);
//                          }, 
//                          "Alpha Characters Only."
//    );

	$('#searchpatientbutton').click(function(){
		$("#searchpatientform").validate({
	  		rules:{
	  			fname:{
	                 required: true,
	                 lettersonly: true
	             },	  			
	  			lname:{
	                 required: true,
	                 lettersonly: true
	             },
	  		},
	  		messages:{
	  			fname:{
	  				required: "First name is required.",
	  				lettersonly: "Alpha characters only."
	  			},	  			
	  			lname:{
	  				required: "Last name is required.",
	  				lettersonly: "Alpha characters only."	
	  			},
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
				error.css('margin-top','17px');
			}
	  		
		});
	});
});

$(document).ready(function($){	
	$('#thetable').dataTable( {			
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"iDisplayLength" : 10
	} );
});
$(document).ready(function($){	
	$('#thestable').dataTable( {			
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"iDisplayLength" : 10
	} );
});

$(document).ready(function(){
	$('#changepasswordsavebutton').click(function(){	
		$("#changepasswordForm").validate({			
	  		rules:{
	  			oldPassword:"required",
	  			password:{
	  				required:true,	  	
	  				minlength:8	
	  			},
	  			confirmpassword:{
	  				required:true,
	  				equalTo:"#password"
	  			}
	  		},
	  		messages:{
	  			oldPassword:"Enter old password.",
	  			password:{
	  				required:"Enter new password.",	
	  				minlength:"Minimum length required is 8."	  				
	  			},	  			
	  			confirmpassword:{
	  				required:"Confirm new password.",
	  				equalTo:"Please enter same password again."
	  			},	  			
	  		},
	  		errorElement:"div",
			wrapper:"div",
			errorPlacement: function(error, element) {
				error.appendTo( element.parent().next() );
				offset=element.offset();
				error.insertBefore(element);
				error.addClass('message');
				error.css('position','absolute');	
				error.css('margin-left','-300px');
				error.css('margin-top','-30px');
			}
		});
	});	
});

function createRequest(){
	var xmlhttp;
	if (window.XMLHttpRequest)
	  {// code for IE7+, Firefox, Chrome, Opera, Safari
	  xmlhttp=new XMLHttpRequest();
	}else{// code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}	
	return xmlhttp;
}
function imposeMaxLength(Object, MaxLen){
	return (Object.value.length <= MaxLen);
} 
function cleanupMessages(){
	if(document.getElementById("changePassword_successMessage").innerHTML != ""){
		document.getElementById("changePassword_successMessage").innerHTML = "";
	}
	if(document.getElementById("changePassword_failureMessage").innerHTML != ""){
		document.getElementById("changePassword_failureMessage").innerHTML = "";
	}
};
function login_cleanupMessages(){
	document.getElementById("login_successMessage").innerHTML = "&nbsp;";
	document.getElementById("login_failureMessage").innerHTML = "&nbsp;";
};
