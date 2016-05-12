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
				<h1>Апрель 2012 года</h1>
				<h2>Стационар:</h2>
				<ul>
					<li>1. Добавлена возможность просмотра (выгрузки) реестра иностранных пациентов, лежавщих в стационаре (Журнал обращений -> Адресный листок) </li>
					<li>2. Добавлена возможность формирования служебной записки по пациентам без страховых документов стационара на заведующих отделений (Журнал обращений -> Реестры -> По госпитализированным без прикрепленных полисов)</li>				
				</ul>

				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>