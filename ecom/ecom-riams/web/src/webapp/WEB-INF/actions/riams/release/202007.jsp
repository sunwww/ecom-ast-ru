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
					<% // Версия от 13 июля %>
				<h1>Июль 2020 года</h1>
				
				<ul>
					<li></li>
					<li>Добавление браслета с пометкой Мед. работник при создания карты коронавируса</li>
					<li>Импорт результатов анализов</li>
					<li>Вывод даты рождения в печати направлений на COVID</li>
					<li>Вывод информации о свободных местах в госпитале</li>
					<li>Полный запрет на редактирование выписки с момента, как она наступила. Удалять данные выписки теперь может только КЭО.</li>
					<li>Вывод информации о заведующем отделения в списке сотрудников, переход на персону с пользователя</li>
					<li></li>
				</ul>
					</div>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>