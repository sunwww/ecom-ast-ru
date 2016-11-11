alter table VocQualityEstimationCritDefect alter column id set default nextval('VocQualityEstimationCritDefect_sequence');<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
				<h1>Ноябрь 2016 года</h1>
				<ul>
					<li>Экспертные карты: При простановке дефекта обязателен выбор причины дефекта</li>
					<li>Отчет "Журнал хирургических операций" дополнен фильтром по использованию анестезии</li>
					<li>Добавлена печать оценочных карт</li>
					<li>Отображение оценочных карт - добавлена группировка по группе</li>
					<li>Добавлено подтверждение при удалении пред. направления</li>
					<li>14 форма - в подсчете учитываются уникальные госпитализации</li>
					<li>При создании госпитализации для пользователей с определенной ролью обязательны к заполнению поля "диета" и "режим"</li>
					<li><msh:ifInRole roles="/Policy/Config/HelpAdmin">
						<br>
					Роль для обязательного заполнения полей: /Policy/Mis/MedCase/Stac/Slo/ForceCreatePrescriptionList
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