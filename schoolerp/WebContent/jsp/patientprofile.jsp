<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="theme" value="'simple'" scope="page" />
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
			<li><a href="javascript: void(0)" style="cursor:text" class="current">My Profile</a></li>
		</ul>
</div>
<div style="height: 500px">
	<!-- main column -->
	<div id="mainCell" class="column column-center">

		<div id="personMsgContainer"></div>

		<!-- title -->

		<div id="mainContainer" class="pdfhtml">
		
			<div id="mainContainerPerson">
				<!-- person details -->
				<table cellpadding="0" cellspacing="0" border="0"
					class="zebra peopleViewChunk ntRoundBottom">
					<colgroup span="4">
						 <col width="300">
						<col width="270">
						<col width="270">
						<col width="270">
					</colgroup>
					<tbody>
						<tr>
							<th colspan="4"><span class="body-font-hdr"><strong>Profile
										Information</strong></span></th>
						</tr>
						<tr>
							<td class="profileimage" rowspan="2" valign="middle"><span
								id="profileImage"><img
									src="<s:url action='ImageAction?imgFileName=%{userInSession.userName}'/>"
									width="150" height="150"> <br></span> &nbsp;<a
								id="genericDeleteTrigger" href="javascript:;" title="Delete"
								onclick="return genericBeginDelete();" style="display: none;">
							</a></td>
							<td>
								<table cellpadding="0" cellspacing="0" class="fields" border="0">
									<colgroup span="2">
										<col width="50">
										<col width="125">
									</colgroup>
									<tbody>
										<tr>
											<td align="right"><span class="body-font"><strong>Full
														Name :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="firstname" /> <s:property value="lastname" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>Username :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="username" /></span></td>
										</tr>
									</tbody>
								</table>
							</td>
							<td>
								<table cellpadding="0" cellspacing="0" class="fields" border="0">
									<colgroup span="2">
										<col width="65">
										<col width="75">
									</colgroup>
									<tbody>
										<tr>
											<td align="right"><span class="body-font"><strong>User
														Status :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="userStatus" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>D.O.B. :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="dateOfBirth" /></span></td>
										</tr>
									</tbody>
								</table>
							</td>
							<td>
								<table cellpadding="0" cellspacing="0" class="fields" border="0">
									<colgroup span="2">
										<col width="170">
										<col width="80">
									</colgroup>
									<tbody>
										<tr>
											<td align="right"><span class="body-font"><strong>Gender :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="gender" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>Password
														will expire in :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="passwordExpirationDays" /></span></td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<table cellpadding="0" cellspacing="0" class="fields" border="0">
									<colgroup span="2">
										<col width="11%">
										<col width="89%">
									</colgroup>
									<tbody>
										<tr>
											<td align="right"><span class="body-font"><strong>Note :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="userInSession.note" /></span></td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<th colspan="4"><span class="body-font-hdr">Insurance
									Information</span></th>
						</tr>
						<tr>
							<td>
								<table cellpadding="0" cellspacing="0" class="fields" border="0">
									<colgroup span="2">
										<col width="145">
										<col width="125">
									</colgroup>
									<tbody>
										<tr>
											<td align="right"><span class="body-font"><strong>Issued
														by :</strong></span></td>
											<td align="left"><span class="body-font"> <s:property
														value="patient.issuedBy" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>Company
														Name :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="patient.companyName" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>Enrollment
														Mode :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="patient.enrollmentMode" /></span></td>
										</tr>
									</tbody>
								</table>
							</td>
							<td>
								<table cellpadding="0" cellspacing="0" class="fields" border="0">
									<colgroup span="2">
										<col width="100">
										<col width="100">
									</colgroup>
									<tbody>
										<tr>
											<td align="right"><span class="body-font"><strong>Policy
														Number :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="patient.policyNumber" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>Sum
														Assured :</strong></span></td>
											<td align="left"><span class="body-font">INR <s:property
														value="patient.sumAssured" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>Ref.
														by :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="patient.enrollmentReferredBy" /></span></td>
										</tr>
									</tbody>
								</table>
							</td>
							<td>
								<table cellpadding="0" cellspacing="0" class="fields" border="0">
									<colgroup span="2">
										<col width="170">
										<col width="80">
									</colgroup>
									<tbody>
										<tr>
											<td align="right"><span class="body-font"><strong>Membership
														Type :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="patient.membershipType" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>Membership
														Exp. Date :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="membershipExpirationDate" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>Relationship
														Type :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="patient.relationshipType" /></span></td>
										</tr>
									</tbody>
								</table>
							</td>
							<td>
								<table cellpadding="0" cellspacing="0" class="fields" border="0">
									<colgroup span="2">
										<col width="160">
										<col width="110">
									</colgroup>
									<tbody>
										<tr>
											<td align="right"><span class="body-font"><strong>Primary
														Insured :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="patient.primaryInsured" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>Insurance Exp. Date :</strong></span></td>
											<td align="left"><span class="body-font"><s:property
														value="insuranceExpirationDate" /></span></td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<th align="center" colspan="2"><span class="body-font-hdr">Patient
									Primary Contact</span></th>
							<th align="center" colspan="2"><span class="body-font-hdr">Patient
									Secondary Contact</span></th>
						</tr>
						<tr>
							<td colspan="2">
								<table cellpadding="0" cellspacing="0" class="fields" border="0">
									<colgroup span="2">
										<col width="100">
										<col width="250">
									</colgroup>
									<tbody>
										<tr>
											<td align="right"><span class="body-font"><strong>Address  :</strong></span></td>
											<td><span class="body-font">
												
											<s:property	value="priContact.flatNumber" />
											
												<s:property value="priContact.locality" />
												<s:property
														value="priContact.street" /> <s:property
														value="priContact.city" /></span></td>
										</tr>
										<tr>
											<td></td>
											<td><span class="body-font"><s:property
														value="priContact.state" /> <s:property
														value="priContact.pincode" /></span></td>
										</tr>
										<tr>
											<td></td>
											<td><span class="body-font"><s:property
														value="priContact.country" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>Phone
														1 :</strong></span></td>
											<td><span class="body-font"><s:property
														value="priContact.phoneNumber1" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>Phone
														2 :</strong></span></td>
											<td><span class="body-font"><s:property
														value="priContact.phoneNumber2" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>Email :</strong></span></td>
											<td><span class="body-font"><s:property
														value="priContact.emailAddress" /></span></td>
										</tr>
									</tbody>
								</table>
							</td>
							<td colspan="2">
								<table cellpadding="0" cellspacing="0" class="fields" border="0">
									<colgroup span="2">
										<col width="100">
										<col width="250">
									</colgroup>
									<tbody>
										<tr>
											<td class="lblwide" align="right"><span
												class="body-font"><strong>Address :</strong></span></td>
											<td><span class="body-font"><s:property
														value="secContact.flatNumber" /> <s:property
														value="secContact.locality" /> <s:property
														value="secContact.street" /> <s:property
														value="secContact.city" /></span></td>
										</tr>
										<tr>
											<td></td>
											<td><span class="body-font"><s:property
														value="secContact.state" /> <s:property
														value="secContact.pincode" /></span></td>
										</tr>
										<tr>
											<td></td>
											<td><span class="body-font"><s:property
														value="secContact.country" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>Phone
														1 :</strong></span></td>
											<td><span class="body-font"><s:property
														value="secContact.phoneNumber1" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>Phone
														2 :</strong></span></td>
											<td><span class="body-font"><s:property
														value="secContact.phoneNumber2" /></span></td>
										</tr>
										<tr>
											<td align="right"><span class="body-font"><strong>Email :</strong></span></td>
											<td><span class="body-font"><s:property
														value="secContact.emailAddress" /></span></td>
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
				<br>
			</div>
		</div>
	</div>
</div>