<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

	<tiles:put name='title' type='string'>
		<msh:title></msh:title>
	</tiles:put>

	<tiles:put name='body' type='string'>
		<table class='mainMenu'>
			<tr>
				<td class='col1'>
				<div class='menu'>
				<h2>Что появилось нового в ПО "МедОС"?</h2>
				<ul>
					<li><msh:link  action="ecom_release-201604.do">
						 апрель 2016 года
                        </msh:link></li>
					<li><msh:link  action="ecom_release-201603.do">
						 март 2016 года
                        </msh:link></li>
					<li><msh:link  action="ecom_release-20160216.do">
					 февраль 2016 года
                        </msh:link></li>
                      </ul>  
					<h2>Дополнительная информация</h2>
					<ul>
					<li><msh:link  action="ecom_release-201501mkb.do">
					Изменения в МКБ январь 2015 г.
                        </msh:link></li>
					
				</ul>
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>
