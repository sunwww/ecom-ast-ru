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
				<h1>Октябрь 2016 года</h1>
				<ul>
					<li>Добавлена возможность прикреплять визиты к закрытому СПО (при наличии соответствующей роли)</li>
					<msh:ifInRole roles="/Policy/Config/HelpAdmin">
						<br>
						Роль: /Policy/Mis/MedCase/Visit/CanAttachToCloseSPO
					</msh:ifInRole>
					<li>Карты осмотров: при создании карты происходит создание визитов к специалистам (если в услуге выбран врач)</li>
					<li>Поля "профиль" и "метод" в операции - обязательны к заполнению</li>
					<li>Добавление услуги - добавлено поле "дата по" (для койко-дней) с автоматическим подсчетом кол-ва услуг (дней)</li>
					<li></li>
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