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
					<% // Версия от 3 марта %>
				<h1>Март 2020 года</h1>
				
				<ul>
					<li></li>
					<li>Экспертиза KP: экспертные карты заведующего и эксперта (КР)</li>
					<li>Изменения в форме переливания крови и её компонентов, печать согласия</li>
					<li>Возврат даты предварительной госпитализации в планирование введения ангиогенеза</li>
					<li>Возможность обновить ЭЛН, который уже есть в системе. Обновляется ФИО, ДР, место работы, хеш и отметка о выгрузке в периодах </li>
					<li>Осложнения в хирургической операции, отчёт по ним в анализе работы отделений</li>
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