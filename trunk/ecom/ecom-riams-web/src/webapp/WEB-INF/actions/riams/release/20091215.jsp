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
				<h1>Декабрь 2009 года</h1>
				<h2>ЭкоМ:МИС</h2>
				<ul>
					<li>При сохранение персоны отображается окно с возможными совпадениями по ФИО и году рождению или по СНИЛСу</li>
					<li>Доработан справочник организаций с возможностью поиска по старому и новому коду</li>
				</ul>
		
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>
