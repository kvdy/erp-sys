<%@page import="com.wtr.bl.vo.UserVO"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="theme" value="'simple'" scope="page" />
<script type="text/javascript">
$(document).ready(function(){
	$('#oldPassword').focus();
});	
</script>
<div id="bcrumbs">
		<ul id="breadcrumbs-one">
				<li><s:url value="/secure/displaypatientappointments.action"
					var="SearchedPatient">
					<s:param name="patientId">
						<s:property value="patientAppointment.patient.id" />
					</s:param>
				</s:url> <s:a href="%{SearchedPatient}"> Home </s:a></li>
			<li><a href="javascript:void(0)" style="cursor:text" class="current">Change Password</a></li>
		</ul>
</div>
<div style="height:550px">
<div>
	<table width="100%">
		<tr>						
			<td width="30%" align="left">			
				&nbsp;
			</td>
			<td width="40%" align="center">
				<label id="changePassword_successMessage" class="label_messages"><s:property value="successMessage" /></label>
				<label id="changePassword_failureMessage" class="label_failuremessages"><s:property value="failureMessage" /></label>
			</td>
			<td width="30%" align="right">
				&nbsp;
			</td>
		</tr>
	</table>
</div>

<div class="loginBoxes">
	<s:form action="changepassword.action" name="changepasswordForm" id="changepasswordForm">
		<div id="personLogin" class="loginBox">
			<div class="frame">
				<div class="title">Change Password</div>
				<div class="content">
					<div class="filler">
						<span class="cpcol">Username</span>
						<s:label style="font-size:14pt" name="userName" id="userName" value="%{#session.user.userName}"></s:label>
					</div>
					<div class="filler">
						<span class="cpcol">Old Password<font color="red">*</font></span>
						<s:password tabindex="1" name="oldPassword" id="oldPassword" size="10" maxLength="32" value=""></s:password>
					</div>
					<div class="filler">
						<span class="cpcol">New Password<font color="red">*</font></span>
						<s:password tabindex="2" name="password" id="password" size="10" maxLength="32" value=""></s:password>
					</div>
					<div class="filler">
						<span class="cpcol">Confirm Password<font color="red">*</font></span>
						<s:password tabindex="3" name="confirmpassword" id="confirmpassword" size="10" maxLength="32" value=""></s:password>
					</div>
				</div>
				<div class="content" style="height:25px">
					<s:submit type="button" style="width:43%;margin-left:42%;height:100%" cssClass="btnClass" tabindex="4"  value="Save" id="changepasswordsavebutton" name="changepasswordsavebutton" title="Save"></s:submit>
				</div>
			</div>		
		</div>
	</s:form>
</div>

<%-- <div id="changepassword_dialog" title="Change Password">
	<s:form action="changepassword.action" name="changepasswordForm" id="changepasswordForm">
		<div>
			<table align="center" style="height: 180px">	
			<tr>
				<td align="right" style="font-size:14pt">User Name</td>
				<td width="14%"></td>
				<td align="left"><s:label name="userName" id="userName" value="%{#session.user.userName}"></s:label></td>
			</tr>
			<tr>
				<td align="right" style="font-size:14pt"><font color="red">*</font>Old Password</td>
				<td width="14%"></td>
				<td align="left"><s:password tabindex="1" name="oldPassword" id="oldPassword" size="20" maxlength="50" value=""></s:password></td>
			</tr>
			<tr>
				<td align="right" style="font-size:14pt"><font color="red">*</font>New Password</td>
				<td width="14%"></td>
				<td align="left"><s:password tabindex="2" name="password" id="password" size="20" maxlength="50" value=""></s:password></td>
			</tr>
			<tr>
				<td align="right" style="font-size:14pt"><font color="red">*</font>Confirm Password</td>
				<td width="14%"></td>
				<td align="left"><s:password tabindex="3" name="confirmpassword" id="confirmpassword" size="20" maxlength="50" value=""></s:password></td>
			</tr>							
			</table>
		</div>		
		<div align="center" style="margin-top:2%">
			<s:submit type="button" style="font-size:12pt;font-style:italic;width:8%" tabindex="4" value="Save" id="changepasswordsavebutton" name="changepasswordsavebutton" title="Save" />
		</div>
	</s:form>
</div>
 --%>
</div>
</body>
</html>