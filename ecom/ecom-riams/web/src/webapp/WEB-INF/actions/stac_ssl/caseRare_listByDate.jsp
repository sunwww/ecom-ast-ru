<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Журнал обращений по редким случаям госпитализации (СЛС) за ${param.id}" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:stac_journal currentAction="stac_journalCaseRareByHospital" />
  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="list" nativeSql="select m.id,ss.code,m.dateStart,m.dateFinish,p.lastname||' '||p.firstname||' '||p.middlename,m.username,d.name as dname,m.emergency,vdh.name as vdhname,m.dateStart,m.dateFinish from MedCase as m left join patient p on p.id=m.patient_id left join vocdeniedhospitalizating vdh on vdh.id = m.deniedhospitalizating_id left join mislpu d on d.id=m.department_id left join StatisticStub ss on ss.id=m.statisticStub_id where m.DTYPE='HospitalMedCase' and cast(m.rareCase as int)=1 and m.dateStart=cast('${param.id}' as date)"/>
    <msh:table name="list" action="entityParentView-stac_ssl.do" idField="1" noDataMessage="Не найдено">
      <msh:tableColumn columnName="Стат.карта" property="2" />
      <msh:tableColumn columnName="Дата начала" property="3" />
      <msh:tableColumn columnName="Дата окончания" property="4" />
      <msh:tableColumn columnName="Пациент" property="5" />
      <msh:tableColumn columnName="Кем открыт" property="6" />
      <msh:tableColumn columnName="Отделение" property="7" />
      <msh:tableColumn columnName="Экстренность" property="8" />
      <msh:tableColumn columnName="Отказ от госпитализации" property="9" />
    </msh:table>
  </tiles:put>
</tiles:insert>

