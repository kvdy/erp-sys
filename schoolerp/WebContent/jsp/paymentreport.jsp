<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="theme" value="'simple'" scope="page" />
<script>
	$(document).ready(
			function() {
				
				$('#list_date').val(0);
				$('#list_peoples').val(0);
				document.getElementById("list_peoples").value="<s:property value="labName"/>";
				document.getElementById("list_date").value="<s:property value="dateFilterName"/>";
				var dateRangeOption = document.getElementById("list_date").value;
				//alert('dateRangeOption = '+dateRangeOption);
				if (dateRangeOption == 'Custom Date Range') {
					document.getElementById('f_dateBegin').disabled=false;
					document.getElementById("f_dateBegin").value="<s:property value="f_dateBegin"/>";
					document.getElementById('f_dateEnd').disabled=false;
					document.getElementById("f_dateEnd").value="<s:property value="f_dateEnd"/>";
				} else {
					document.getElementById('f_dateBegin').disabled=true;
					document.getElementById('f_dateEnd').disabled=true;
				}
				$('#list_date').change(function() {
					var val = $(this).val();
					if (val == 'Custom Date Range') {
						document.getElementById('f_dateBegin').disabled=false;
						document.getElementById('f_dateEnd').disabled=false;
					} else {
						document.getElementById('f_dateBegin').disabled=true;
						document.getElementById("f_dateBegin").value="";
						document.getElementById('f_dateEnd').disabled=true;
						document.getElementById("f_dateEnd").value="";
					};
				});

				$("#f_dateBegin").datepicker(
						{
							defaultDate : "+1w",
							changeMonth : true,
							dateFormat : "dd/mm/yy",
							numberOfMonths : 3,
							showCurrentAtPos : 2,
							maxDate : new Date(),
							onClose : function(selectedDate) {
								$("#f_dateEnd").datepicker("option", "minDate",
										selectedDate);
							}
						});

				$("#f_dateEnd").datepicker(
						{
							defaultDate : "+1w",
							changeMonth : true,
							dateFormat : "dd/mm/yy",
							numberOfMonths : 3,
							maxDate : new Date(),
							onClose : function(selectedDate) {
								$("#f_dateBegin").datepicker("option",
										"maxDate", selectedDate);
							}
						});
				
			});
</script>
<div style="height: 500px">

	<div id="centrewrap">
		<ul id="breadcrumbs-one">
			<li><s:url value="/secure/displaydoctorhome.action"
					var="MyDashboard"></s:url> <s:a href="%{MyDashboard}">
					<span> Home </span>
				</s:a></li>
			<li><a href="javascript:void(0)" class="current" style="cursor:text">Patient Payment Report</a></li>
		</ul>
		<br>
	</div>
	<div id="estimatedwork">
		<s:form action="/secure/displaypaymentreport" name="paymentreportForm"
			id="paymentreportForm" cssClass="pymtrpt">
			<table border="0" cellpadding="5" cellspacing="10">
				<thead>
					<tr>
						<th colspan="7" width="100%"><span class="body-font-hdr"><strong>Payment
									Report Filter Options</strong></span></th>
					</tr>
					<tr>
						<th colspan="7" width="100%"><hr></th>
					</tr>
				</thead>
				<tr>
					<td><br></td>
				</tr>
				<tr>
					<td align="center" width="15%" ><s:select list="allLabs"
							name="labName" id="list_peoples"  headerKey="All Labs" headerValue="All Labs"></s:select></td>
					<td align="center" width="20%"><s:select list="dateFilter"
							name="dateFilterName" id="list_date" cssClass="multi-select"
							headerKey="All Dates" headerValue="All Dates"></s:select></td>
					<td align="right"><span id="frmdate" class="body-font">From</span></td>		
					<td align="center" width="20%"><s:textfield name="f_dateBegin"
							id="f_dateBegin" size="20" value="" class="date-select"></s:textfield></td>
					<td align="right"><span id="frmdate" class="body-font">To</span></td>		
					<td align="center" width="20%"><s:textfield name="f_dateEnd"
							id="f_dateEnd" size="20" value="" class="date-select"></s:textfield></td>
					<td align="center" width="15%" valign="middle"><span
						class="font-12"><s:submit type="button" cssClass="applybtn"
								tabindex="4" value="Apply" id="f_submit" name="f_submit"
								title="Apply Filter"></s:submit></span>
					<!-- <input type="submit"
						name="f_submit" value="Apply filter" class="submit"> --></td>
				</tr>
			</table>
		</s:form>
		<br>
	</div>

	<div id="pymtrpt">
		<table  style="border-top-right-radius:5px;border-top-left-radius:5px;border:1px solid lightgrey">
			<tr>
				<th colspan="11" style="background-color:#F0F0F0"><span class="body-font-hdr"> Patient Payment Report</span></th>
			</tr>
		</table>
		<table id="thetable" width="100%" class="rptTable">
			<thead>
				<tr>

					<!-- 		<table cellpadding="0" cellspacing="0" border="0"
			class="newTable projectViewChunk">
			<thead>
				<tr>
 -->
					
				</tr>
				<tr>
					<th class="th_searchtable" width="7%" align="center">Patient</th>
					<!-- 					<th class="th_searchtable" width="12%" align="center">Appointment Name</th> -->
					<th class="th_searchtable" width="12%" align="center">Appointment
						Date</th>
					<th class="th_searchtable" width="8%" align="center">Laboratory</th>
					<th class="th_searchtable" width="12%" align="center">Payment
						Date</th>
					<th class="th_searchtable" width="9.5%" align="center">Payment
						Type</th>
					<th class="th_searchtable" width="9.5%" align="center">Total
						Amount</th>
					<th class="th_searchtable" width="9%" align="center">Amount
						Paid</th>
					<th class="th_searchtable" width="10.5%" align="center">Pending
						Amount</th>
					<th class="th_searchtable" width="10%" align="center">Payment
						Note</th>
					<th class="th_searchtable" width="7%" align="center">Paid By</th>
				</tr>
			</thead>

			<tbody>
				<s:iterator value="pymtsForRpt">
					<tr>
						<td class="td_searchtable" width="7%"><s:property
								value="appointment.patient.user.firstName" />&nbsp;<s:property
								value="appointment.patient.user.lastName" /></td>
						<%-- 					<td class="td_searchtable" width="12%"><s:property value="appointment.name"/></td> --%>
						<td class="td_searchtable" width="12%"><s:property
								value="appointment.strAppointmentDateTime" /></td>
						<td class="td_searchtable" width="8%"><s:property
								value="appointment.lab.name" /></td>
						<td class="td_searchtable" width="12%"><s:property
								value="strPaymentDate" /></td>
						<td class="td_searchtable" width="9.5%"><s:property
								value="paymentType" /></td>
						<td class="td_searchtable" width="9.5%"><s:property
								value="strTotalAmount" /></td>
						<td class="td_searchtable" width="9%"><s:property
								value="strPaidAmount" /></td>
						<td class="td_searchtable" width="10.5%"><s:property
								value="strPendingAmount" /></td>
						<td class="td_searchtable" width="10%"><s:property
								value="note" /></td>
						<td class="td_searchtable" width="7%"><s:property
								value="paidBy" /></td>
					</tr>
				</s:iterator>
			</tbody>

			<tfoot>
				<tr>
					<th colspan="11">&nbsp;</th>
				</tr>
			</tfoot>
		</table>
	</div>
</div>
