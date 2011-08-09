<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal">Список СЛО </msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    	<tags:stac_journal currentAction="stac_journalRepeatCaseByHospital"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section>
    <msh:sectionTitle>Журнал повторных случаев госпитализаций 
    <a  href="stac_journalRepeatCaseByHospital_print.do?id=${param.id}"  >Печать</a> &nbsp; <a href="stac_journalRepeatCaseByHospital_list.do?id=${param.id}">Изменить...</a>
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_repeatCase" nativeSql="select m.id,p.lastname||' '||p.firstname||' '||p.middlename,d.name as depname,ss.code as sscode,p.birthday,m.dateStart,m.dateFinish,vdh.name as vdhname,m.ambulanceTreatment from MedCase as m left join VocDeniedHospitalizating vdh on vdh.id=m.deniedHospitalizating_id left join statisticstub as ss on ss.id=m.statisticstub_id left join bedfund as bf on bf.id=m.bedfund_id left join vocbedsubtype as vbst on vbst.id=bf.bedSubType_id left join vocbedtype as vbt on vbt.id=bf.bedtype_id left join mislpu as d on d.id=m.department_id left join patient as p on p.id=m.patient_id where m.DTYPE='HospitalMedCase' and m.dateStart between cast($piece('${param.id}',':',2) as Date) and cast($piece('${param.id}',':',3) as Date) and m.patient_id=$piece('${param.id}',':',1) order by m.dateStart" />
    <msh:table name="journal_repeatCase" action="entitySubclassView-mis_medCase.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="#" property="sn" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Стат.карта" property="4" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Отделение" property="3" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Фамилия имя отчетсво пациента" property="2" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Амб.случаи" property="9" guid="fc223a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Отказ от госпитализации" property="8" guid="fc223a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Дата пост. " property="6" guid="f6523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Дата выписки" property="7" guid="f6523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>

