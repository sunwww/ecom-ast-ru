<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">
	<tiles:put>
		<ecom:titleTrail mainMenu="poly_medcardForm" title="Список талонов"/>
	</tiles:put>
  <tiles:put name="body" type="string">
 <msh:ifInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table name="list" 
        deleteUrl="entityParentDeleteGoParentView-poly_ticket.do" editUrl="entityParentEdit-poly_ticket.do" viewUrl="entityShortView-poly_ticket.do"
        action="entityParentView-poly_ticket.do" idField="id" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Номер" property="id"/>
            <msh:tableColumn columnName="Дата выдачи талона" property="date"/>
            <msh:tableColumn columnName="Время выдачи талона" property="time"/>
            <msh:tableColumn columnName="Специалист" property="workFunctionInfo" />
            <msh:tableColumn columnName="Пациент" property="patientName" />
            <msh:tableColumn columnName="Статус" property="statusName"/>
	        <msh:tableColumn columnName="Беседа с родст." property="talk"/>
        </msh:table>
            </msh:ifInRole>
            <msh:ifNotInRole roles="/Policy/Mis/MisLpu/Psychiatry">
        <msh:table
         deleteUrl="entityParentDeleteGoParentView-poly_ticket.do" editUrl="entityParentEdit-poly_ticket.do" viewUrl="entityShortView-poly_ticket.do"
         name="list" action="entityParentView-poly_ticket.do" idField="id" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Номер" property="id"/>
            <msh:tableColumn columnName="Дата выдачи талона" property="date"/>
            <msh:tableColumn columnName="Время выдачи талона" property="time"/>
            <msh:tableColumn columnName="Специалист" property="workFunctionInfo" />
            <msh:tableColumn columnName="Пациент" property="patientName" />
            <msh:tableColumn columnName="Статус" property="statusName"/>
        </msh:table>
            </msh:ifNotInRole>

  </tiles:put>
</tiles:insert>