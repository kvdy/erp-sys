<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="theme" value="'simple'" scope="page" />
<script type="text/javascript">
	$(document).ready(function() {
		var field = document.getElementById("userName");
		field.focus();
		//$('#notification').hide();
	});
</script>
<div>
<table width="100%">
<tr>
<td width="30%">
&nbsp;
</td>
<td width="40%" align="center">
<label class="label_messages" id="login_successMessage"><s:property value="successMessage" /></label>
<label class="label_failuremessages" id="login_failureMessage"><s:property value="failureMessage" /></label>
</td>
<td width="30%" align="right" style="padding-right:15px;">
 &nbsp;
</td>
</tr>
</table>
</div>

<div class="loginBoxes">

	<s:form id="loginform" action="out/login.action">
		<div id="personLogin" class="loginBox">
		<label  style="color:blue;font-size:12pt">*Username and Password are case sensitive.</label>
			<div class="frame">
				<div class="title">Login</div>
				<div class="content">
					<div class="filler">
						<!-- <label class="col">Username</label> -->
						<span class="cpcol">Username<font color="red">*</font></span>
						<s:textfield key="Username" tabindex="1" name="userName"
							id="userName" size="30" maxlength="32" value=""></s:textfield>
					</div>
					<div class="filler">
						<span class="cpcol">Password<font color="red">*</font></span>
						<s:password key="Password" tabindex="2" name="password"
							id="password" size="30" maxlength="32" value=""></s:password>
					</div>
				</div>
				<div class="content">
					<div class="filler">
						<s:submit type="button" class="btnClass" tabindex="3" value="Login"
							id="loginbutton" title="Login" />
					</div>
				</div>
			</div>
		</div>
	</s:form>
</div>
