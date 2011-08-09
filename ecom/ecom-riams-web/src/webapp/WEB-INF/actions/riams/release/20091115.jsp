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
				<h1>Ноябрь 2009 года</h1>
				<h2>ЭкоМ:МИС</h2>
				<ul>
					<li>Перенос данных из одной персоны в другую</li>
					<li>Удаление СЛО, СЛС с регистрацией кто удалил и когда</li>
				</ul>
		
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>
