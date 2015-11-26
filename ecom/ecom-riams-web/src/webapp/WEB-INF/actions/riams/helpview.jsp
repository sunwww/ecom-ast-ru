<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

	<tiles:put name='title' type='string'>
	
	</tiles:put>

	<tiles:put name='body' type='string'>
	
	<h2><a href='js-riams-instructions.do'>ИНСТРУКЦИИ ПО РАБОТЕ В МедОС</a></h2>
	<h2>Контекстная помощь</h2>
		${contextText}
	</tiles:put>
	<tiles:put name="javascript" type="string">

	</tiles:put>
</tiles:insert>
