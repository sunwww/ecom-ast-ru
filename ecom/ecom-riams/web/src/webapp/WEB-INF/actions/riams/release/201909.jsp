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
					<% // Версия от 02 сентября %>
				<h1>Сентябрь 2019 года</h1>
				
				<ul>
					<h2>Стационар:</h2>
					<ul>
						<li> * Для перевода из родового отделения необходимо добавить именно вычисление риска ВТЭО (СЛО->Расчёты (в самом низу страницы)->
							Добавить вычисление риска ВТЭО), а не карту оценки риска, которая является устаревшей </li>
					</ul>
					<h2>ЕДКЦ:</h2>
					<ul>
						<li> * Печать протокола и печать списка протоколов </li>
						<li> * Добавлена возможность работы с другими шаблонами </li>
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