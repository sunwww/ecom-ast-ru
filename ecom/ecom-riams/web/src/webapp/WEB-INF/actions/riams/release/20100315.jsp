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
				<h1>Март 2010 года</h1>
				<h2>Новые возможности при работе с "Эком:ЭкоМ":</h2>
				<ul>
					<li>1. Добавлено поле не входит в оплату по ОМС</li>					
				</ul>
				<h2>Новые возможности при работе с "Эком:Стационаром":</h2>
					<ul>
					<li>1. Добавлен период актуальности в хир.операциях </li>
					<li>2. Добавлен журнал по профилю коек </li>
					<li>3. В СЛО добавлен просмотр всех мед.услуг по СЛС</li>
					<li>4. Добавлен журнал редких случаев</li>
					<li>5. Добавлено ограничение по дате выписки, чтоб выписывали только текущим числом</li>
					<li>6. Добавлено поле представителя в СЛС</li>
					</ul>
					<h2>Новые возможности при работе с "Эком:Талон":</h2>
					<ul>
						<li>1. Добавлен просмотр по статистике пользователям</li>
						<li>2. Добавлен просмотр по иногородним, региональным пациентам</li>
						<li>3. Добавлено поле представителя </li>
					</ul>
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>
