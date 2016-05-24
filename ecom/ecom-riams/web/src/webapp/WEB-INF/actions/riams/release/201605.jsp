<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

	<tiles:put name='title' type='string'>
	</tiles:put>

	<tiles:put name='body' type='string'>
		<table class='mainMenu'>
			<tr>
				<td class='col1'>
				<div class='menu'>
				<h1>Май 2016 года</h1>
				<ul>
					<li>1. Доп. диспансеризация: добавлены проверки на даты услуг (если дата приходится на воскресенье, либо услуга оказана позже даты окончания ДД, сохранение карты невозможно) </li>
					<li>2. Исправлена ошибка входа при наличии выполненных, но не подтвержденных заявок</li>				
					<li>2. Исправлена ошибка, при которой не сохранялась причина отбраковки биоматериала в лаборатории</li>				
					<li>2. Исправлена ошибка, при которой было возможно создание двух направлений на одно время</li>				
				</ul>

				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>