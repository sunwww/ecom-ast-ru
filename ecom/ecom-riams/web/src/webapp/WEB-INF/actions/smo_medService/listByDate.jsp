<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Журнал услуг" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="smo_medCase" />
    <tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section>
      <msh:sectionTitle>
        Результаты поиска за дату с ${param.id}.
        <msh:link action="journal_serviceMedCase.do">Выбрать другую дату</msh:link>
      </msh:sectionTitle>
      <msh:sectionContent>
        <ecom:webQuery name="listInf" nativeSql="select smc.id,smc.dateStart,smc.timeExecute,pat.lastname || ' ' || pat.firstname || ' ' || pat.middlename,pat.birthday from MedCase as smc left join Patient pat on smc.patient_id=pat.id left join MedService ms on smc.medService_id=ms.id where smc.dateStart='${param.id}' " />
        <msh:table name="listInf" action="entityParentView-smo_medService.do" idField="1">
          <msh:tableColumn columnName="ФИО пациента" identificator="false" property="3" />
          <msh:tableColumn columnName="Год рождения" identificator="false" property="4" />
        </msh:table>
      </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>