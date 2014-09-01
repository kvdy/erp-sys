<%@ taglib uri="/struts-tags" prefix="s"%>
<ul>
	<li><s:url value="/secure/displaydoctorhome.action" var="MyDashboard"></s:url>
		<s:a href="%{MyDashboard}"><span> Home &nbsp; <em
				class="opener-technology">
				 <img src="<s:url value="/images/zonebar-downarrow.png"/>" alt="dropdown" />
			</em>
		</span></s:a>	
	
<%-- 	<a href="#"><span> My Dashboard &nbsp; <em
				class="opener-technology">
				 <img src="<s:url value="/images/zonebar-downarrow.png"/>" alt="dropdown" />
			</em>
		</span></a> --%></li>
<%-- 	<li><a href="#"><span> Reports &nbsp; <em
				class="opener-science"> <img src="<s:url value="/images/zonebar-downarrow.png"/>" alt="dropdown" />
			</em>
		</span></a></li>
 --%><%-- 	<li><s:url value="/secure/displaydoctorappointments.action" var="MyAppointments"></s:url>
		<s:a href="%{MyAppointments}"><span> Appointments &nbsp; <em
				class="opener-technology">
				<img src="<s:url value="/images/zonebar-downarrow.png"/>" alt="dropdown"/>
			</em>
		</span></s:a>
	 --%>
<%-- 	<a href="#"><span> Appointments &nbsp; <em
				class="opener-gaming"> <img src="<s:url value="/images/zonebar-downarrow.png"/>" alt="dropdown" />
			</em>
		</span></a> --%></li>
</ul>
