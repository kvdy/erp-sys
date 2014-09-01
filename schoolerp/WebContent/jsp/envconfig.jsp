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
<div style="height:550px">
<div>
	<table width="100%">
		<tr>						
			<td width="30%" align="left" style="font-size:16pt;color:blue;padding-left:15px;">			
				Environment Configurations
			</td>			
			<td width="70%" align="right" style="padding-right:15px;">
				<s:url value="/out/displaylogin.action" var="LogIn"/>
				<s:a href="%{LogIn}">LogIn</s:a>
			</td>
		</tr>
	</table>
</div>
<hr/>

<div style="height:500px">
	<div style="margin-top:1%;padding-left:50px">					
		Total number of properties = <s:property value="%{getAppProps().size()}" />		
	</div>
	<br/>
	<table width="80%" border="1" align="center">
		<tr>
			<th width="30%" style="background-color:#CEE3F6;color:black;font-size:14pt;font-weight:bold;text-align:center">Property Name</th>
			<th width="70%" style="background-color:#CEE3F6;color:black;font-size:14pt;font-weight:bold;text-align:center">Property Value</th>
		</tr>			
		<s:iterator value="getAppProps().keySet" var="key">					
     		<tr>     			
				<td width="30%" align="right" style="font-size:12pt;color:black;padding-right:10px;">
					<s:property value="%{#key}"/>					
				</td>	
				<td width="70%" align="left" style="font-size:12pt;color:blue;padding-left:10px;">	
					<s:property value="%{getAppProps().get(#key)}"/>
				</td>	
		  </tr>	
		</s:iterator>
	</table>
</div>
</div>
</body>
</html>