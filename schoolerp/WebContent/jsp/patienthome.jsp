<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="theme" value="'simple'" scope="page" />
<style type="text/css">
#thetable_length {
	display: none;
}
</style>
<div style="height: 500px" id="mainContainer">

	<div id="rbox">
		<table cellpadding="0" cellspacing="0" border="0"
			class="zebra peopleViewChunk ntRoundBottom">
			<colgroup span="3">
				<col width="250">
				<col width="250">
				<col width="250">
			</colgroup>
			<tbody>
				<tr>
					<th colspan="3">Upcoming Appointments</th>
				</tr>
				<tr>
					<td colspan="3">
						<table>
							<tr>
								<td>Date/Time</td>
								<td>Appointment Name</td>
								<td>Lab</td>
								<td>Status</td>

							</tr>
							<tr>
								<td colspan="4">
									<hr>
								</td>
							</tr>
							<s:if test="emptyAppointmentsFlag == false">

								<s:iterator value="userAppointments">
									<tr>
										<td><s:property value="strAppointmentDateTime" /></td>
										<td><s:property value="name" /></td>
										<td><s:property value="lab.name" /></td>
										<td><s:property value="status" /></td>
									</tr>
								</s:iterator>
							</s:if>
							<s:else>
								<tr>
									<td colspan="4">No appointments this week</td>
								</tr>
							</s:else>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

</div>
