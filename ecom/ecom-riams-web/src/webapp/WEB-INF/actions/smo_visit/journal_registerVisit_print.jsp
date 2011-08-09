<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/printLayout.jsp" flush="true" >
  <tiles:put name="body" type="string">
    
    <%
    	if (request.getAttribute("listRegisterVisit")!=null) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Период с ${beginDate} по ${finishDate}</msh:sectionTitle>
    <msh:sectionContent>
        <msh:table name="listRegisterVisit" action="entitySubclassView-mis_medCase.do" idField="id" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="№визита" property="id"/>            
            <msh:tableColumn columnName="Дата приема" property="date"/>            
            <msh:tableColumn columnName="Специалист" property="spec"/>
            <msh:tableColumn columnName="ФИО" property="fio"/>
            <msh:tableColumn columnName="Пол" property="sex"/>
            <msh:tableColumn columnName="Дата рождения" property="birthday"/>
            <msh:tableColumn columnName="Адрес" property="address"/>
            <msh:tableColumn columnName="Район проживания" property="rayon"/>
            <msh:tableColumn columnName="Серия номер полиса" property="policy"/>
            <msh:tableColumn columnName="Диагноз. Код МКБ" property="mkb"/>
            <msh:tableColumn columnName="Поток обслуживания" property="serviceStream"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <%} %>

    	</tiles:put>
</tiles:insert>
