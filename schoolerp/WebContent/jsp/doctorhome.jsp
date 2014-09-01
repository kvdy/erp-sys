<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="theme" value="'simple'" scope="page" />
<style type="text/css">
#thetable_length {
	display: none;
}
</style>
<script>
	$(document).ready(function() {
		$('#errmsg').hide();
	//	$('#notification').hide();
		/*$('#f_dateBegin').hide();
		$('#f_dateEnd').hide();
		$('#frmdate').hide();
		$('#todate').hide();
		$('#filler').hide();
		
		$('#list_date').val(0);
		$('#list_peoples').val(0);
	
		$('#list_date').change(function () {
			var val = $(this).val();
			if(val == 'Custom Date Range') {
				$('#f_dateBegin').show();
				$('#f_dateEnd').show();
				$('#frmdate').show();
				$('#todate').show();
				$('#filler').show();
			} else {
				$('#f_dateBegin').hide();
				$('#f_dateEnd').hide();
				$('#frmdate').hide();
				$('#todate').hide();
				$('#filler').hide();
			}
		});*/
		document.getElementById("f_dateEnd").disabled=true;
		document.getElementById("f_dateBegin").disabled=true;
		$('#list_date').change(function () {
			var val = $(this).val();
			if(val == 'Custom Date Range') {
				document.getElementById("f_dateEnd").disabled=false;
				document.getElementById("f_dateBegin").disabled=false;
			}else{
				document.getElementById("f_dateEnd").disabled=true;
				document.getElementById("f_dateBegin").disabled=true;
				}
			});
	    $("#f_dateBegin").datepicker({
	    	defaultDate: "+1w",
	        changeMonth: true,
	        dateFormat: "dd/mm/yy",
	        numberOfMonths: 3,
	        showCurrentAtPos: 2,
	        maxDate: new Date(),
	        onClose: function( selectedDate ) {
	          $( "#f_dateEnd" ).datepicker( "option", "minDate", selectedDate );
	        }
	    });

	    $("#f_dateEnd").datepicker({
	        defaultDate: "+1w",
	        changeMonth: true,
	        dateFormat: "dd/mm/yy",
	        numberOfMonths: 3,
	        maxDate: new Date(),
	        onClose: function( selectedDate ) {
	          $( "#f_dateBegin" ).datepicker( "option", "maxDate", selectedDate );
	        }
   	    });
	    
	});
</script>
<!-- <div id="bcrumbs">
		<ul id="breadcrumbs-one">
			<li><a href="javascript:void(0)" class="current">Welcome &nbsp;Dr. &nbsp;<s:property
					value="%{#session.user.firstName}" />&nbsp;&nbsp;<s:property
					value="%{#session.user.lastName}" /></a></li>
		</ul>
</div> -->
<div style="background: #eee;height">
<table width="100%">
<tr>
<td width="30%" style="padding-left:30px;">
<label style="font-size:12pt">Welcome </label><label style="font-size:12pt;font-weight:bold">Dr. <s:property value="%{#session.user.firstName}"/>&nbsp;<s:property value="%{#session.user.lastName}"/></label>
</td>
<td width="40%" align="center">

</td>
<td width="30%" align="right" style="padding-right:30px;font-size:12pt">You last signed in on <s:property value="%{#session.user.strLastLoginDate}"/>
</td>
</tr>
</table>
</div>
<div>
<table style="width:100%">
<tr>
<td width="30%">

</td>
<td width="40%" align="center">
<label id="doctorhome_successMessage" class="label_messages"><s:property value="successMessage" /></label>
<label id="doctorhome_failureMessage" class="label_failuremessages"><s:property value="failureMessage" /></label>
</td>
<td width="30%" align="right" style="padding-right:15px;">
				
