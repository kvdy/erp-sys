<%@page import="com.wtr.bl.vo.UserVO"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div id="top-bar">
	<div id="logo"><span class="logo">schoolERP</span></div>
	<div id="right-side">
	<s:if test="toggleMenu=='true'">
		<s:url value="/secure/displayprofile.action" var="MyProfile">
		</s:url>
		<s:a href="%{MyProfile}" cssClass="first body-font-hdr">My Profile</s:a>
				&ensp;<div style="display: inline;color: #fff">|</div>
	</s:if>
	<s:if test="toggleMenu=='false'">
 	<s:url value="/secure/displaychangepassword.action" var="ChangePwd">				
					<s:param name="userId" value="%{#session.user.userId}"/>					
				</s:url>
				<s:a href="%{ChangePwd}" cssClass="first body-font-hdr">Change Password</s:a>
				&ensp;<div style="display: inline;color: #fff">|</div>
</s:if>
		&ensp;<s:url value="/out/displaylogin.action" var="LogOut"/>
				<s:a href="%{LogOut}" cssClass="first body-font-hdr">LogOut</s:a>
			<!-- &ensp; <a href="#"></a>&ensp; <a
			href="#"></a> &emsp; -->
	</div>
</div>