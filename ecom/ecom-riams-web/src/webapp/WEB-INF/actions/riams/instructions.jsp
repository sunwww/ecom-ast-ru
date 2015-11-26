<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

	<tiles:put name='title' type='string'>
		<msh:title>ИНСТРУКЦИИ</msh:title>
	</tiles:put>

	<tiles:put name='body' type='string'>
		<table class='mainMenu'>
			<tr>
				<td class='col1'>
				<div class='menu'>
				<h2>Лаборатория</h2>
				<ul>
					<li><msh:link  action="js-riams-instruction.do?id=lab">
					Рабочее место процедурной мед.сестры
                        </msh:link></li>
					
					
				</ul>
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>
