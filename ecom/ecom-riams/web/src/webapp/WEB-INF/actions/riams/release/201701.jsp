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
				<h1>Январь 2017 года</h1>
				
				<ul>
					
					<li>Роддом: добавлен запрет на создание родов и новорожденных позже текущей даты</li>
					<li>Лаборатория: добавлена возможность создавать дневники исследований по умолчанию</li>
					<li>Лаборатория: добавлена возможность врачу лаборатории создавать назначения на основе существующих назначений</li>
					<li><msh:ifInRole roles="/Policy/Config/HelpAdmin">
						<br>
					Необходимо врачу лабораторию дать роль с политикой /Policy/Mis/Journal/Prescription/LabSurvey/SelfPrescriptionDoctor , 
					Врач сможет назначить только те анализы (услуги), которые отмечены галочкой "может назначаться врачом лаборатории"
					
					</msh:ifInRole>
					</li>
					<li></li>
					<msh:ifInRole roles="/Policy/Config/HelpAdmin">в
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