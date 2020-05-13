<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Журнал редких случаев госпитализации (СЛС)" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:stac_journal currentAction="stac_journalCaseRareByHospital" />
  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="list" hql="select dateStart, count(*) from MedCase where DTYPE='HospitalMedCase' and cast(rareCase as int)=1 group by dateStart"/>
    <msh:table name="list" action="stac_journalCaseRareHospitalByDate.do" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Дата поступления" property="1" />
      <msh:tableColumn columnName="Кол-во СЛС" property="2" />
    </msh:table>
  </tiles:put>
</tiles:insert>

