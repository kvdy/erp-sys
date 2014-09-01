<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="theme" value="'simple'" scope="page" />
<div style="height: 500px">
	<!-- support column -->
	<div id="supportCell" class="column column-first-270">
		<table cellpadding="0" cellspacing="1" border="0"
			class="zebra stretch peopleItemChunk ntRoundBottom">
			<tbody>
				<tr>
					<th>Profile Image</th>
				</tr>
				<tr>
					<td class="profileimage"><span id="profileImage"><img
							src="../images/saved_resource.png" width="150" height="150">
							<br> <em>No profile image specified.</em></span> &nbsp;<a
						id="genericDeleteTrigger" href="javascript:;" title="Delete"
						onclick="return genericBeginDelete();" style="display: none;">
					</a></td>
				</tr>
			</tbody>
		</table>
	</div>

	<!-- main column -->
	<div id="mainCell" class="column column-center">

		<div id="personMsgContainer"></div>

		<div id="exportThis">
			<!-- title -->

			<div id="mainContainer" class="pdfhtml">
				<div id="mainContainerPerson">
					<!-- person details -->
					<table cellpadding="0" cellspacing="0" border="0"
						class="zebra peopleViewChunk ntRoundBottom">
						<colgroup span="3">
							<col width="250">
							<col width="250">
							<col width="250">
						</colgroup>
						<tbody>
							<tr>
								<th colspan="3">Profile Information</th>
							</tr>
							<tr>
								<td>
									<table cellpadding="0" cellspacing="0"
										class="fields" border="0">
										<colgroup span="2">
											<col width="50">
											<col width="125">
										</colgroup>
										<tbody>
											<tr>
												<td align="right">Full Name</td>
												<td align="left"><s:property value="firstname"/> <s:property value="lastname"/></td>
											</tr>
											<tr>
												<td align="right">Username</td>
												<td align="left"><s:property value="username"/></td>
											</tr>
										</tbody>
									</table>
								</td>
								<td>
									<table cellpadding="0" cellspacing="0"
										class="fields" border="0">
										<colgroup span="2">
											<col width="65">
											<col width="75">
										</colgroup>
										<tbody>
											<tr>
												<td align="right">User Status</td>
												<td align="left"><s:property value="userStatus"/></td>
											</tr>
											<tr>
												<td align="right">D.O.B</td>
												<td align="left"><s:property value="dateOfBirth"/></td>
											</tr>
										</tbody>
									</table>
								</td>
								<td>
									<table cellpadding="0" cellspacing="0"
										class="fields" border="0">
										<colgroup span="2">
											<col width="150">
											<col width="100">
										</colgroup>
										<tbody>
											<tr>
												<td align="right">Gender</td>
												<td align="left"><s:property value="gender"/></td>
											</tr>
											<tr>
												<td align="right">Password will expire in</td>
												<td align="left"><s:property value="passwordExpirationDays"/></td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<table cellpadding="0" cellspacing="0"
										class="fields" border="0">
										<colgroup span="2">
											<col width="11%">
											<col width="89%">
										</colgroup>
										<tbody>
											<tr>
												<td align="right">Note</td>
												<td align="left"><s:property value="userInSession.note"/></td>
											</tr>
										</tbody>
									</table>									
								</td>
							</tr>
							</tbody>
							</table>
					<table cellpadding="0" cellspacing="0" border="0"
						class="zebra peopleViewChunk ntRoundBottom">
						<colgroup span="3">
							<col width="250">
							<col width="250">
							<col width="250">
						</colgroup>
						<tbody>
							<tr>
								<th colspan="3">Insurance Information</th>
							</tr>					
							<tr>
								<td>
									<table cellpadding="0" cellspacing="0" 
										class="fields" border="0">
										<colgroup span="2">
											<col width="125">
											<col width="125">
										</colgroup>
										<tbody>
											<tr>
												<td align="right">Issued by</td>
												<td align="left">
													<s:property value="patient.issuedBy"/>
												</td>
											</tr>
											<tr>
												<td align="right">Company Name</td>
												<td align="left"><s:property value="patient.companyName"/></td>
											</tr>
											<tr>
												<td align="right">Enrollment Mode</td>
												<td align="left"><s:property value="patient.enrollmentMode"/></td>
											</tr>
											<tr>
												<td align="right">Insurance Exp. Date</td>
												<td align="left"><s:property value="insuranceExpirationDate"/></td>											</tr>
										</tbody>
									</table>
								</td>
								<td>
									<table cellpadding="0" cellspacing="0"
										class="fields" border="0">
										<colgroup span="2">
											<col width="100">
											<col width="100">
										</colgroup>
										<tbody>
											<tr>
												<td align="right">Policy Number</td>
												<td align="left"><s:property value="patient.policyNumber"/></td>
											</tr>
											<tr>
												<td align="right">Sum Assured</td>
												<td align="left">INR <s:property value="patient.sumAssured"/></td>
											</tr>
											<tr>
												<td align="right">Ref. by</td>
												<td align="left"><s:property value="patient.enrollmentReferredBy"/></td>
											</tr>
											<tr>
												<td align="right">Primary Insured?</td>
												<td align="left"><s:property value="patient.primaryInsured"/></td>
											</tr>
										</tbody>
									</table>
								</td>
								<td>
									<table cellpadding="0" cellspacing="0"
										class="fields" border="0">
										<colgroup span="2">
											<col width="150">
											<col width="100">
										</colgroup>
										<tbody>
											<tr>
												<td align="right">Membership Type</td>
												<td align="left"><s:property value="patient.membershipType"/></td>
											</tr>
											<tr>
												<td align="right">Membership Exp. Date</td>
												<td align="left"><s:property value="membershipExpirationDate"/></td>
											</tr>
											<tr>
												<td align="right">Relationship Type</td>
												<td align="left"><s:property value="patient.relationshipType"/></td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
					<table cellpadding="0" cellspacing="0" border="0"
						class="zebra peopleViewChunk ntRoundBottom">
						<colgroup span="2">
							<col width="375">
							<col width="375">
						</colgroup>
						<tbody>
							<tr>
								<th align="center">Patient Primary Contact</th>
								<th align="center">Patient Secondary Contact</th>
							</tr>
							<tr>
								<td>
									<table cellpadding="0" cellspacing="0"
										class="fields" border="0">
										<colgroup span="2">
											<col width="100">
											<col width="250">
										</colgroup>
										<tbody>
											<tr>
												<td align="right">Address</td>
												<td><s:property value="priContact.flatNumber"/> <s:property value="priContact.locality"/> <s:property value="priContact.street"/> <s:property value="priContact.city"/></td>
											</tr>
											<tr>
												<td></td>
												<td><s:property value="priContact.state"/> <s:property value="priContact.pincode"/></td>
											</tr>
											<tr>
												<td></td>
												<td><s:property value="priContact.country"/></td>
											</tr>
											<tr>
												<td align="right">Phone 1</td>
												<td><s:property value="priContact.phoneNumber1"/></td>
											</tr>
											<tr>
												<td align="right">Phone 2</td>
												<td><s:property value="priContact.phoneNumber2"/></td>
											</tr>
											<tr>
												<td align="right">Email</td>
												<td><s:property value="priContact.emailAddress"/></td>
											</tr>
										</tbody>
									</table>
								</td>
								<td>
									<table cellpadding="0" cellspacing="0"
										class="fields" border="0">
										<colgroup span="2">
											<col width="100">
											<col width="250">
										</colgroup>
										<tbody>
											<tr>
												<td class="lblwide" align="right">Address</td>
												<td><s:property value="secContact.flatNumber"/> <s:property value="secContact.locality"/> <s:property value="secContact.street"/> <s:property value="secContact.city"/></td>
											</tr>
											<tr>
												<td></td>
												<td><s:property value="secContact.state"/> <s:property value="secContact.pincode"/></td>
											</tr>
											<tr>
												<td></td>
												<td><s:property value="secContact.country"/></td>
											</tr>
											<tr>
												<td align="right">Phone 1</td>
												<td><s:property value="secContact.phoneNumber1"/></td>
											</tr>
											<tr>
												<td align="right">Phone 2</td>
												<td><s:property value="secContact.phoneNumber2"/></td>
											</tr>
											<tr>
												<td align="right">Email</td>
												<td><s:property value="secContact.emailAddress"/></td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>					
				</div>
				</div>
			</div>
		</div>
	</div>