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
					<li></li>
							
				</ul>
				<h2>В рамках движения "Свободное ПО" Ткачевой Светланой сделано:</h2>
				<ul>
					<li>Обновлено создание соответствий для платных пациентов с платными услугами в Договорах в "отчете по поликлинике"</li>
					<li>В договоре по платным пациентам отображен результат визита в списке последние посещения</li>
				</ul>
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>