</td>
</tr>
</table>
</div>
<div style="height: 500px" id="mainContainer">

	<div id="psrchbox" style="margin-left:120px">
	<!-- <div id="notification"  class="messageError"></div> -->
		<s:form action="/secure/displaysearchedpatients"
			id="searchpatientform">
			<table cellpadding="10" cellspacing="1" border="0"
				class="zebra stretch">
				<!-- project details -->
				<tbody>
					<tr>
						<th><span class="body-font-hdr"><strong>Search Patient</strong></span></th>
					</tr>
					<tr>
						<td style="padding-left:15%">
							<table cellpadding="10" cellspacing="0" border="0" class="fields"
								style="width: auto">
								<colgroup span="2">
									<col width="133">
									<col width="200">
								</colgroup>
								<tbody>
									<tr>
										<td align="right"><span class="body-font"><strong>First Name:</strong><font color="red">*</font><span class="body-font"></td>
										<td colspan="2"><input type="text" class="stretch"
											name="fname" id="fname" value=""
											style="width: 100% !important; margin-bottom: 5px"
											placeholder="Patient's firstname"></td>
									</tr>
									<tr>
										<td align="right"><span class="body-font"><strong>Last Name:</strong><font color="red">*</font></span></td>
										<td colspan="2"><input type="text" class="stretch"
											name="lname" id="lname" value=""
											style="width: 100% !important; margin-bottom: 5px"
											placeholder="Patient's lastname"></td>
									</tr>
									<tr style="height:60px">&nbsp;</tr>
								</tbody>
							</table>
						</td>
						
					</tr>
					
					<tr>
						<td><s:submit type="button" cssClass="btnClass" style="width:35%;font-size:14pt;margin-left:37%;height:30px"
								value="Search" id="searchpatientbutton"
								name="searchpatientbutton" title="Search"></s:submit></td>
					</tr>
					
				</tbody>
			</table>
		</s:form>

	</div>
	<div id="psrchbox" style="margin-left:0%;padding-left:0%">
		<s:form action="/secure/displaypaymentreport" name="paymentreportForm" id="paymentreportForm">
			<table cellpadding="10" cellspacing="1" border="0"
				class="zebra stretch">
				<!-- project details -->
				<tbody>
					<tr>
						<th><span class="body-font-hdr"><strong>Quick Patient Payment Report</strong></span></th>
					</tr>
					<tr>
						<td style="padding-left:15%">
							<table cellpadding="10" cellspacing="0" border="0" class="fields"
								style="width: auto">
								<colgroup span="2">
									<col width="133">
									<col width="200">
								</colgroup>
								<tbody >
									<tr>
										<td align="right"><span class="body-font"><strong>Lab :</strong></span></td>
										<td colspan="2">
											<s:select list="allLabs" name="labName" id="list_peoples" style="height:25px;width:100% !important; margin-bottom: 5px;font-size:12pt" cssClass="multi-select" headerKey="All Labs" headerValue="All Labs"></s:select>
										</td>
									</tr>
									<tr>	
											<td align="right"><span class="body-font"><strong>Date Range :</strong></span></td>
											<td colspan="2">
											<s:select list="dateFilter"  name="dateFilterName"  style="height:25px;width:100% !important; margin-bottom: 5px;font-size:12pt"  id="list_date" cssClass="multi-select" headerKey="All Dates" headerValue="All Dates"></s:select>
										</td>	
									</tr>
									<tr>
										<td align="right"><span id="frmdate" class="body-font">From</span></td>
										<td>
											<s:textfield name="f_dateBegin" id="f_dateBegin" size="20" style="width:90%" value="" class="date-select"></s:textfield>
										</td>
										
									</tr>
									<tr>
										<td align="right"><span id="todate" class="body-font">To</span></td>
											<td>
												<s:textfield name="f_dateEnd" id="f_dateEnd" size="20" style="width:90%" value="" class="date-select"></s:textfield>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td><s:submit type="button" cssClass="btnClass" style="width:32%;font-size:14pt;margin-left:38%;height:30px"
								value="Payment Report" id="f_submit"
								name="f_submit" title="Payment Report"></s:submit></td>
					</tr>
				</tbody>
			</table>
		</s:form>
		
	</div>
<%-- 	<div id="rbox">
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
 --%>
</div>
