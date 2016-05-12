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
				<h1>Июнь 2010 года</h1>
				<h2>Новые возможности при работе с "Эком:ЭкоМ":</h2>
				<ul>
					<li>1. Доработано css</li>
					<li>2. Доработан справочник МКБ. Для поиска диагноза по МКБ теперь необязательно переключать раскладку на латинскую</li>				
				</ul>
				<h2>Новые возможности при работе с "Эком:Стационаром":</h2>
					<ul>
					<li>1. Доработан справочник ЛС</li>
					<li>2. В СЛО добавлены диагнозы основной и сопутствующий</li>
					<li>3. При создании нового СЛС возможно сразу прикрепить полиса ОМС и ДМС</li>
					<li>4. Доработаны полисы. Добавлен просмотр СЛС, прикрепленные к данному полису</li>
					</ul>
					<h2>Новые возможности при работе с "Эком:Талон":</h2>
					<ul>
						<li>1. Добавлен журнал по дублям по специалисту</li>
					</ul>
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>
