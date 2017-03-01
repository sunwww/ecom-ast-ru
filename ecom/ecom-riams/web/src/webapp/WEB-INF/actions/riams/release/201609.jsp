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
				<h1>Сентябрь 2016 года</h1>
				<ul>
					<li>Печать реестра по лаборатории: начало суток = 16:00</li>
					<li>Исправлено помечание распечатанных исследований как распечатанные</li>
					<li>ДМС: Реализована возможность удаления записей по группе счета</li>
					<li>В направление на госпитализацию добавлены поле "показания к госпитализации"</li>
					<li>Добавлен уровень оказания мед помощи для ЛПУ</li>
					<li>13 форма - добавлено отображение профиля операции</li>
					<li></li>
				
				</ul>
				<h2>В рамках движения "Свободное ПО" Ткачевой Светланой сделано:</h2>
				<ul>
					<li>Исправлена ошибка при переносе данных с одной персоны в другую</li>
					<li>Изменено поле номер направления в талоне</li>
					<li>Добавлено ограничение по дате смерти при редактирование талона </li>
					<li>Доработано изменение пароля для версии с СУБД Cache</li>
				</ul>
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>