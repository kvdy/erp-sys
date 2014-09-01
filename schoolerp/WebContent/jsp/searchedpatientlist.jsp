<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="theme" value="'simple'" scope="page" />
<!-- main column -->
<div style="height: 500px">

	<div id="centrewrap">
		<ul id="breadcrumbs-one">
			<li><s:url value="/secure/displaydoctorhome.action"
					var="MyDashboard"></s:url> <s:a href="%{MyDashboard}">
					<span> Home </span>
				</s:a></li>
			<li><a href="javascript:void(0)" style="cursor:text" class="current">Searched Patients List</a></li>
		</ul>
		<br>
	</div>

	<div id="mainCell" class="column column-center">

		<div id="exportThis">
			<!-- 					<div id="people_list">
						<table id="peopleList" cellpadding="0" cellspacing="0" border="0"
							class="newTable ntRoundBottom">
							<colgroup span="7">
								<col width="40">
								<col width="15%">
								<col width="15%">
								<col>
								<col width="20%">
								<col width="10%">
								<col width="25">
							</colgroup>
							<thead>
								<tr>
									<th>&nbsp;</th>
									<th><a href="javascript:void(0);"
										onclick="people_list_table.update({'f_sortField': 'firstname','f_sortDir': 'DESC'});">First
											name</a></th>
									<th><a href="javascript:void(0);"
										onclick="people_list_table.update({'f_sortField': 'lastname','f_sortDir': 'DESC'});">Last
											name</a></th>
									<th><a href="javascript:void(0);"
										onclick="people_list_table.update({'f_sortField': 'client','f_sortDir': 'DESC'});">Client</a></th>
									<th><a href="javascript:void(0);"
										onclick="people_list_table.update({'f_sortField': 'groupname','f_sortDir': 'DESC'});">Access
											Level</a></th>
									<th><a href="javascript:void(0);"
										onclick="people_list_table.update({'f_sortField': 'lastlogin','f_sortDir': 'DESC'});">Last
											login</a></th>
									<th class="cellIcon">&nbsp;</th>
								</tr>
							</thead>
							<tbody class="peopleListBody">
 -->
			<s:iterator value="searchedpatients">
				<s:url id="patientURL" value="/secure/displaysearchedpatient.action"
					var="GotoPatient">
					<s:param name="id">
						<s:property value="id" />
					</s:param>
				</s:url>
				<s:a id="patientLink" href="%{GotoPatient}">
					<div id="ovbox" class="roundTop roundBottom">
						<div class="profimg">
							<img src="<s:url action='ImageAction?imgFileName=%{user.userName}'/>" width="80"
								height="80" />
						</div>
						<div class="addrheader">
							<strong><s:property value="user.userName" /></strong>
						</div>
						<div class="nmcardtxt">
							<strong><s:property value="user.lastName" />, <s:property
									value="user.firstName" /></strong>
						</div>
					</div>
				</s:a>
					<%-- 								<tr personid="1" groupname="Administrator" clientid=""
									onmouseover="highlightRow(this,true);rcm(this,event);"
									onmouseout="highlightRow(this,false);"
									class="striped peoplerow  self primaryaccount" title="">
									<td><a href="/people/view/1/+/+/+/list/"
										onmouseover="ttx(this,event)" rel="/people/viewsimple/1/"
										id="anonymous_element_2" title=""> <img
											src="/people/thumb/0/40/" align="absmiddle"
											class="userThumbPLB" border="0" width="40" height="40">
									</a></td>
									<td onclick="redirectURL('/people/view/1/+/+/+/list/');"
										class="clickable"><a href="/people/view/1/+/+/+/list/"
										onmouseover="ttx(this,event)" rel="/people/viewsimple/1/"><s:property value="user.firstName"/></a>
									</td>
									<td onclick="redirectURL('/people/view/1/+/+/+/list/');"
										class="clickable"><a href="/people/view/1/+/+/+/list/"
										onmouseover="ttx(this,event)" rel="/people/viewsimple/1/"
										id="anonymous_element_1" title="">Vaidya</a></td>
									<td onclick="redirectURL('/people/view/1/+/+/+/list/');"
										class="clickable"><em>No client</em></td>
									<td onclick="redirectURL('/people/view/1/+/+/+/list/');"
										class="clickable">Administrator</td>
									<td onclick="redirectURL('/people/view/1/+/+/+/list/');"
										class="clickable">04/15/2014<br> 4:00 PM
									</td>
									<td class="cellIcon"><a href="/people/edit/1/list/"
										title="Edit" class="iconOver"> <img
											src="https://BE19.https.cdn.softlayer.net/80BE19/w0.s.myintervals.com/i/icons/edit.gif"
											width="17" height="17" border="0" alt="Edit"
											name="icon_edit_0">
									</a></td>
								</tr>
 --%>
			</s:iterator>
			<!-- 							</tbody>
							<tfoot>
							</tfoot>
						</table>
 					</div>
-->
		</div>
	</div>
</div>
