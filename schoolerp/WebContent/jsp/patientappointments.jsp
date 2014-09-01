<%@page import="javax.print.attribute.standard.Severity"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="theme" value="'simple'" scope="page" />
<script type="text/javascript">
	/* $(document).ready(
			function() {
				var appointmentIdRadio = document
						.getElementsByName('appointmentIdRadio');
				if (appointmentIdRadio[0] != null) {
					appointmentIdRadio[0].checked = true;
					appointmentIdRadioClicked();
				}
			}); */
</script>
<!-- 
<script
	src="<%-- <%=request.getContextPath()%> --%>/js/patientappointmentsscript.js"></script> -->
<!-- <div id="bcrumbs">
	<ul id="breadcrumbs-one">
		<li><a href="" class="current">Welcome&nbsp;&nbsp;<s:property
					value="%{#session.user.firstName}" />&nbsp;&nbsp;<s:property
					value="%{#session.user.lastName}" /></a>   
		</li>
		</ul>
	<ul class="right" >you last signed in on <s:property value="%{#session.user.strLastLoginDate}"/></ul>	
	
	
</div> -->

<div style="height: 500px" id="mainContainer">
	<div id="centrewrap">
		<br>	
		<table style="border-top-right-radius:5px;border-top-left-radius:5px;border:1px solid lightgrey">
			<tr>
				<th colspan="5" width="100%" style="background-color:#F0F0F0"><span class="body-font-hdr">Scheduled Appointments</span></th>
			</tr>
		</table>	
		<table id="thestable" width="100%" class="sapmtTable">
			<thead>
				
				<tr>
					<th class="th_searchtable font-12" width="15%" align="center">Lab</th>
					<th class="th_searchtable font-12" width="20%" align="center">Appointment
						Name</th>
					<th class="th_searchtable font-12" width="20%" align="center">Time</th>
					<th class="th_searchtable font-12" width="15%" align="center">Status</th>
					<th class="th_searchtable font-12" width="25%" align="left">Test Preparation Note</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="scheduledAppointments">
					<tr>
						<td class="td_searchtable font-12" width="15%"><s:property
								value="lab.name" /></td>
						<td class="td_searchtable font-12" width="20%"><s:property
								value="name" /></td>
						<td class="td_searchtable font-12" width="20%"><s:property
								value="strAppointmentDateTime" /></td>
						<td class="td_searchtable font-12" width="15%"><s:property
								value="status" /></td>
						<td class="td_searchtable font-12" width="20%"><s:property
								value="testPreparationNotes"/></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<table style="border-top-right-radius:5px;border-top-left-radius:5px;border:1px solid lightgrey">
			<tr>
				<th colspan="5" width="100%" style="background-color:#F0F0F0"><span class="body-font-hdr">Completed Appointments</span></th>
			</tr>
		</table>
		<table id="thetable" width="100%" class="apmtTable">
			<thead>
				
				<tr>
					<th class="th_searchtable font-12" width="20%" align="center">Lab</th>
					<th class="th_searchtable font-12" width="20%" align="center">Appointment
						Name</th>
					<th class="th_searchtable font-12" width="20%" align="center">Time</th>
					<th class="th_searchtable font-12" width="20%" align="center">Status</th>
					<th class="th_searchtable font-12" width="20%" align="center">Ready Reports</th>
					<th class="th_searchtable font-12" width="8%" align="center">View</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="completedAppointments">
					<tr>
						<td class="td_searchtable font-12" width="20%"><s:property
								value="lab.name" /></td>
						<td class="td_searchtable font-12" width="20%"><s:property
								value="name" /></td>
						<td class="td_searchtable font-12" width="20%"><s:property
								value="strAppointmentDateTime" /></td>
						<td class="td_searchtable font-12" width="20%"><s:property
								value="status" /></td>
						<td class="td_searchtable font-12" style="text-align:center" width="20%"><s:property
								value="numberOfReportsReleased" /></td>	
						<td class="td_searchtable font-12" width="8%"><s:url
								id="reportURL" value="/secure/displaypatientreports.action"
								var="GotoReports">
								<s:param name="appointmentId">
									<s:property value="id" />
								</s:param>
							</s:url>
							<s:a id="reportLink" href="%{GotoReports}">
								<img
									src="<%=request.getContextPath()%>/images/career_view_button1.png"
									alt="View Appointment Details" />
							</s:a></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		
	</div>
	<!-- Commented as this section is moved to reports page; can be deleted after review 
	<div id="div2_center">
		<table width="100%" style="border: 1px solid #1b5790;">
			<tr>
				<td class="td_headertable">Appointment Details</td>
			</tr>
		</table>
		<table class="displayDetailsTable">
			<tr>
				<td class="td_left_displayDetailsTable">Appointment Name</td>
				<td id="td_app_name" class="td_right_displayDetailsTable"></td>
			</tr>
			<tr>
				<td class="td_left_displayDetailsTable">Doctor Name</td>
				<td id="td_app_docName" class="td_right_displayDetailsTable"></td>
				<s:hidden id="td_app_patientName"></s:hidden>
			</tr>
			<!-- 			<tr>
				<td class="td_left_displayDetailsTable">Patient Name</td>
				<td id="td_app_patientName" class="td_right_displayDetailsTable"></td>
			</tr>
 
			<tr>
				<td class="td_left_displayDetailsTable">Lab Name</td>
				<td id="td_app_labName" class="td_right_displayDetailsTable"></td>
			</tr>
			<tr>
				<td class="td_left_displayDetailsTable">Referred To</td>
				<td id="td_app_referredTo" class="td_right_displayDetailsTable"></td>
			</tr>
			<tr>
				<td class="td_left_displayDetailsTable">Comments</td>
				<td id="td_app_comments" class="td_right_displayDetailsTable"></td>
			</tr>
			<tr>
				<td class="td_left_displayDetailsTable">Status</td>
				<td id="td_app_status" class="td_right_displayDetailsTable"></td>
			</tr>
			<tr>
				<td class="td_left_displayDetailsTable">Time</td>
				<td id="td_app_time" class="td_right_displayDetailsTable"></td>
			</tr>
			<tr>
				<td class="td_left_displayDetailsTable">Details</td>
				<td id="td_app_details" class="td_right_displayDetailsTable"></td>
			</tr>
			<tr>
				<td class="td_left_displayDetailsTable">Last Updated By</td>
				<td id="td_app_lastUpdatedBy" class="td_right_displayDetailsTable"></td>
			</tr>
			<tr>
				<td class="td_left_displayDetailsTable">Last Updated Date</td>
				<td id="td_app_lastUpdatedDate" class="td_right_displayDetailsTable"></td>
			</tr>
			<tr>

				<td class="td_right_displayDetailsTable" colspan="2"><s:url
					id="reportURL" value="/secure/displaypatientreports.action"
					var="GotoReports">
					<s:param name="appointmentId"></s:param>
					</s:url><s:a id="reportLink" href="%{GotoReports}">
					Reports (<div id="reports" style="display: inline;"></div>)</s:a>
				</td>
			</tr>
		</table>
	</div>
-->
</div>
