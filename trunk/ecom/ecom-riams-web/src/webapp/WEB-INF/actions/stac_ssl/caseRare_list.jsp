<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal" title="Журнал редких случаев госпитализации (СЛС)" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:stac_journal currentAction="stac_journalCaseRareByHospital" />
  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="list" hql="select dateStart, count(*) from MedCase where DTYPE='HospitalMedCase' and rareCase=1 group by dateStart"/>
    <msh:table name="list" action="stac_journalCaseRareHospitalByDate.do" idField="1" noDataMessage="Не найдено" guid="b0e1aebf-a031-48b1-bc75-ce1fbeb6c6db">
      <msh:tableColumn columnName="#" property="sn" guid="ee7181c8-98a7-48b3-8009-3be23b7b86b5" />
      <msh:tableColumn columnName="Дата поступления" property="1" guid="c1058c40-21b8-4e52-b0ff-09fb280d33a7" />
      <msh:tableColumn columnName="Кол-во СЛС" property="2" guid="8938a621-314c-4246-9c88-5381272a71f1" />
    </msh:table>
  </tiles:put>
</tiles:insert>

