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
				<h1>Февраль 2017 года</h1>
				
				<ul>
				<li>Диспансеризация: возможность добавления в карту диспансеризации назначений</li>
				<li>Изменен алгоритм подсчета даты начала и окончания нетрудоспособности(считается правильно)</li>
					<li><msh:ifInRole roles="/Policy/Config/HelpAdmin">
						<br>
						
					</msh:ifInRole>
					</li>
					<li>При формировании дубликата больничного листа новый документ создается с одним продлением (объединяются продления в предыдущем ЛН)</li>
					<li></li>
				</ul>
				
				<h2>В рамках движения "Свободное ПО" Ткачевой Светланой сделано:</h2>
				<ul>
					
					<li></li>
					<li></li>
					<li></li>
				</ul>
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>