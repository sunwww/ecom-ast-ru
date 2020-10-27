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
					<% // Версия от 17 октября
					%>
				<h1>Октябрь 2017 года</h1>
				
				<ul>
					<li>263 приказ. Добавлено поле "номер направления ЛПУ". Добавлено указанное поле, номер направления формируется автоматически</li>
					<li>Разное: Исправлены опечатки в запросе при получении списка полисов ДМС,</li>
					<li>Личный кабинет пациента. </li>
					...Добавлен код случая, появилась возможность повторно выгрузить историю лечения пациента</li>
					...Добавлена возможность выгрузить истории пациента после оформления согласия</li>
					<li>Поле "Номер талона ВМП". Добавлено поле, поле отображается в госпитализации</li>
					<li>Электронный больничный лист</li>
					<li>...Исправлена ошибка при повторном нажатии на ссылку анулирования ЭЛН.</li>
					<li>...Доработан функционал по импорту данных ЭЛН с ФСС</li>
					<li>...Добавлены кнопки для импорта данных.</li>
				</ul>
					</div>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>