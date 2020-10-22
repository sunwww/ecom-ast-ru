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
					<% // Версия от 2 июня %>
				<h1>Июнь 2020 года</h1>
				
				<ul>
					<li></li>
					<li>Учёт даты смерти в подсчёте возраста в персоне</li>
					<li>Запрет на изменение времени забора биоматериала</li>
					<li>Запрет на удаление операции, с которой связан браслет</li>
					<li>При установке нового начальника ЛПУ у предыдущего начальника этого ЛПУ это значение автоматически убирается</li>
					<li>Вывод диагнозов в зависимости от поиска по дате в журнале по коечному фонду</li>
					<li></li>
				</ul>
					</div>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>