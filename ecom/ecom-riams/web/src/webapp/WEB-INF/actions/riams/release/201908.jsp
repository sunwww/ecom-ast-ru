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
					<% // Версия от 31 июля %>
				<h1>Август 2019 года</h1>
                    <h2>Стационар:</h2>
                    <ul>
                        <li> * Печать отчёта по классификации Робсона </li>
						<li> * В протокол операции добавлен протокол периоперационной антибиотикопрофилактики. Поля <u>класс раны</u>, <u>препарат</u> обязательны всегда.
							Если выбран <u>препарат</u> (если препарат не нужен, выбрать "Нет"), то поля <u>доза</u>, <u>путь введения</u> и <u>время первой дозы</u> обязательны.
							При необходимости можно указать <u>время повторной дозы</u>. Добавлено <b>примечание</b> в форму протокола операции (внизу страницы),
							в котором подробно расписана классификация ран</li>
						<li> * Добавлен отчёт по пациентам, записанным на определённую дату, с выводом других визитов в этот день </li>
                    </ul>
					<h2>Лаборатория:</h2>
					<ul>
						<li> * Вывод ФИО лабораторного техника, выполнившего анализ, в дневник с результатом </li>
					</ul>
					<h2>ЕДКЦ:</h2>
					<ul>
						<li> * Модуль работы с ЕДКЦ: листы наблюдений пациентов, создание новых персон, протоколы консультаций и ежесуточного наблюдения </li>
					</ul>

					<msh:ifInRole roles="/Policy/Config/HelpAdmin">
							<br>
						</msh:ifInRole>
				</ul>
					</div>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>