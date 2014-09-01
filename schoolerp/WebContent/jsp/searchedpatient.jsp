<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="theme" value="'simple'" scope="page" />
<div id="bcrumbs">
	<ul id="breadcrumbs-one">
		<li><s:url value="/secure/displaydoctorhome.action"
				var="MyDashboard"></s:url> <s:a href="%{MyDashboard}">
				<span> Home </span>
			</s:a></li>
			<li><a href="javascript:void(0)" class="current" style="cursor:text">Searched Patient Details</a></li>
		
	</ul>
</div>
<div style="height: 550px;">
	

	<div id="lcontent">
		<!-- person details -->
		<table cellpadding="0" cellspacing="0" border="0"
			class="zebra peopleViewChunk ntRoundBottom">
			<colgroup span="2">
				<col width="140">
				<col width="240">
			</colgroup>
			<tbody>
				<tr>
					<th colspan="2"><span class="body-font-hdr">Searched
							Patient Information</span></th>
				</tr>
				<tr>
					<td class="profileimage" valign="middle" align="center" colspan="2"><span
						id="profileImage"><img
							src="<s:url action='ImageAction?imgFileName=%{username}'/>"
							width="100" height="100" style="bottom: 0;"></span></td>					
					
					</tr>
					</tbody>
					</table>
					
					<div style="overflow:auto;margin:0">
						<table class="displayDetailsTable" style="border:0;">
						<tr>
						<td class="td_left_displayDetailsTable">Full Name</td>
						<td class="td_right_displayDetailsTable"><s:property value="firstname" /> <s:property value="lastname" /></td>
						</tr>
						<tr>
						<td class="td_left_displayDetailsTable">Username</td>
						<td class="td_right_displayDetailsTable"><s:property value="username" /></td>
						</tr>
						<tr>
						<td class="td_left_displayDetailsTable">Date Of Birth</td>
						<td id="td_dob" class="td_right_displayDetailsTable"><s:property value="dateOfBirth" /></td>
						</tr>
						<tr>
						<td class="td_left_displayDetailsTable">Gender</td>
						<td class="td_right_displayDetailsTable"><s:property value="gender" /></td>
						</tr>
						<tr>
						<td class="td_left_displayDetailsTable">Comapany Name</td>
						<td class="td_right_displayDetailsTable"><s:property value="companyName" /></td>
						</tr>
						<tr>
						<td class="td_left_displayDetailsTable">Policy Number</td>
						<td class="td_right_displayDetailsTable"><s:property value="policyNo" /></td>
						</tr>
						<tr>
						<td class="td_left_displayDetailsTable">Primary Insured</td>
						<td class="td_right_displayDetailsTable"><s:property value="primaryInsured" /></td>
						</tr>
						<tr>
						<td class="td_left_displayDetailsTable">Insurance Expiration Date</td>
						<td class="td_right_displayDetailsTable"><s:property value="insuranceExpirationDate" /></td>
						</tr>
						<tr>
						<td class="td_left_displayDetailsTable">Primary Address</td>
						<td class="td_right_displayDetailsTable"><s:property
											value="priContact.flatNumber" />  <s:property
											value="priContact.locality" />  <s:property
											value="priContact.street" />  <s:property
											value="priContact.city" /> <s:property
											value="priContact.state" /> <s:property
											value="priContact.pincode" /></td>
						</tr>
						
						<tr>
							<td class="td_left_displayDetailsTable">Primary Contact</td>
						<td class="td_right_displayDetailsTable"><s:property
											value="priContact.phoneNumber1" />  <s:property
											value="priContact.phoneNumber2" /></td>
						</tr>
						<tr>
							<td class="td_left_displayDetailsTable">Primary Email</td>
						<td class="td_right_displayDetailsTable"><s:property
											value="priContact.emailAddress" />  </td>
						</tr>
						<tr>
						<td class="td_left_displayDetailsTable">Secondary Address</td>
						<td class="td_right_displayDetailsTable"><s:property
											value="secContact.flatNumber" /> <s:property
											value="secContact.locality" /> <s:property
											value="secContact.street" /> <s:property
											value="secContact.city" /> <s:property
											value="secContact.state" /> <s:property
											value="secContact.pincode" /> <s:property
											value="secContact.country" /></td>
						</tr>
						<tr>
							<td class="td_left_displayDetailsTable">Secondary Contact</td>
						<td class="td_right_displayDetailsTable"><s:property
											value="secContact.phoneNumber1" />  <s:property
											value="secContact.phoneNumber2" /></td>
						</tr>
						<tr>
							<td class="td_left_displayDetailsTable">Secondary Email</td>
						<td class="td_right_displayDetailsTable"><s:property
											value="secContact.emailAddress" /></td>
						</tr>
						</table>	
						</div>			
				
	</div>
	<div id="rcontent">
		<table style="border-top-right-radius:5px;border-top-left-radius:5px;border:1px solid lightgrey">
				<tr>
					<th colspan="5" style="background-color:#F0F0F0"  width="100%"><span class="body-font-hdr">Scheduled Appointments</span></th>
				</tr>
		</table>
		<table id="thetable" width="100%" class="apmtTable">
			<thead>
				
				<tr>
					<th class="th_searchtable font-12" width="15%" align="center">Lab</th>
					<th class="th_searchtable font-12" width="20%" align="center">Appointment
						Name</th>
					<th class="th_searchtable font-12" width="20%" align="center">Time</th>
					<th class="th_searchtable font-12" width="15%" align="center">Status</th>
					<th class="th_searchtable font-12" width="25%" align="left">Test
						Preparation Note</th>
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
						<td class="td_searchtable font-12" width="25%"><s:property
								value="testPreparationNotes" /></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<br>
		<table style="border-top-right-radius:5px;border-top-left-radius:5px;border:1px solid lightgrey">
				<tr>
					<th colspan="5" style="background-color:#F0F0F0"  width="100%"><span class="body-font-hdr">Completed Appointments</span></th>
				</tr>
		</table>
		<table id="thestable" width="100%" class="sapmtTable">
			<thead>
				
				<tr>
					<th class="th_searchtable font-12" width="20%" align="center">Laboratory
						Name</th>
					<th class="th_searchtable font-12" width="22%" align="center">Appointment
						Name</th>
					<th class="th_searchtable font-12" width="20%" align="center">Time</th>
					<th class="th_searchtable font-12" width="18%" align="center">Status</th>
					<th class="th_searchtable font-12" width="20%" align="center">Ready Reports</th>
					<th class="th_searchtable font-12" width="8%" align="center">View</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="completedAppointments">
					<tr>
						<td class="td_searchtable font-12" width="20%"><s:property
								value="lab.name" /></td>
						<td class="td_searchtable font-12" width="22%"><s:property
								value="name" /></td>
						<td class="td_searchtable font-12" width="20%"><s:property
								value="strAppointmentDateTime" /></td>
						<td class="td_searchtable font-12" width="18%"><s:property
								value="status" /></td>
						<td class="td_searchtable font-12" style="text-align:center" width="20%"><s:property
								value="numberOfReportsReleased" /></td>	
						<td class="td_searchtable font-12" width="8%"><s:url
								id="reportURL"
								value="/secure/displaysearchedpatientreports.action"
								var="GotoReports">
								<s:param name="appointmentId">
									<s:property value="id" />
								</s:param>
							</s:url> <s:a id="reportLink" href="%{GotoReports}">
								<img src="../images/career_view_button1.png"
									alt="View Appointment Details" />
							</s:a></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
</div>
<!-- 	</div>
</div> -->