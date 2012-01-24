<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/printLayout.jsp" flush="true" >
  <tiles:put name="body" type="string">
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска талонов ${infoTypePat}. Период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_hosp" nativeSql="select p.id,hosp.dateStart,$$CheckPatientOMC^ZMedPolicy(hosp.patient_id,hosp.dateStart),p.lastname||' '||p.firstname||' '||p.middlename,count(*),ss.code 
    , hosp.dateStart, dep.name as depname, vas.name as vasname,p.birthday
    from Medcase hosp 
    left join StatisticStub ss on ss.id=hosp.statisticStub_id 
    left join MisLpu dep on dep.id=hosp.department_id
    left join vocservicestream vss on vss.id=hosp.servicestream_id 
    left join patient p on p.id=hosp.patient_id 
    left join vocAdditionStatus vas on vas.id=p.additionStatus_id
    where hosp.dateStart  between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${param.dateEnd}','dd.mm.yyyy') and vss.code in ('OBLIGATORYINSURANCE','PRIVATEINSURANCE') ${add} and $$CheckPatientOMC^ZMedPolicy(hosp.patient_id,hosp.dateStart) is not null group by p.id" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
        <msh:table name="journal_hosp" action="entityView-mis_patient.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Доп.статус" property="9"/>
            <msh:tableColumn columnName="ФИО пациента" property="4"/>
            <msh:tableColumn columnName="Год рождения" property="10"/>
            <msh:tableColumn columnName="№стат.карты" property="6"/>
            <msh:tableColumn columnName="Полис" property="3"/>
            <msh:tableColumn columnName="Кол-во" property="5"/>
            <msh:tableColumn columnName="Дата поступления" property="7"/>
            <msh:tableColumn columnName="Отделение" property="8"/>
        </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% } else {%>
    	<i>Выберите параметры поиска и нажмите "Найти" </i>
    	<% }   %>
    	</tiles:put>
</tiles:insert>
