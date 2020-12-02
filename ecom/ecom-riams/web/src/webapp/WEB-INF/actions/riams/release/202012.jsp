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
					<% // Версия от 30 ноября %>
				<h1>Декабрь 2020 года</h1>
				<ul>
					<li></li>
                    <li>Механизм выполнения ПЦР-анализов на SARS-COV2 лабораторией ГБУЗ АО АМОКБ:</li>
						<ul>
							<li> - отчёт "Положительные ПЦР" добавлен в меню лаборатории</li>
							<li> - убран забор биоматериала ПЦР для врачей </li>
							<li> - забор биоматериала ПЦР ставится автоматически при создании назначения на SARS-COV2 на следующий день на 06:00 утра</li>
							<li> - настроен механизм редактирования браслетов при редактирования результата анализа до подтверждения врачом КДЛ</li>
							<li> - в реестр приёма-передачи добавлен вывод даты рождения</li>
							<li> - Кнопка Рез. ПЦР доступна только для SARS-COV2-исследований при наличии роли</li>
						</ul>
					<li>Сводная таблица ПЦР-анализов на SARS-COV2, выполненных лабораторией ГБУЗ АО АМОКБ</li>
					<li>Вывод врачебных комиссий в госпитализации за все СЛО</li>
					<li>Оптимизация отчёта по браслетам</li>
					<li></li>
				</ul>
					</div>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>