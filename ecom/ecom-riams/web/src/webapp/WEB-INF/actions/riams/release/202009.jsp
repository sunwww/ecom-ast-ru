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
					<% // Версия от 31 августа %>
				<h1>Сентябрь 2020 года</h1>
				
				<ul>
					<li></li>
					<li>Сохранение роста и веса в СЛС при изменении данных о поступлении</li>
					<li>Валидация диагнозов МКБ</li>
					<li>В журнал хирургических операций добавлен вывод номера истории и переход на новую страницу</li>
					<li>Исправлена печать информации по визитам (печать выбранных поликлинических визитов пациента списком)</li>
                    <li>Обновление отчёта по пациентам с незаполненными данными</li>
					<li>Печать большого количества дневников одним списком</li>
					<li>Привилегии при редактировании дневников</li>
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