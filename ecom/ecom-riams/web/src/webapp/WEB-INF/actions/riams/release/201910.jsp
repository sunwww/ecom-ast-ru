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
					<% // Версия от 27 сентября %>
				<h1>Октябрь 2019 года</h1>
				
				<ul>
					<h2>Стационар:</h2>
					<li></li>
					<li>Печать персональных данных пациента реанимации: вывод номера истории и диагноза вместо ФИО и даты рождения</li>
					<li>Назначение на операцию - доработана форма создания назначения, выбор операционной, услуги, врача, информации о наркозе и группе крови. Расчет занятости операционной.</li>
					<msh:ifInRole roles="/Policy/Config/HelpAdmin">
							<br>
						</msh:ifInRole>
				</ul>
					</div>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>