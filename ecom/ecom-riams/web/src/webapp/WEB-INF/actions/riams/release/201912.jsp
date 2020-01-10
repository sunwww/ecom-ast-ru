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
					<% // Версия от 2 декабря %>
				<h1>Декабрь 2019 года</h1>
				
				<ul>
					<li></li>
					<li>Патология лабораторных сообщений: отправка уведомлений и браслет пациента с информацией об услуге</li>
					<li>Изменения в онкологической форме: при добавлении через ++ по умолчанию увеличивается дата введения на 1 день</li>
					<li>Акты РВК в стационаре и поликлинике, отчёт по ним</li>
					<li>В журнал микробиоологических исследований помимо поиска на текущий момент добавлен поиск по периоду СЛО</li>
					<li>Учёт в родах женский консультаций, вывод их в отчёт по родам</li>
					<li>Журнал пациентов, осмотренных врачами приемного отделения: свод госиптализированных, свод по отказникам</li>
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