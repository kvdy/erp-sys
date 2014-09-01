<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<script type="text/javascript">
</script>	
</head>
<body>
<!--<div style="height:550px">
 <div>
 	<table width="100%">
		<tr>						
			<td width="30%" align="left" style="font-size:16pt;color:blue;padding-left:15px;">			
				Home
			</td>
			<td width="40%" align="center">
				<label class="label_messages"><s:property value="successMessage" /></label>
				<label class="label_failuremessages"><s:property value="failureMessage" /></label>
			</td>
			<td width="25%" align="right" style="padding-right:15px;">
				
			</td>
			<td width="5%" align="right" style="padding-right:15px;">

			</td>
		</tr>
	</table>
</div>-->
<hr/>

<div style="height:500px">
	<table width="100%">
		<tr>
			<td width="30%"/>
			<td width="70%" align="right" style="font-size:12pt;color:blue;padding-right:15px;">	
				Welcome&nbsp;&nbsp;<s:property value="%{#session.user.firstName}"/>&nbsp;&nbsp;<s:property value="%{#session.user.lastName}"/>, you last signed in on <s:property value="%{#session.lastlogindate}"/>
			</td>	
		</tr>	
	</table>
	<div style="margin-top:3%;padding-left:50px">					
		<s:url value="/secure/displayuseradmin.action" var="UserAdmin" />
		<s:a style="font-size:16pt" href="%{UserAdmin}">User Administration</s:a>
	</div>
	<div style="margin-top:3%;padding-left:50px">
		<s:url value="/secure/displaygroupfeaturerightadmin.action" var="GroupFeatureRightAdmin" />
		<s:a style="font-size:16pt" href="%{GroupFeatureRightAdmin}">Group Feature Right Administration</s:a>
	</div>
	<div style="margin-top:3%;padding-left:50px">
		<s:url value="/secure/displaypatientadmin.action" var="PatientAdmin" />
		<s:a style="font-size:16pt" href="%{PatientAdmin}">Patient Administration</s:a>
	</div>
	<div style="margin-top:3%;padding-left:50px">
		<s:url value="/secure/displaylabadmin.action" var="LabAdmin" />
		<s:a style="font-size:16pt" href="%{LabAdmin}">Lab Administration</s:a>
	</div>
	<div style="margin-top:3%;padding-left:50px">
		<s:url value="/secure/displaylabtechnicianadmin.action" var="LabTechnicianAdmin" />
		<s:a style="font-size:16pt" href="%{LabTechnicianAdmin}">LabTechnician Administration</s:a>
	</div>
	<div style="margin-top:3%;padding-left:50px">
		<s:url value="/secure/displaydoctoradmin.action" var="DoctorAdmin" />
		<s:a style="font-size:16pt" href="%{DoctorAdmin}">Doctor Administration</s:a>
	</div>
</div>
</div>
</body>
</html>