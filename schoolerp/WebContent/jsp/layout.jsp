<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>schoolERP</title>
<link href="<%=request.getContextPath()%>/styles/reset-min.css"
	rel="stylesheet" type="text/css"></link>
<link
	href="<%=request.getContextPath()%>/styles/jquery-ui-1.10.3.custom.css"
	rel="stylesheet" type="text/css"></link>
<link href="<%=request.getContextPath()%>/styles/jquery-ui.css"
	rel="stylesheet" type="text/css"></link>
<link href="<%=request.getContextPath()%>/styles/demo_page.css"
	rel="stylesheet" type="text/css"></link>
<link href="<%=request.getContextPath()%>/styles/demo_table.css"
	rel="stylesheet" type="text/css"></link>
<link href="<%=request.getContextPath()%>/styles/demo_table_jui.css"
	rel="stylesheet" type="text/css"></link>
<link href="<%=request.getContextPath()%>/styles/styles.css"
	rel="stylesheet" type="text/css"></link>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-1.8.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery.validate.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquerydataTables.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery/jquery.qtip-1.0.0-rc3.min.js"></script>
<!-- <script type="text/javascript"  src="<%=request.getContextPath()%>/jquery/jquery-ui-timepicker-addon.js"></script>-->
<script type="text/javascript"  src="<%=request.getContextPath()%>/jquery/jquery.ui.datepicker.js"></script>
<script src="<%=request.getContextPath()%>/js/script.js"></script>
</head>
<body bgcolor="#FFFFFF" topmargin=0 leftmargin=0 rightmargin=0
	bottommargin=0>


	<div id="page_wrap">
		<tiles:insertAttribute name="header" />
	<div id="zone-bar">
			<tiles:insertAttribute name="menu"/>
	</div>
	<div id="main_content">
		<div id="left_menu">
			<tiles:insertAttribute name="lmenu"/>
		</div>
		<tiles:insertAttribute name="body" />
	</div>
	<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>