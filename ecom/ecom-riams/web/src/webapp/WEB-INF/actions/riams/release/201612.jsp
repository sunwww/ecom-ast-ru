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
				<h1>Декабрь 2016 года</h1>
				
				<ul>
					<li>При изменении отделения при создании СЛО очищается поле "врач"</li>
					<li>Лаборатория - вид назначения со сроком выполнения до 6 часов отображается при назначении не позднее 2х часов при поступлении в отделениие</li>
					<li>Отчет "список пациентов по возрастам" добавить участок, возможность не группировать по возрастам</li>
					<li>При назначении анализа из шаблона брать дату, указанную в поле "дата"</li>
					<li>Добавить возможность печати лаб. дневников по СЛО</li>
					<li></li>
					<li><msh:ifInRole roles="/Policy/Config/HelpAdmin">
						<br>
					
					</msh:ifInRole>
					</li>
					<li></li>
					<msh:ifInRole roles="/Policy/Config/HelpAdmin">
						<br>
					
					</msh:ifInRole>
					
				</ul>
				
				<h2>В рамках движения "Свободное ПО" Ткачевой Светланой сделано:</h2>
				<ul>
					<li></li>
					<li></li>
					<li></li>
					<li></li>
				</ul>
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>