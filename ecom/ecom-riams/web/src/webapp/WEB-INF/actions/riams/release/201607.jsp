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
				<h1>Июль 2016 года</h1>
				<ul>
					<li>Ежедневный отчет - отображение пациентов, лежащих по ДМС</li>
					<li>Доработана печать истории родов. В шаблон печатается данные по госпитализации, данные по родам и новорожденным</li>
					<li>Реализован экспорт плана ДД в ФОМС</li>
					<li></li>
				
				</ul>
				
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>