<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal" title="Журнал обращений по открытым случаям госпитализации (СЛС)" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:stac_journal currentAction="stac_journalOpenByHospital" />
  </tiles:put>
  <tiles:put name="body" type="string">
  <ecom:webQuery name="list" nativeSql="select to_char(t.dateStart,'dd.mm.yyyy') as dateStart
  ,count(distinct t.id) as cnt1
  ,count(distinct case when t1.dtype='DepartmentMedCase' then t.id else null end) as cnt2
  ,CURRENT_DATE-t.dateStart as cntDays 
  from MedCase as t 
  left join MedCase as t1 on t1.parent_id=t.id   
  where t.DTYPE='HospitalMedCase' and (t.noActuality='0' or t.noActuality is null) 
  and t.dateFinish is null 
  and t.deniedHospitalizating_id is null 
  and (t.ambulanceTreatment is null or t.ambulanceTreatment='0') 
  and t1.prevMEdCase_id is null 
  group by t.dateStart order by t.dateStart"/>
    <msh:table name="list" action="stac_journalOpenHospitalByDate.do" 
    viewUrl="stac_journalOpenHospitalByDate.do?s=Short"
    idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" guid="ee7181c8-98a7-48b3-8009-3be23b7b86b5" />
      <msh:tableColumn columnName="Дата поступления" property="1" guid="c1058c40-21b8-4e52-b0ff-09fb280d33a7" />
      <msh:tableColumn columnName="Кол-во СЛС" isCalcAmount="true" property="2" guid="8938a621-314c-4246-9c88-5381272a71f1" />
      <msh:tableColumn columnName="Кол-во СЛО" isCalcAmount="true" property="3" guid="43ff91bb-5ed5-4b20-926a-0819cc48fbb5" />
       <msh:tableColumn columnName="Кол-во к.дней (на текущий момент)" property="4" />  
    </msh:table>
  </tiles:put>
</tiles:insert>

