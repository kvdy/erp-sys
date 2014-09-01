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

function addReportCommentButtonClicked(rId){
	//alert("inside addReportCommentButtonClicked");
	var request = createRequest();
	//alert(request);
	id_value=rId;
	$('#middlebox').empty();
	comment=document.getElementById("tb_reportComment").value;
	//alert('id_value = '+id_value);
	//alert('comment ='+comment);
	request.onreadystatechange=function(){		
		  if (request.readyState==4 && request.status==200){			 
			 var temp = request.responseText ;		 		 		 
			 obj = JSON.parse(temp);
			 //alert('obj = '+obj);	
			 var html_comments = '<table width="100%" border="0"><tr>&nbsp;&nbsp;</tr>';
			 for (var reportComment=0, size=obj.length; reportComment<size;reportComment++) {
				 html_comments += '<tr><td width="2%">'
			   			  + '</td><td class="td_comment" width="40%" style="color:#1b5790;text-weight:bold">'	
			              + obj[reportComment].commentedBy			              
			              + '</td><td class="td_comment" width="30%" style="color:#777">- '
			              + obj[reportComment].strCommentedDate
			              +'</td></tr>'
			              + '<tr><td width="2%">'
			              + '</td><td class="td_comment" colspan="2">'
			              + obj[reportComment].comment
			              +'</td></tr><tr><td colspan="3" style="color:lightgrey">&nbsp;&nbsp;<hr/>&nbsp;&nbsp;</td></tr>';			              	             
			 }
			 html_comments +='</table>';
			$('#middlebox').append(html_comments);	
			$('#middlebox').show();
			$('#topbox').hide();
			document.getElementById("tb_reportComment").value='';
			};//end of if 
		  };//end of function	
		  request.open("GET","addreportcomments?reportId="+id_value+"&comment="+comment+"&t="+Math.random(),true);		  
		  request.send();
		  //alert('request sent');
};

