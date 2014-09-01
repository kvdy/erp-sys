<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="theme" value="'simple'" scope="page" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.media.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/reportcommentsscript.js"></script>
<script>
	$(document).ready(function() {
		$('a.media').media({
			width : 400,
			height : 400
		});
		$('#middlebox').hide();
		/*
		$('#addcommentbutton').click(function(){
			var comment = document.getElementById("tb_reportComment").value;
			var trimmedComment = comment.trim();
			if(trimmedComment.length==0){
				alert('Please enter comment.');
				return false;
			}
			
		});
		*/
	});
</script>
<div id="bcrumbs">
	<ul id="breadcrumbs-one">
		<li><s:url value="/secure/displaydoctorhome.action"
				var="MyDashboard"></s:url> <s:a href="%{MyDashboard}">
					 Home 
				</s:a></li>
		<li><s:url value="/secure/displaysearchedpatient.action"
				var="SearchedPatient">
				<s:param name="patientId">
					<s:property value="tmpReportInfoVO.appointment.patient.id" />
				</s:param>
			</s:url> <s:a href="%{SearchedPatient}">
				Searched Patient Details
			</s:a></li>
		<li><s:url value="/secure/displaysearchedpatientreports.action"
				var="SearchedPatientReports">
				<s:param name="appointmentId">
					<s:property value="tmpReportInfoVO.appointment.id" />
				</s:param>
			</s:url> <s:a href="%{SearchedPatientReports}">Searched Patient Report Details </s:a></li>
		<li><a href="javascript:void(0)" style="cursor:text" class="current">Report Comments</a></li>
	</ul>
</div>
<div style="height: 500px" id="mainContainer">

	<br>
	
	<div style="margin-left:25px;">
		<label style="font-size:16pt">Report</label>
		<label style="font-size:16pt;margin-left:46%">Comments</label>
	</div>
	<div id="leftbox">
	
		<iframe src="<s:url action='ReportAction?id=%{reportId}'/>"
			width="650" height="445"></iframe>
	</div>
		
		<div id="rightbox">	
		
		<div id="topbox">			
				<table  border="0">
					<tr>&nbsp;&nbsp;</tr>
					<s:iterator value="reportComments">
											
							<tr>
								<td width="2%">&nbsp;</td>
								<td class="td_comment" style="width:40%;color:#1b5790;text-weight:bold">
								<s:property value="commentedBy" />
										<td class="td_comment" style="width:30%" style="color:#777">
										- <s:property value="strCommentedDate" />
										</td>
								<%-- <%
									}
								%> --%>
							</tr>
							<tr>
								<td width="2%"></td>
								<td class="td_comment" colspan="2">	
									<s:property value="comment" />
								</td>
							</tr>
							<tr>
								<td colspan="3" style="color:lightgrey">&nbsp;&nbsp;<hr/>&nbsp;&nbsp;</td>
							</tr>
						
					
				</s:iterator>
				</table>
			
		</div>
		<div id="middlebox"></div>
		<div id="bottombox">
		<label style="padding-left:8px;font-size:12pt">Comment :</label>	
			<br/>			
			<s:textarea name="reportComment.comment" rows="5" cols="113" id="tb_reportComment"
				onkeypress="return imposeMaxLength(this,1999);" value=""
				style="resize:none"></s:textarea>
			<br>
				<br>
				<div style="float:left;margin-left:-175px;margin-bottom:50px;height:40px">
					<s:submit type="button" cssClass="btnClass" tabindex="4" style="height:50%" 
						value="Add Comment" id="addcommentbutton" name="addcommentbutton" onclick="addReportCommentButtonClicked('%{reportId}')"
						title="Add Comment"></s:submit>
				</div>			
		</div>

	</div>
</div>
