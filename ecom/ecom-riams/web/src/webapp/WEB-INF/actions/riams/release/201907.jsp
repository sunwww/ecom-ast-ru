<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

	<tiles:put name='title' type='string'>
	</tiles:put>

	<tiles:put name='body' type='string'>
		<table class='mainMenu'>
			<tr>
				<td class='col1'>
				<div class='menu'>
					<% // Версия от 03 июля %>
				<h1>Июль 2019 года</h1>
				
				<ul>
					<li></li>
					<h2>Лаборатория:</h2>
					<ul>
						<li> * Если отметить БРАК в уже подтверждённом назначении, в начале связанного с анализом дневника появится причина и дата-время брака </li>
					</ul>


					<msh:ifInRole roles="/Policy/Config/HelpAdmin">
							<br>
						</msh:ifInRole>
				</ul>
					</div>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>