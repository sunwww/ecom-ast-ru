<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/printLayout.jsp" flush="true" >
  <tiles:put name="body" type="string">
    <%
    String date = (String)request.getParameter("dateBegin") ;
    Long department = (Long)request.getAttribute("department") ;
    if (date!=null && !date.equals("") && department!=null && department.intValue()>0 )  {
    	%>
   <p>Результаты поиска обращений в отделение  ${departmentInfo}.  </p>
  <p> Период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch}</p>
    <msh:section>
    <msh:sectionTitle>Результаты поиска обращений в отделение  ${departmentInfo}. Период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nativeSql="select m.id,m.dateStart as mdateStart,m.dateFinish,m.transferDate,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename,pat.birthday,sc.code as sccode
    ,vas.name as vasname,vbst.name as vbstname,vss.name as vssname
    ,case when m.prevMedCase_id is not null then 'Да' else 'Нет' end,vhr.name,sls.dateStart as slsdateStart
    from medCase m 
    
    left join MedCase as sls on sls.id = m.parent_id
    left join BedFund bf on m.bedfund_id=bf.id
    left join VocBedSubType vbst on vbst.id=bf.bedSubType_id
    left join VocServiceStream vss on vss.id=bf.serviceStream_id
    left join VocHospitalizationResult vhr on vhr.id=sls.result_id
    left join StatisticStub as sc on sc.medCase_id=sls.id 
    left outer join Patient pat on m.patient_id = pat.id 
    left join VocAdditionStatus vas on pat.additionStatus_id=vas.id
    where m.DTYPE='DepartmentMedCase' and m.department_id='${department}'  and m.${dateSearch}  between '${param.dateBegin}'  and '${param.dateEnd}'
    order by vbst.name, m.${dateSearch}
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" action="entityParentView-stac_slo.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="7" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Доп.статус" property="8"/>
      <msh:tableColumn columnName="Перевод из др. отд." property="11"/>
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="6" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата поступления в стационар" property="13" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Дата поступления в отделение" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Дата перевода" property="4" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
      <msh:tableColumn columnName="Дата выписки" property="3" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
      <msh:tableColumn columnName="Тип коек" property="9"/>      
      <msh:tableColumn columnName="Поток обслуживания" property="10"/>  
      <msh:tableColumn columnName="Результат госпитализации" property="12"/>  
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Нет данных </i>
    	<% }   %>
    	</tiles:put>
</tiles:insert>