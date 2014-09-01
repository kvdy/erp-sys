<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="theme" value="'simple'" scope="page" />
<script src="<%=request.getContextPath()%>/js/patientreportsscript.js"></script>
<div id="bcrumbs">
		<ul id="breadcrumbs-one">
			<li><s:url value="/secure/displaypatientappointments.action"
					var="SearchedPatient">
					<s:param name="patientId">
						<s:property value="patientAppointment.patient.id" />
					</s:param>
				</s:url> <s:a href="%{SearchedPatient}">
					<span> Home 
				</s:a></li>
			<li><a href="javascript: void(0)" style="cursor:text" class="current" id="myReports">My Reports</a></li>
		</ul>
</div>
<!-- main column -->
<div id="mainCell" class="column column-center">
	<!-- title -->
	<div id="mainContainer" class="pdfhtml">
		<br>
		<div id="mainContainerPerson" style="padding-left: 10px;width:1300px;">
			<!-- person details -->
			<table cellpadding="0" cellspacing="0" border="0" width="100%"
				class="zebra peopleViewChunk ntRoundBottom">
				<colgroup span="3">
					<col width="300">
					<col width="350">
					<col width="300">
				</colgroup>
				<tbody>
					<tr>
						<th colspan="3"><span class="body-font-hdr"><strong>Appointment Information</strong></span></th>
					</tr>
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" class="fields" border="0">
								<colgroup span="2">
									<col width="100">
									<col width="200">
								</colgroup>
								<tbody>
									<tr>
										<td align="right"><span class="body-font"><strong>Name : </strong></span></td>
										<td align="left"><span class="body-font"><s:property
												value="patientAppointment.name" /> <s:property
												value="lastname" /></span></td>
									</tr>
									<tr>
										<td align="right"><span class="body-font"><strong>Status : </strong></span></td>
										<td align="left"><span class="body-font"><s:property
												value="patientAppointment.status" /></span></td>
									</tr>
								</tbody>
							</table>
						</td>
						<td>
							<table cellpadding="0" cellspacing="0" class="fields" border="0">
								<colgroup span="2">
									<col width="150">
									<col width="200">
								</colgroup>
								<tbody>
									<tr>
										<td align="right"><span class="body-font"><strong>Doctor's Name : </strong></span></td>
										<td align="left"><span class="body-font"><s:property value="doctorName" /></span></td>
									</tr>
									<tr>
										<td align="right"><span class="body-font"><strong>Time : </strong></td>
										<td align="left"><span class="body-font"><s:property
												value="patientAppointment.strAppointmentDateTime" /></span></td>
									</tr>
								</tbody>
							</table>
						</td>
						<td>
							<table cellpadding="0" cellspacing="0" class="fields" border="0">
								<colgroup span="2">
									<col width="150">
									<col width="150">
								</colgroup>
								<tbody>
									<tr>
										<td align="right"><span class="body-font"><strong>Lab Name : </strong></span></td>
										<td align="left"><span class="body-font"><s:property value="labName" /></span></td>
									</tr>
									<tr>
										<td align="right"><span class="body-font"><strong>Details : </strong></span></td>
										<td align="left"><span class="body-font"><s:property
												value="patientAppointment.details" /></span></td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td align="left" colspan="3"><span class="body-font"><strong>Comments : </strong></span><span class="body-font"><s:property
								value="patientAppointment.comments" /></span></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>

<div style="height: 490px" id="mainContainer">
	<div id="leftwrap">
		<table style="border-top-right-radius:5px;border-top-left-radius:5px;border:1px solid lightgrey">
				<tr>
					<th colspan="5" style="background-color:#F0F0F0" width="100%"><span class="body-font-hdr">Report Details</span></th>
				</tr>
		</table>
		<table id="thetable" width="100%" class="rptTable">
			<thead>
				<tr>
					<th class="th_searchtable" width="20%" align="center"><span class="font-12">Report
						Name</span></th>
					<th class="th_searchtable" width="20%" align="center">Report
						Type</th>
					<th class="th_searchtable" width="20%" align="center">Report
						Status</th>
					<th class="th_searchtable" width="8%" align="center">View</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="patientReports">
				<tr id='<s:property value="id"/>'>					
						<td class="td_searchtable" width="20%"><s:property
								value="name" /></td>
						<td class="td_searchtable" width="20%"><s:property
								value="reportType.name" /></td>
						<td class="td_searchtable" width="12%" style="text-align: center;">
							<%
								String status = request.getAttribute("reportStatus").toString();
							%> <%
								if (status.equals("Released")) {
							%> Available <%
								} else {
							%> Not Available <%
								}
							%>
						</td>
						<td class="td_searchtable" width="8%" style="text-align: center;"><input
							type="radio" name="reportInfoIdRadio"
							onclick="reportInfoIdRadioClicked()"
							value="<s:property value="id"/>"></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
	<div id="div2_center">
		<table width="100%" class="headertable">
			<tr>
				<td class="td_headertable">Report Details</td>
			</tr>
		</table>
		<table class="displayDetailsTable">

			<tr>
				<td class="td_left_displayDetailsTable">Name</td>
				<td id="td_reportinfo_name" class="td_right_displayDetailsTable"
					colspan="2"></td>
			</tr>
			<tr>
				<td class="td_left_displayDetailsTable">RCN</td>
				<td id="td_reportinfo_rcn" class="td_right_displayDetailsTable"
					colspan="2"></td>
			</tr>
			<%--			<tr>
 				<td class="td_left_displayDetailsTable">View Report</td>
				<td class="td_right_displayDetailsTable">
					<s:form style="margin:0" action="" name="viewreportForm" id="viewreportForm">
							<s:submit type="image" value="viewreport" src="../images/pdf_button_32x32.png" 
							id="viewReportButton" title="View Report"  onclick="viewreport()"/>
					</s:form>
				</td> 
			</tr> --%>
			<tr>
				<td class="td_left_displayDetailsTable">Report Status</td>
				<td id="td_reportinfo_reportstatus"
					class="td_right_displayDetailsTable"></td>
				<td style="border:0;background-color:#F0F0F0;"><s:form
						style="margin:0" action="" name="viewreportForm"
						id="viewreportForm">
						<s:submit type="image" value="viewreport"
							src="../images/pdf_button_32x32.png" id="viewReportButton"
							title="View Report" onclick="viewreport()" />
					</s:form></td>
			</tr>
			<tr>
				<td class="td_left_displayDetailsTable">Created Date</td>
				<td id="td_reportinfo_createdDate"
					class="td_right_displayDetailsTable" colspan="2"></td>
			</tr>
			<tr>
				<td class="td_left_displayDetailsTable">Created By</td>
				<td id="td_reportinfo_createdBy"
					class="td_right_displayDetailsTable" colspan="2"></td>
			</tr>
		</table>

	</div>

</div>
