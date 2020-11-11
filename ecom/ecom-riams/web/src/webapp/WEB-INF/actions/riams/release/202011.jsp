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
					<% // Версия от 30 октября %>
				<h1>Ноябрь 2020 года</h1>
				<ul>
					<li></li>
					<li>Удаление данных выписки по ролям в течение календарного дня (строго не в инфекционных отделениях) </li>
					<li>Перенос случая родов, классификации Робсона и, если есть, карты оценки риска ВТЭО, в другое СЛО (если рано создали случай в родовом отделении)</li>
					<li>Отчёт по данным выписки, которые были удалены</li>
					<li>Печать данных по принятым пациентам</li>
					<li>Вывод результата госпитализации при печати выписной формы</li>
					<li>В карте Covid-19 добавлен справочник с КТ. Если выбран результат КТ, обязательно заполнить дату и место проведения</li>
					<li></li>
				</ul>
					</div>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>