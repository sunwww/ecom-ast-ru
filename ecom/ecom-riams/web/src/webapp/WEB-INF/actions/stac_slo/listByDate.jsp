<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" title="Список всех CЛО по дате" />
    
  </tiles:put>
  <tiles:put name="side" type="string" >

  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:sectionTitle>${param.departmentInfo}</msh:sectionTitle>
    <ecom:webQuery name="datelist" nativeSql="select m.id,m.dateStart,m.dateFinish,m.transferDate,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename,pat.birthday,sc.code from medCase m left join MedCase as sls on sls.id = m.parent_id left join StatisticStub as sc on sc.medCase_id=sls.id left outer join Patient pat on m.patient_id = pat.id where m.DTYPE='DepartmentMedCase' and m.department_id='${param.department}' and m.${param.dateSearch}=to_date('${param.id}','dd.mm.yyyy')" />
    <msh:table name="datelist" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="7" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="5" />
      <msh:tableColumn columnName="Год рождения" property="6" />
      <msh:tableColumn columnName="Дата поступления" property="2" />
      <msh:tableColumn columnName="Дата перевода" property="4" />
      <msh:tableColumn columnName="Дата выписки" property="3" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
	  <tags:stac_journal currentAction="${param.action}"/>
  </tiles:put>
</tiles:insert>

