<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Журнал вакцинаций" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="vac_vaccination" />
    <tags:mis_journal />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section>
      <msh:sectionTitle>
        Результаты поиска за дату с ${param.id}.
        <msh:link action="journal_vaccination.do">Выбрать другую дату</msh:link>
      </msh:sectionTitle>
      <msh:sectionContent>
        <ecom:webQuery name="listInf" nativeSql="select smc.id,pat.id&#xA;from MedCase as smc&#xA;left join Patient pat on smc.patient_id=pat.id &#xA;where smc.DTYPE='ServiceMedCase' and smc.dateExecute='${param.id}'" />
        <msh:table name="listInf" action="entityView-vac_vaccination.do" idField="1">
          <msh:tableColumn columnName="ФИО пациента" identificator="false" property="2" />
          <msh:tableColumn columnName="Время" identificator="false" property="3" />
          <msh:tableColumn columnName="Мед.услуга" identificator="false" property="4" />
          <msh:tableColumn identificator="false" property="2" columnName="Кол-во" />
          <msh:tableColumn columnName="Специалист" property="5" />
          <msh:tableColumn columnName="Случай мед.обслуживания" property="6" />
        </msh:table>
      </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>

