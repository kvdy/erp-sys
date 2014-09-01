<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="theme" value="'simple'" scope="page" /><div id="top-bar">
	<div id="logo"><span class="logo">schoolERP</span></div>
	<div id="right-side">
		<s:url value="#" var="Vast"/>
		<s:a href="%{Vast}">Contact Us</s:a>
	</div>
</div>