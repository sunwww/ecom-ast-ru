<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Journals">ДОГОВОРА</msh:title>
	</tiles:put>

	<tiles:put name='body' type='string'>
		<table class='mainMenu'>
			<tr>
				<td class='col1'>
				<div class='menu'>
				<h2>ДОГОВОРА</h2>
				<ul>
					<li><msh:link roles='/Policy/Mis/Contract/PriceList/View' action="journal_infectMessage.do">
                            Прайс-листы
                        </msh:link></li>
					<li><msh:link roles="/Policy/Mis/Contract/Customer/View" action="journal_militiaMessage.do">
                            Заказчики
                        </msh:link></li>
                        
				</ul>
				</div>
				
				</td>
			</tr>
			
		</table>
	</tiles:put>
</tiles:insert>
