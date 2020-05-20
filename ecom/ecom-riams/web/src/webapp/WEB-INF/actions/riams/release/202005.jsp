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
					<% // Версия от 7 мая %>
				<h1>Май 2020 года</h1>
				
				<ul>
					<li></li>
					<li>Учёт показателей в экспорте ЭЛН: постановка на учёт в службе занятости населения</li>
					<li>Печать протоколов истории болезни без подписи врача</li>
					<li>Печать протокола переливания крови без биологической пробы (при выборе некоторых компонентов этот параметр необязателен)</li>
					<li>Отчёт по аннулированным результатам лаб. исследований</li>
					<li>Отчёт по COVID-19 для медицинских статистиков</li>
					<li>Браслет для беременных после создания карты COVID</li>
					<li>Отчёт по анализам на COVID-19</li>
                    <li>Механизм быстрого создания пользователей с должностью и сбросом пароля (по желанию)</li>
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