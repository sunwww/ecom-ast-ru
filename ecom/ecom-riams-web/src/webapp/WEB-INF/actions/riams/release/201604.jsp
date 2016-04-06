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
				<h1>Апрель 2016 года</h1>
				<h2>Изменения от 06.04.2016</h2>
				<ul>
					<li>Исправлена ошибка при автоматическом закрытии большого количество открытых СПО</li>
				<li>Запрет на набитие диспансеризации для пациентов без актуального полиса</li>
					<li>Доработан классификатор - теперь в нем отображаются листы назначений</li>
					<li>Доработано проставление участка при импорте прикрепленного населения (исправлена ошибка при нахождении 2х похожих адресов, напр. "ул. Ленина" и "пл. Ленина")</li>
					<li>Добавлено отображения типа назначения в журнале забора биоматериала</li>
					<li></li>
				</ul>
				
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>