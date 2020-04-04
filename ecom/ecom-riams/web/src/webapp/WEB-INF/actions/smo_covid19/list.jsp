<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="mis_patientForm" title="Карты COVID"/>

  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="cardList" nativeSql="select c.id, c.cardNumber,c.diagnosis, c.diagnosisDate
    ,c.createDate, c.createTime
    ,case when c.exportDate is not null then 'color:green' when c.noActual='1' then 'color:gray' else '' end as f9_styleRow
from Covid19 c where patient_id=${param.id} order by c.createDate, c.createTime" />
    <msh:table styleRow="7" name="cardList" action="entityView-smo_covid19.do" idField="1">
      <msh:tableColumn columnName="Номер ИБ" property="2" />
      <msh:tableColumn columnName="Диагноз" property="3" />
      <msh:tableColumn columnName="Дата диагноза" property="4" />
      <msh:tableColumn columnName="Дата создания" property="5" />
      <msh:tableColumn columnName="Время создания" property="6" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_covid19" name="Новую карту COVID" title="Новую карту COVID" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

