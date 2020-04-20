<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="mis_medCaseForm" title="Карты COVID"/>
<%
  String patientId = request.getParameter("patient");
  String sqlAdd;
  if (patientId!=null) {
    sqlAdd = " c.patient_id="+patientId;
  } else {
    sqlAdd = " c.medCase_id="+request.getParameter("id");
  }
  request.setAttribute("sqlAdd",sqlAdd);

%>
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="cardList" nativeSql="select c.id, c.cardNumber
    ,coalesce(mkb.code||' '||mkb.name,c.diagnosis) as diagnosis, c.diagnosisDate
    ,c.createDate, c.createTime
    ,case when c.exportDate is not null then 'background-color:#8ee68e; color:black' when c.noActual='1' then 'background-color:#979090; color:black'
     when (c.labResult is not null and c.labResult!='') then 'background-color: #f0ba57; color:black' else '' end as f9_styleRow
from Covid19 c
left join vocidc10 mkb on mkb.id=c.mkb_id
 where ${sqlAdd} order by c.createDate, c.createTime" />
    <msh:table styleRow="7" name="cardList" action="entityParentView-smo_covid19.do" idField="1">
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

