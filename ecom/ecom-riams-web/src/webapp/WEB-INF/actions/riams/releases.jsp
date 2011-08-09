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
				<h2>Что появилось нового в ПО "Эком:МИС"?</h2>
				<ul>
					<li><msh:link  action="ecom_release-20090815.do">
					Август 2009 года
                        </msh:link></li>
					<li><msh:link  action="ecom_release-20090915.do">
					Сентябрь 2009 года
                        </msh:link></li>
					<li><msh:link  action="ecom_release-20091015.do">
					Октябрь 2009 года
                        </msh:link></li>
					<li><msh:link  action="ecom_release-20091115.do">
					Ноябрь 2009 года
                        </msh:link></li>
                        <li><msh:link  action="ecom_release-20091215.do">
					Декабрь 2009 года
                        </msh:link></li>
                        <li><msh:link  action="ecom_release-20100115.do">
					Январь 2010 года
                        </msh:link></li>
                        <li><msh:link  action="ecom_release-20100215.do">Февраль 2010 года</msh:link></li>
                        <li><msh:link  action="ecom_release-20100315.do">Март 2010 года</msh:link></li>
                        <li><msh:link  action="ecom_release-20100415.do">Апрель 2010 года</msh:link></li>
                        <li><msh:link  action="ecom_release-20100515.do">Май 2010 года</msh:link></li>
				</ul>
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>
