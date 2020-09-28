<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="mis_patientForm" title="Список сообщений о суицидах по пациенту"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  </tiles:put>
  <tiles:put name="body" type="string">
  	<ecom:webQuery name="listd" nativeSql="select s.id,s.suicideDate
  	from SuicideMessage s
  	where s.patient_id=${param.id}	"/>
  	<msh:section createRoles="/Policy/Mis/Psychiatry/CareCard/SuicideMessage/Create" createUrl="entityParentPrepareCreate-psych_suicideMessage.do?id=${param.id}" title="Список сообщений о суицидах по пациенту">
    <msh:table name="listd" action="entityParentView-psych_suicideMessage.do" idField="1">
              <msh:tableColumn property="sn" columnName="#"/>
              <msh:tableColumn property="1" columnName="ИД"/>
              <msh:tableColumn property="2" columnName="Дата суицида"/>
    </msh:table>
    </msh:section>
  </tiles:put>
</tiles:insert>

