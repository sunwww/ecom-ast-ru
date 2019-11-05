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
						<% // Версия от 1 ноября %>
						<h1>Ноябрь 2019 года</h1>
						<ul>
							<h2>ЕДКЦ:</h2>
							<li></li>
							<li>Добавлен вывод пациентов, находящихся на наблюдении в текущий момент (если не указывать период), добавлен вывод причины снятия с наблюдения</li>
							<msh:ifInRole roles="/Policy/Config/HelpAdmin">
								<br>
							</msh:ifInRole>
						</ul>
					</div>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>