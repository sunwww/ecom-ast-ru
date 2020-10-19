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
					<% // Версия от 1 октября %>
				<h1>Октябрь 2020 года</h1>
				
				<ul>
					<li></li>
					<li>Отчёт по умершим с ПЦР</li>
					<li>Отчёт по COVID для лаборатории по пациентам, состоящим во всех инфекционных отделениях</li>
					<li>Журнал COVID для лаборатории (пациенты по всем инфекционным отделениям за период)</li>
					<li>Механизм листов наблюдения и протоколов для ЕДКЦ (беременные)</li>
					<li>Чек-листы контроля качества по степеням тяжести заболевания</li>
					<li>Журнал чек-листов контроля качества по степеням тяжести заболевания</li>
					<li></li>
					<msh:ifInRole roles="/Policy/Config/HelpAdmin">
							<br>
						</msh:ifInRole>
				</ul>
					</div>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>