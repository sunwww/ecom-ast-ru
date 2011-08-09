<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/printLayout.jsp" flush="true" >
  <tiles:put name="body" type="string">
    <%
    String date = request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	String emer= request.getParameter("emergancyIs") ;
    	if (emer!=null && emer.equals("on")) {
    		request.setAttribute("emerIs","m.emergency=1") ;
    		request.setAttribute("emerInfo","экстренно") ;
    	} else {
    		request.setAttribute("emerIs","(m.emergency is null or m.emergency = 0)") ;
    		request.setAttribute("emerInfo","планово") ;
    	}
    	String disc = request.getParameter("dischargeIs") ;
    	if (disc!=null && disc.equals("on")) {
    		request.setAttribute("dateI","dateFinish") ;
    		request.setAttribute("timeI","dischargeTime") ;
    		request.setAttribute("dischInfo","выбывшим ") ;
    	} else {
    		request.setAttribute("dateI","dateStart") ;
    		request.setAttribute("timeI","entranceTime") ;
    		request.setAttribute("dischInfo","поступившим ") ;
    	}
    	%>
    
    	%>
    
    <msh:section>
    <msh:sectionTitle>Реестр за день ${param.dateBegin}. По ${dischInfo}  ${emerInfo}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nativeSql="select m.id,m.dateStart,m.dateFinish,m.dischargeTime,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename,pat.birthday,sc.code,m.emergency,ml.name,m.entranceTime from MedCase as m  left join StatisticStub as sc on sc.medCase_id=m.id left outer join Patient pat on m.patient_id = pat.id left join MisLpu as ml on ml.id=m.department_id where m.DTYPE='HospitalMedCase' and m.${dateI} ='${param.dateBegin}' and  ${emerIs} order by m.${dateI},m.${timeI}" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" action="entityParentView-stac_ssl.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="34a9f56ab-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Стат.карта" property="7" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="6" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата поступления" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Время поступления" property="10" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Отделение" property="9" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
      <msh:tableColumn columnName="Дата выписки" property="3" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
      <msh:tableColumn columnName="Время выписки" property="4" guid="d964-5653-4920-bb78-1622cbeefa34" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Нет данных </i>
    	<% }   %>
    	</tiles:put>
</tiles:insert>
