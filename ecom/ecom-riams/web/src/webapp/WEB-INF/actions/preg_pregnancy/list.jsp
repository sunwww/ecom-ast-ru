<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Список всех беременностей" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:table name="list" action="entityParentView-preg_pregnancy.do" idField="id">
      <msh:tableColumn columnName="Информация о беременности" property="information"/>
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_pregnancy" name="Новую беременность" title="Добавить беременность" />
    </msh:sideMenu>
    <msh:sideMenu title="Перейти">
      <msh:sideLink key="ALT+2" roles="/Policy/Mis/Patient/View" params="patient" action="/entityView-mis_patient" name="Пациент" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

