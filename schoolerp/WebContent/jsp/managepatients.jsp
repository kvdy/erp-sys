<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="theme" value="'simple'" scope="page" />
<script src="<%=request.getContextPath()%>/js/managepatientsscript.js"></script>
<div style="height: 500px">
	<div id="centrewrap">
		<table id="thetable" width="100%" class="apmtTable">
			<thead>
				<tr>
					<th class="th_searchtable" width="20%" align="center">Patient
						Name</th>
					<th class="th_searchtable" width="20%" align="center">Appointment
						Name</th>
					<th class="th_searchtable" width="20%" align="center">Time</th>
					<th class="th_searchtable" width="20%" align="center">Status</th>
					<th class="th_searchtable" width="8%" align="center">View</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="searchedPatients">
					<tr>
						<td class="td_searchtable" width="20%"><s:property
								value="lab.name" /></td>
						<td class="td_searchtable" width="20%"><s:property
								value="name" /></td>
						<td class="td_searchtable" width="20%"><s:property
								value="strAppointmentDateTime" /></td>
						<td class="td_searchtable" width="20%"><s:property
								value="status" /></td>
						<td class="td_searchtable" width="8%"><input type="radio"
							name="appointmentIdRadio" onclick="appointmentIdRadioClicked()"
							value="<s:property value="id"/>"></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
	<div id="div2_center">
		<table width="100%">
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
 -->
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

		</table>

	</div>

</div>
